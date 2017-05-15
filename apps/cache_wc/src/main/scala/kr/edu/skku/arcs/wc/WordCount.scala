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
    var cachedRdd = textFile.flatMap (_.split (" "))
                            .cache()
    cachedRdd.map ((_, 1))
             .reduceByKey (_ + _)
             .saveAsTextFile (hdfs + "hdfs_kdda_result_1")
    cachedRdd.map ((_, 1))
             .reduceByKey (_ + _)
             .saveAsTextFile (hdfs + "hdfs_kdda_result_2")

    // alluxio kdda
    textFile = sc.textFile (alluxio + "kdda")
    cachedRdd = textFile.flatMap (_.split (" "))
    cachedRdd.map((_, 1))
             .reduceByKey (_ + _)
             .saveAsTextFile (alluxio + "alluxio_kdda_result_1")
    cachedRdd.saveAsTextFile (alluxio + "kdda_med_result_1")
    sc.textFile (alluxio + "kdda_med_result_1")
      .map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (alluxio + "alluxio_kdda_result_2")

    textFile = sc.textFile (hdfs + "kddb")
    cachedRdd = textFile.flatMap (_.split (" "))
      .cache()
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "hdfs_kddb_result_1")
    cachedRdd.map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (hdfs + "hdfs_kddb_result_2")


    textFile = sc.textFile (alluxio + "kddb")
    cachedRdd = textFile.flatMap (_.split (" "))
    cachedRdd.map((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (alluxio + "alluxio_kddb_result_1")
    cachedRdd.saveAsTextFile (alluxio + "kddb_med_result_1")
    sc.textFile (alluxio + "kddb_med_result_1")
      .map ((_, 1))
      .reduceByKey (_ + _)
      .saveAsTextFile (alluxio + "alluxio_kddb_result_2")
  }
}
