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

package alluxio.worker.block.allocator;


import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.BlockStoreLocation;
import alluxio.worker.block.meta.StorageDirView;
import alluxio.worker.block.meta.StorageTierView;

import com.google.common.base.Preconditions;

import java.io.IOException;

import javax.annotation.concurrent.NotThreadSafe;


@NotThreadSafe
public final class SparkAllocator implements Allocator {
  private BlockMetadataManagerView mManagerView;
  /*
  protected static final int SIZE_BYTES = Constants.MB * 10;
  public LocalAlluxioClusterResource mLocalAlluxioClusterResource =
	      new LocalAlluxioClusterResource.Builder()
	          .setProperty(PropertyKey.WORKER_MEMORY_SIZE, SIZE_BYTES)
	          .setProperty(PropertyKey.USER_BLOCK_SIZE_BYTES_DEFAULT, SIZE_BYTES)
	          .setProperty(PropertyKey.MASTER_TTL_CHECKER_INTERVAL_MS, Integer.MAX_VALUE).build();
  protected LocalAlluxioCluster mLocalAlluxioCluster = null;
  private FileSystem mFileSystem = null;
  private String tier1 = "/";	// MEM root dir
  private String tier2 = "/";	// SSD root dir
  */
  /**
   * Creates a new instance of {@link SparkAllocator}.
   *
   * @param view {@link BlockMetadataManagerView} to pass to the allocator 
   */
  public SparkAllocator(BlockMetadataManagerView view) throws IOException {
    mManagerView = Preconditions.checkNotNull(view);
   // mLocalAlluxioCluster = mLocalAlluxioClusterResource.get();
   // mFileSystem = mLocalAlluxioCluster.getClient();
  }

  @Override
  public StorageDirView allocateBlockWithView(long sessionId, long blockSize,
      BlockStoreLocation location, BlockMetadataManagerView view) {
    mManagerView = Preconditions.checkNotNull(view);
    return allocateBlock(sessionId, blockSize, location);
  }

  /**
   * Allocates a block from the given block store location. The location can be a specific location,
   * or {@link BlockStoreLocation#anyTier()} or {@link BlockStoreLocation#anyDirInTier(String)}.
   *
   * @param sessionId the id of session to apply for the block allocation
   * @param blockSize the size of block in bytes
   * @param location the location in block store
   * @return a {@link StorageDirView} in which to create the temp block meta if success, null
   *         otherwise
   * @throws IllegalArgumentException if block location is invalid
   */
  private StorageDirView allocateBlock(long sessionId, long blockSize,
      BlockStoreLocation location) {
    Preconditions.checkNotNull(location);
    if (location.equals(BlockStoreLocation.anyDirInTier("SSD"))) {
      // When SSD tier is ok, loop over all tier views and dir views,
      // and return a temp block meta from the first available dirview.
      for (StorageTierView tierView : mManagerView.getTierViews()) {
        for (StorageDirView dirView : tierView.getDirViews()) {
          if (dirView.getAvailableBytes() >= blockSize) {
            return dirView;
          }
        }
      }
      return null;
    }

    String tierAlias = location.tierAlias();
    StorageTierView tierView = mManagerView.getTierView(tierAlias);
    if (location.equals(BlockStoreLocation.anyDirInTier(tierAlias))) {
      // Loop over all dir views in the given tier
      for (StorageDirView dirView : tierView.getDirViews()) {
        if (dirView.getAvailableBytes() >= blockSize) {
          return dirView;
        }
      }
      return null;
    }

    int dirIndex = location.dir();
    StorageDirView dirView = tierView.getDirView(dirIndex);
    if (dirView.getAvailableBytes() >= blockSize) {
      return dirView;
    }
    return null;
  }
}
