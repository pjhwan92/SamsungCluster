#export SPARK_CLASSPATH="/home/arcs/fr/alluxio/core/client/target/alluxio-core-client-1.2.1-SNAPSHOT-jar-with-dependencies.jar:/home/arcs/fr/alluxio/conf:${SPARK_CLASSPATH}"
export SPARK_CLASSPATH="/home/arcs/fr/spark/lib:${SPARK_CLASSPATH}"

export JAVA_HOME="/usr/lib/jvm/jdk1.7.0_79"
export SPARK_HOME="/home/arcs/fr/spark"

#export HADOOP_CONF_DIR="hdfs://gtx980ti1:9000/etc/hadoop"
export HADOOP_CONF_DIR="/home/arcs/tachyon/hadoop-2.7.2/etc/hadoop"

#export SPARK_WORKER_MEMORY="32g"
export SPARK_MASTER_OPTS="-Dspark.worker.timeout=1000000"
export SPARK_WORKER_CORES="18"
#export SPARK_DAEMON_MEMORY="32g"
export SPARK_HISTORY_OPTS="-Dspark.history.fs.logDirectory=hdfs://compute11:9000/logs"
