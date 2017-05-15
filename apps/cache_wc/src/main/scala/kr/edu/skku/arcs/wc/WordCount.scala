package kr.edu.skku.arcs.wc

import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by pjh on 5/12/17.
  */
class WordCount {
  var sc: SparkContext = null

  def wc () {
    val hdfs = "hdfs://compute11:9000/user/arcs/"
    val alluxio = "alluxio://compute11:19998/"

    //val conf = new SparkConf ().setAppName ("word count");
    val spark = SparkSession.builder.appName ("word count").getOrCreate ()
    var textFile: RDD[String] = null

    sc = spark.sparkContext

    // hdfs kdda
    textFile = sc.textFile (hdfs + "kdda")
    textFile.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .saveAsTextFile(hdfs + "_hdfs_kdda_result_1")           // 0
    var cachedRdd = textFile.flatMap (_.split (" ")).cache()
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "_hdfs_kdda_result_2")          // 1
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "_hdfs_kdda_result_3")          // 2

    // alluxio kdda
    textFile = sc.textFile (alluxio + "kdda")
    cachedRdd = textFile.flatMap (_.split (" "))
    cachedRdd.map((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (alluxio + "_alluxio_kdda_result_1")    // 3
    cachedRdd.saveAsTextFile (alluxio + "_kdda_med_result_1") // 4  - mediate rdd
    sc.textFile (alluxio + "_kdda_med_result_1")
      .map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (alluxio + "_alluxio_kdda_result_2")    // 5

    // hdfs kddb
    textFile = sc.textFile (hdfs + "kddb")
    textFile.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .saveAsTextFile(hdfs + "_hdfs_kddb_result_1")           // 6
    cachedRdd = textFile.flatMap (_.split (" ")).cache()
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "_hdfs_kddb_result_1")          // 7
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "_hdfs_kddb_result_2")          // 8

    // alluxio kddb
    textFile = sc.textFile (alluxio + "kddb")
    cachedRdd = textFile.flatMap (_.split (" "))
    cachedRdd.map((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (alluxio + "_alluxio_kddb_result_1")    // 9
    cachedRdd.saveAsTextFile (alluxio + "_kddb_med_result_1") // 10  - mediate rdd
    sc.textFile (alluxio + "_kddb_med_result_1")
      .map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (alluxio + "_alluxio_kddb_result_2")    // 11
  }
}
