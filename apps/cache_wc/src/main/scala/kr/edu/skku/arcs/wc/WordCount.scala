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
    val files = List ("_2500", "_5000", "_7500", "_10000")

    //val conf = new SparkConf ().setAppName ("word count");
    val spark = SparkSession.builder.appName ("word count").getOrCreate ()
    var textFile: RDD[String] = null
    var cachedRdd: RDD[String] = null

    sc = spark.sparkContext

    for (file <- files) {
      textFile = sc.textFile (hdfs + file)
      textFile.count()

      // hdfs
      textFile = sc.textFile(hdfs + file)
      textFile.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_hdfs" + file + "_result_1") // 0
      cachedRdd = sc.textFile(hdfs + file).cache()
      cachedRdd.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_hdfs" + file + "_result_2") // 1
      cachedRdd.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_hdfs" + file + "_result_3") // 2

      // alluxio
      textFile = sc.textFile(hdfs + file)
      textFile.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_alluxio" + file + "_result_1") // 3
      sc.textFile(hdfs + file).saveAsTextFile(alluxio + "wc" + file + "_med_result_1")
      cachedRdd = sc.textFile(alluxio + "wc" + file + "_med_result_1")
      cachedRdd.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_alluxio" + file + "_result_2") // 5

      textFile = sc.textFile(hdfs + file)
      textFile.count()
    }
  }

  def wc2 () {
    val hdfs = "hdfs://compute11:9000/user/arcs/"
    val alluxio = "alluxio://compute11:19998/"

    //val conf = new SparkConf ().setAppName ("word count");
    val spark = SparkSession.builder.appName ("word count").getOrCreate ()
    var textFile: RDD[String] = null
    var cachedRdd: RDD[_] = null

    sc = spark.sparkContext
    textFile = sc.textFile (hdfs + "kdda")
    textFile.count()

    // hdfs kdda
    textFile = sc.textFile (hdfs + "kdda")
    textFile.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .saveAsTextFile(hdfs + "wc_hdfs_kdda_result_1")           // 0
    textFile = sc.textFile (hdfs + "kdda")
    cachedRdd = textFile.flatMap (_.split (" ")).cache()
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "wc_hdfs_kdda_result_2")          // 1
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "wc_hdfs_kdda_result_3")          // 2

    // alluxio kdda
    textFile = sc.textFile (hdfs + "kdda")
    cachedRdd = textFile.flatMap (_.split (" "))
    cachedRdd.map((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "wc_alluxio_kdda_result_1")    // 3
    cachedRdd.saveAsTextFile (alluxio + "wc_kdda_med_result_1") // 4  - mediate rdd
    sc.textFile (alluxio + "wc_kdda_med_result_1")
      .map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "wc_alluxio_kdda_result_2")    // 5

    textFile = sc.textFile (hdfs + "kddb")
    textFile.count()

    // hdfs kddb
    textFile = sc.textFile (hdfs + "kddb")
    textFile.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .saveAsTextFile(hdfs + "wc_hdfs_kddb_result_1")           // 6
    textFile = sc.textFile (hdfs + "kddb")
    cachedRdd = textFile.flatMap (_.split (" ")).cache()
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "wc_hdfs_kddb_result_2")          // 7
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "wc_hdfs_kddb_result_3")          // 8

    // alluxio kddb
    textFile = sc.textFile (hdfs + "kddb")
    cachedRdd = textFile.flatMap (_.split (" "))
    cachedRdd.map((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "wc_alluxio_kddb_result_1")    // 9
    cachedRdd.saveAsTextFile (alluxio + "wc_kddb_med_result_1") // 10  - mediate rdd
    sc.textFile (alluxio + "wc_kddb_med_result_1")
      .map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "wc_alluxio_kddb_result_2")    // 11
  }
}
