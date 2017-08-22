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

package alluxio.shell.command;

import alluxio.AlluxioURI;
import alluxio.client.file.FileSystem;
import alluxio.exception.AlluxioException;
import alluxio.exception.ExceptionMessage;
import alluxio.exception.FileDoesNotExistException;
import alluxio.shell.AlluxioShellUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.util.List;

/**
 * Copies a file or a directory in the Alluxio filesystem.
 */
@ThreadSafe
public final class CreateSplitsCommand extends AbstractShellCommand {

  /**
   * @param fs the filesystem of Alluxio
   */
  public CreateSplitsCommand(FileSystem fs) {
    super(fs);
  }

  @Override
  public String getCommandName() {
    return "createSplits";
  }

  @Override
  protected int getNumOfArgs() {
    return 3;
  }

  @Override
  protected Options getOptions() {
    return new Options().addOption(RECURSIVE_OPTION);
  }

  @Override
  public void run(CommandLine cl) throws AlluxioException, IOException {
    String[] args = cl.getArgs();
    AlluxioURI path = new AlluxioURI(args[0]);
    long numPartitions = Long.parseLong(args[1]);
    boolean isPartitionSize = Boolean.parseBoolean(args[2]);
    List<AlluxioURI> paths = AlluxioShellUtils.getAlluxioURIs(mFileSystem, path);
    if (paths.size() == 0) {
      throw new FileDoesNotExistException(
          ExceptionMessage.PATH_DOES_NOT_EXIST.getMessage(path.getPath()));
    }

    if (path.containsWildcard()) {
      createSplitsWildcard(paths, numPartitions, isPartitionSize);
    } else {
      createSplits(path, numPartitions, isPartitionSize);
    }
  }

  /**
   * Added by pjh.
   *
   * @param paths file paths
   * @param numPartitions number of partitions
   * @param isPartitionSize whether partition size or number of partitions
   */
  private void createSplitsWildcard(List<AlluxioURI> paths, long numPartitions, boolean isPartitionSize) {
    /* ToDo must be implemented */
  }

  /**
   * Added by pjh.
   *
   * @param path file path
   * @param numPartitions number of partitions
   * @param isPartitionSize whether partition size or number of partitions
   * @throws IOException if IO error occurs
   * @throws AlluxioException if Alluxio error occurs
   */
  private void createSplits(AlluxioURI path, long numPartitions, boolean isPartitionSize)
      throws IOException, AlluxioException {
    mFileSystem.createSplits(path, numPartitions, isPartitionSize);
    System.out.println("Created splits: " + path.getPath());
  }

  @Override
  public String getUsage() {
    return "createSplits <file> <# of partitions> <is number of partitions or size";
  }

  @Override
  public String getDescription() {
    return "Create splits of a file";
  }
}
