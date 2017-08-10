package alluxio.wire;

import alluxio.annotation.PublicApi;

import com.google.common.base.Objects;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Created by pjh on 8/9/17.
 */
@PublicApi
@NotThreadSafe
public class PrefetchBlockMeta {
  private WorkerNetAddress mWorkerNetAddress;
  private long mLength;

  /**
   * Added by pjh.
   */
  public PrefetchBlockMeta() {}

  /**
   * Added by pjh.
   */
  protected PrefetchBlockMeta(alluxio.thrift.PrefetchBlockMeta prefetchBlockMeta) {
    mWorkerNetAddress = ThriftUtils.fromThrift(prefetchBlockMeta.getAddress());
    mLength = prefetchBlockMeta.getLength();
  }

  /**
   * Added by pjh.
   *
   * @return the size of block
   */
  public long getLength() {
    return mLength;
  }

  /**
   * Added by pjh.
   *
   * @return the address of source worker
   */
  public WorkerNetAddress getWorkerNetAddress() {
    return mWorkerNetAddress;
  }

  public PrefetchBlockMeta setLength(long length) {
    mLength = length;
    return this;
  }

  /**
   * Added by pjh.
   *
   * @param workerNetAddress the address of source worker
   * @return the instance of this class
   */
  public PrefetchBlockMeta setWorkerNetAddress(WorkerNetAddress workerNetAddress) {
    mWorkerNetAddress = workerNetAddress;
    return this;
  }

  /**
   * Added by pjh.
   *
   * @return the instance of this class
   */
  public alluxio.thrift.PrefetchBlockMeta toThrift() {
    return new alluxio.thrift.PrefetchBlockMeta(
        ThriftUtils.toThrift(mWorkerNetAddress), mLength);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PrefetchBlockMeta)) {
      return false;
    }
    PrefetchBlockMeta that = (PrefetchBlockMeta) o;
    return mLength == that.mLength && mWorkerNetAddress.equals(that.mWorkerNetAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(mWorkerNetAddress, mLength);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("workerNetAddress", mWorkerNetAddress)
        .add("length", mLength).toString();
  }
}
