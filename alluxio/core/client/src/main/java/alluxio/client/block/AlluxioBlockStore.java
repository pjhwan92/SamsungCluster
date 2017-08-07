/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.client.block;

import alluxio.Constants;
import alluxio.client.ClientContext;
import alluxio.exception.AlluxioException;
import alluxio.exception.ConnectionFailedException;
import alluxio.exception.ExceptionMessage;
import alluxio.resource.CloseableResource;
import alluxio.thrift.InputSplits;
import alluxio.thrift.Split;
import alluxio.util.network.NetworkAddressUtils;
import alluxio.wire.BlockInfo;
import alluxio.wire.BlockLocation;
import alluxio.wire.PrefetchFromTo;
import alluxio.wire.WorkerInfo;
import alluxio.wire.WorkerNetAddress;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Alluxio Block Store client. This is an internal client for all block level operations in Alluxio.
 * An instance of this class can be obtained via get(). The methods in
 * this class are completely opaque to user input.
 */
@ThreadSafe
public final class AlluxioBlockStore {
  private static final Logger LOG = LoggerFactory.getLogger(Constants.LOGGER_TYPE);

  private final BlockStoreContext mContext;
  private final String mLocalHostName;

  /**
   * Creates a block store using the master address got from config.
   */
  public AlluxioBlockStore() {
    this(ClientContext.getMasterAddress());
  }

  /**
   * Creates a block store with the specified master address.
   *
   * @param masterAddress the master's address
   */
  public AlluxioBlockStore(InetSocketAddress masterAddress) {
    this(BlockStoreContext.get(masterAddress), NetworkAddressUtils.getLocalHostName());
  }

  /**
   * Creates a block store with the given block store context.
   *
   * @param context the block store context
   */
  public AlluxioBlockStore(BlockStoreContext context) {
    this(context, NetworkAddressUtils.getLocalHostName());
  }

  /**
   * Creates an Alluxio block store.
   *
   * @param context       the block store context to use for acquiring worker and master clients
   * @param localHostName the local hostname for the block store
   */
  AlluxioBlockStore(BlockStoreContext context, String localHostName) {
    mContext = context;
    mLocalHostName = localHostName;
  }

  /**
   * Gets the block info of a block, if it exists.
   *
   * @param blockId the blockId to obtain information about
   * @return a {@link BlockInfo} containing the metadata of the block
   * @throws IOException if the block does not exist
   */
  public BlockInfo getInfo(long blockId) throws IOException {
    try (CloseableResource<BlockMasterClient> masterClientResource =
             mContext.acquireMasterClientResource()) {
      return masterClientResource.get().getBlockInfo(blockId);
    } catch (AlluxioException e) {
      throw new IOException(e);
    }
  }

  /**
   * @return the info of all active block workers
   * @throws IOException      when work info list cannot be obtained from master
   * @throws AlluxioException if network connection failed
   */
  public List<BlockWorkerInfo> getWorkerInfoList() throws IOException, AlluxioException {
    List<BlockWorkerInfo> infoList = new ArrayList<>();
    try (CloseableResource<BlockMasterClient> masterClientResource =
             mContext.acquireMasterClientResource()) {
      for (WorkerInfo workerInfo : masterClientResource.get().getWorkerInfoList()) {
        infoList.add(new BlockWorkerInfo(workerInfo.getAddress(), workerInfo.getCapacityBytes(),
            workerInfo.getUsedBytes()));
      }
      return infoList;
    }
  }

