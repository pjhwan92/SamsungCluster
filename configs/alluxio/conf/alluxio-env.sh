#!/usr/bin/env bash
#
# The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
# (the "License"). You may not use this work except in compliance with the License, which is
# available at www.apache.org/licenses/LICENSE-2.0
#
# This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
# either express or implied, as more fully set forth in the License.
#
# See the NOTICE file distributed with this work for information regarding copyright ownership.
#

# Copy it as alluxio-env.sh and edit that to configure Alluxio for your
# site. This file is sourced to launch Alluxio servers or use Alluxio shell
# commands.
#
# This file provides one way to configure Alluxio options by setting the
# following listed environment variables. Note that, setting this file will not
# affect jobs (e.g., Spark job or MapReduce job) that are using Alluxio client
# as a library. Alternatively, you can edit alluxio-site.properties file, where
# you can set all the configuration options supported by Alluxio
# (http://alluxio.org/documentation/) which is respected by both external jobs
# and Alluxio servers (or shell).

JAVA_HOME="/usr/lib/jvm/jdk1.7.0_79"

# The directory where Alluxio deployment is installed. (Default: the parent directory of libexec/).
ALLUXIO_HOME="/home/arcs/fr/alluxio-1.3.0"

# The directory where log files are stored. (Default: ${ALLUXIO_HOME}/logs).
# ALLUXIO_LOGS_DIR

# Hostname of the master.
#ALLUXIO_MASTER_HOSTNAME=localhost
ALLUXIO_MASTER_HOSTNAME=compute11

# This is now deprecated. Support will be removed in v2.0
#ALLUXIO_MASTER_ADDRESS=localhost

# The directory where a worker stores in-memory data. (Default: /mnt/ramdisk).
# E.g. On linux,  /mnt/ramdisk for ramdisk, /dev/shm for tmpFS; on MacOS, /Volumes/ramdisk for ramdisk
# ALLUXIO_RAM_FOLDER

# Address of the under filesystem address. (Default: ${ALLUXIO_HOME}/underFSStorage)
# E.g. "/my/local/path" to use local fs, "hdfs://localhost:9000/alluxio" to use a local hdfs
#ALLUXIO_UNDERFS_ADDRESS=hdfs://localhost:9000/alluxio
ALLUXIO_UNDERFS_ADDRESS=hdfs://compute11:9000/user/arcs

# How much memory to use per worker. (Default: 1GB)
# E.g. "1000MB", "2GB"
ALLUXIO_WORKER_MEMORY_SIZE=48GB

# Config properties set for Alluxio master, worker and shell. (Default: "")
# E.g. "-Dalluxio.master.port=39999"
# ALLUXIO_JAVA_OPTS

# Config properties set for Alluxio master daemon. (Default: "")
# E.g. "-Dalluxio.master.port=39999"
# ALLUXIO_MASTER_JAVA_OPTS

# Config properties set for Alluxio worker daemon. (Default: "")
# E.g. "-Dalluxio.worker.port=49999" to set worker port, "-Xms2048M -Xmx2048M" to limit the heap size of worker. 
# ALLUXIO_WORKER_JAVA_OPTS

# Config properties set for Alluxio shell. (Default: "")
# E.g. "-Dalluxio.user.file.writetype.default=CACHE_THROUGH"
# ALLUXIO_USER_JAVA_OPTS
