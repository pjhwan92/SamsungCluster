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
import alluxio.client.ReadType;
import alluxio.client.file.FileInStream;
import alluxio.client.file.FileOutStream;
import alluxio.client.file.FileSystem;
import alluxio.client.file.URIStatus;
import alluxio.client.file.options.CreateFileOptions;
import alluxio.client.file.options.OpenFileOptions;
import alluxio.exception.AlluxioException;
import alluxio.exception.ExceptionMessage;
import alluxio.exception.FileDoesNotExistException;
import alluxio.exception.InvalidPathException;
import alluxio.shell.AlluxioShellUtils;
import alluxio.thrift.InputSplits;
import alluxio.thrift.Split;
import alluxio.util.io.PathUtils;
import com.google.common.base.Joiner;
import com.google.common.io.Closer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.io.IOUtils;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Copies a file or a directory in the Alluxio filesystem.
 */
@ThreadSafe
public final class PrefetchCommand extends AbstractShellCommand {

  /**
   * @param fs the filesystem of Alluxio
   */
  public PrefetchCommand(FileSystem fs) {
    super(fs);
  }

  @Override
  public String getCommandName() {
    return "prefetch";
  }

  @Override
  protected int getNumOfArgs() {
    return 2;
  }

  @Override
  protected Options getOptions() {
    return new Options();
  }

  @Override
  public void run(CommandLine cl) throws AlluxioException, IOException {
    String[] args = cl.getArgs();
    AlluxioURI filePath = new AlluxioURI(args[0]);
    List<AlluxioURI> filePaths = AlluxioShellUtils.getAlluxioURIs(mFileSystem, filePath);
    if (filePaths.size() == 0) {
      throw new FileDoesNotExistException(
          ExceptionMessage.PATH_DOES_NOT_EXIST.getMessage(filePath.getPath()));
    }
    ArrayList<String> paths = new ArrayList<>();
    ArrayList<Split> splits = new ArrayList<>();

    Map<Split, List<Long>> splitListMap = null;
    for (AlluxioURI path : filePaths) {
      String pathName = path.getPath();
      ArrayList<String> splitPaths = new ArrayList<>();
      ArrayList<Long> splitStarts = new ArrayList<>();
      ArrayList<Long> splitLengths = new ArrayList<>();
      splitPaths.add(pathName);
      splitStarts.add(Long.parseLong("0"));
      splitLengths.add(Long.parseLong(args[1]));
      if (!paths.contains(pathName))
        paths.add(pathName);
      splits.add(new Split(splitPaths, splitStarts, splitLengths));
    }

    splitListMap = mFileSystem.prefetchFiles(new InputSplits(paths, splits));

    Iterator it = splitListMap.entrySet().iterator();

    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      System.out.println(pair.getKey() + " = " + pair.getValue());
      it.remove();
    }
  }

  @Override
  public String getUsage() {
    return "prefetch <path> <length of a split>";
  }

  @Override
  public String getDescription() {
    return "Simply prefetch files into target workers";
  }
}