  /**
   * Gets a stream to read the data of a block. The stream is backed by Alluxio storage.
   *
   * @param blockId the block to read from
   * @return a {@link BlockInStream} which can be used to read the data in a streaming fashion
   * @throws IOException if the block does not exist
   */
  public BufferedBlockInStream getInStream(long blockId) throws IOException {
    BlockInfo blockInfo;
    try (CloseableResource<BlockMasterClient> masterClientResource =
             mContext.acquireMasterClientResource()) {
      blockInfo = masterClientResource.get().getBlockInfo(blockId);
    } catch (AlluxioException e) {
      throw new IOException(e);
    }

    if (blockInfo.getLocations().isEmpty()) {
      throw new IOException("Block " + blockId + " is not available in Alluxio");
    }
    // TODO(calvin): Get location via a policy.
    // Although blockInfo.locations are sorted by tier, we prefer reading from the local worker.
    // But when there is no local worker or there are no local blocks, we prefer the first
    // location in blockInfo.locations that is nearest to memory tier.
    // Assuming if there is no local worker, there are no local blocks in blockInfo.locations.
    // TODO(cc): Check mContext.hasLocalWorker before finding for a local block when the TODO
    // for hasLocalWorker is fixed.
    for (BlockLocation location : blockInfo.getLocations()) {
      WorkerNetAddress workerNetAddress = location.getWorkerAddress();
      if (workerNetAddress.getHost().equals(mLocalHostName)) {
        // There is a local worker and the block is local.
        try {
          return new LocalBlockInStream(blockId, blockInfo.getLength(), workerNetAddress, mContext);
        } catch (IOException e) {
          LOG.warn("Failed to open local stream for block " + blockId + ". " + e.getMessage());
          // Getting a local stream failed, do not try again
          break;
        }
      }
    }
    // No local worker/block, get the first location since it's nearest to memory tier.
    WorkerNetAddress workerNetAddress = blockInfo.getLocations().get(0).getWorkerAddress();
    return new RemoteBlockInStream(blockId, blockInfo.getLength(), workerNetAddress, mContext);
  }

  /**
   * Gets a stream to write data to a block. The stream can only be backed by Alluxio storage.
   *
   * @param blockId   the block to write
   * @param blockSize the standard block size to write, or -1 if the block already exists (and this
   *                  stream is just storing the block in Alluxio again)
   * @param address   the address of the worker to write the block to, fails if the worker cannot
   *                  serve the request
   * @return a {@link BufferedBlockOutStream} which can be used to write data to the block in a
   * streaming fashion
   * @throws IOException if the block cannot be written
   */
  public BufferedBlockOutStream getOutStream(long blockId, long blockSize, WorkerNetAddress address)
      throws IOException {
    if (blockSize == -1) {
      try (CloseableResource<BlockMasterClient> blockMasterClientResource =
               mContext.acquireMasterClientResource()) {
        blockSize = blockMasterClientResource.get().getBlockInfo(blockId).getLength();
      } catch (AlluxioException e) {
        throw new IOException(e);
      }
    }
    // No specified location to write to.
    if (address == null) {
      throw new RuntimeException(ExceptionMessage.NO_WORKER_AVAILABLE.getMessage());
    }
    // Location is local.
    if (mLocalHostName.equals(address.getHost())) {
      return new LocalBlockOutStream(blockId, blockSize, address, mContext);
    }
    // Location is specified and it is remote.
    return new RemoteBlockOutStream(blockId, blockSize, address, mContext);
  }

  /**
   * Gets the total capacity of Alluxio's BlockStore.
   *
   * @return the capacity in bytes
   * @throws IOException when the connection to the client fails
   */
  public long getCapacityBytes() throws IOException {
    try (CloseableResource<BlockMasterClient> blockMasterClientResource =
             mContext.acquireMasterClientResource()) {
      return blockMasterClientResource.get().getCapacityBytes();
    } catch (ConnectionFailedException e) {
      throw new IOException(e);
    }
  }

  /**
   * Gets the used bytes of Alluxio's BlockStore.
   *
   * @return the used bytes of Alluxio's BlockStore
   * @throws IOException when the connection to the client fails
   */
  public long getUsedBytes() throws IOException {
    try (CloseableResource<BlockMasterClient> blockMasterClientResource =
             mContext.acquireMasterClientResource()) {
      return blockMasterClientResource.get().getUsedBytes();
    } catch (ConnectionFailedException e) {
      throw new IOException(e);
    }
  }

