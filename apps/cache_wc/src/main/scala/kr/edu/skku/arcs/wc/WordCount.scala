package kr.edu.skku.arcs.wc

import java.io.{FileOutputStream, PrintWriter}
import java.nio.file.{Files, Paths}

import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer

/**
  * Created by pjh on 5/12/17.
  */
class WordCount {
  var sc: SparkContext = null

  def wc (num: String) {
    val hdfs = "hdfs://compute11:9000/user/arcs/"
    val alluxio = "alluxio://compute11:19998/"
    val localPrefix = System.currentTimeMillis.toString
    //val files = List ("input_200", "input_300", "input_400", "input_500")
    val file = "input_" + num
    //val times: Map[String, ListBuffer[String]] = files.map ((_, ListBuffer[String] ())).toMap
    val times: ListBuffer[String] = new ListBuffer[String]()

    //val conf = new SparkConf ().setAppName ("word count");
    val spark = SparkSession.builder.appName ("word count " + num).getOrCreate ()
    var textFile, cachedRdd: RDD[String] = null

    var start : Long = 0L

    sc = spark.sparkContext

    //for (file <- files) {
      textFile = sc.textFile (hdfs + file)
      textFile.count()

      // hdfs
      /*start = System.currentTimeMillis ()
      textFile = sc.textFile(hdfs + file)
      textFile.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_hdfs_" + file + "_result_1") // 0
      times += (System.currentTimeMillis () - start).toString

      start = System.currentTimeMillis ()
      cachedRdd = sc.textFile(hdfs + file).cache()
      cachedRdd.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_hdfs_" + file + "_result_2") // 1
      times += (System.currentTimeMillis () - start).toString

      start = System.currentTimeMillis ()
      cachedRdd.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_hdfs_" + file + "_result_3") // 2
      times += (System.currentTimeMillis () - start).toString
			*/

      // alluxio
      start = System.currentTimeMillis ()
      textFile = sc.textFile(hdfs + file)
      textFile.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_alluxio_" + file + "_result_1") // 3
      times += (System.currentTimeMillis () - start).toString

      start = System.currentTimeMillis ()
      sc.textFile(hdfs + file).saveAsTextFile(alluxio + "wc_" + file + "_med_result_1")   //5
      cachedRdd = sc.textFile(alluxio + "wc_" + file + "_med_result_1")
      cachedRdd.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_alluxio_" + file + "_result_2") // 5
      times += (System.currentTimeMillis () - start).toString

      start = System.currentTimeMillis ()
      cachedRdd.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
        .saveAsTextFile(hdfs + "wc_alluxio_" + file + "_result_3") // 5
      times += (System.currentTimeMillis () - start).toString
    //}

    Files.createFile (Paths.get ("test_result/" + num + "_" + localPrefix))
    new PrintWriter (new FileOutputStream (localPrefix, true)) {
      write (times.mkString ("\t") + "\n")
      close ()
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
    /*textFile = sc.textFile (hdfs + "kddb")
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
			*/
  }
}