  /**
   * Attempts to promote a block in Alluxio space. If the block is not present, this method will
   * return without an error. If the block is present in multiple workers, only one worker will
   * receive the promotion request.
   *
   * @param blockId the id of the block to promote
   * @throws IOException if the block does not exist
   */
  public void promote(long blockId) throws IOException {
    BlockInfo info;
    try (CloseableResource<BlockMasterClient> blockMasterClientResource =
             mContext.acquireMasterClientResource()) {
      info = blockMasterClientResource.get().getBlockInfo(blockId);
    } catch (AlluxioException e) {
      throw new IOException(e);
    }
    if (info.getLocations().isEmpty()) {
      // Nothing to promote
      return;
    }
    // Get the first worker address for now, as this will likely be the location being read from
    // TODO(calvin): Get this location via a policy (possibly location is a parameter to promote)
    BlockWorkerClient blockWorkerClient = new RetryHandlingBlockWorkerClient(
        info.getLocations().get(0).getWorkerAddress(), null  /* no session */);
    try {
      blockWorkerClient.promoteBlock(blockId);
    } catch (AlluxioException e) {
      throw new IOException(e);
    } finally {
      blockWorkerClient.close();
    }
  }

  /**
   * Added by pjh.
   *
   * @param blockIds         block ids
   * @param blockLocationMap block id to location map
   * @return worker info
   */
  private List<PrefetchFromTo> selectWorker(final List<Long> blockIds,
      final Map<Long, BlockInfo> blockLocationMap) {
    List<BlockLocation> totalLocations = null;

    Map<Long, BlockInfo> tmpMap
        = Maps.filterKeys(blockLocationMap, new Predicate<Long>() {
      @Override
      public boolean apply(Long input) {
        return blockIds.contains(input);
      }
    });

    for (BlockInfo locationSet : tmpMap.values()) {
      totalLocations.addAll(locationSet.getLocations());
    }

    List<Map.Entry<BlockLocation, Long>> splitBlocks
        = new ArrayList<>(CollectionUtils.getCardinalityMap(totalLocations).entrySet());
    long toWorkerId
        = Collections.max(splitBlocks, new Comparator<Map.Entry<BlockLocation, Long>>() {
      @Override
      public int compare(Map.Entry<BlockLocation, Long> o1, Map.Entry<BlockLocation, Long> o2) {
        return o1.getValue().intValue() - o2.getValue().intValue();
      }
    }).getKey().getWorkerId();

    List<PrefetchFromTo> ret = new ArrayList<>();
    for (Map.Entry<BlockLocation, Long> block : splitBlocks) {
      PrefetchFromTo prefetchBlock = new PrefetchFromTo();
      prefetchBlock.setFromWorkerId(block.getKey().getWorkerId());
      prefetchBlock.setToWorkerId(toWorkerId);
      prefetchBlock.setBlockId(block.getValue());
      ret.add(prefetchBlock);
    }

    return ret;
  }

  /**
   * Added by pjh.
   *
   * @param splits       splits
   * @param splitListMap split to worker map
   */
  public void prefetchSplits(InputSplits splits, Map<Split, List<Long>> splitListMap) {
    Set<Long> allBlocks = new HashSet<>();
    Map<Long, BlockInfo> blockLocationsMap = new HashMap<>();

    for (List<Long> blocksInSplit : splitListMap.values()) {
      allBlocks.addAll(blocksInSplit);
    }
    for (long blockId : allBlocks) {
      try {
        blockLocationsMap.put(blockId, getInfo(blockId));
      } catch (IOException e) {
        LOG.error("Error occurs while getting information about block #{}", blockId);
      }
    }

    if (blockLocationsMap.isEmpty()) {
      LOG.info("Blocks which belong to split({}) does not exist on Alluxio", splits);
    }
    try (CloseableResource<BlockMasterClient> client = mContext.acquireMasterClientResource ()){
      for (Map.Entry<Split, List<Long>> blockList : splitListMap.entrySet()) {
        List<PrefetchFromTo> split = selectWorker(blockList.getValue(), blockLocationsMap);
        client.get().prefetchSplit(split);
      }
    } catch (IOException e) {
      LOG.error("Worker {} does not response.", location.getWorkerId());
    } catch (ConnectionFailedException e) {
      LOG.error("error occurs while connecting to worker");
    }
  }
}
