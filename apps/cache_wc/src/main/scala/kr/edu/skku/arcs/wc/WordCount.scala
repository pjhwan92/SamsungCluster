package kr.edu.skku.arcs.wc

import org.apache.spark._
import org.apache.spark.rdd.RDD

/**
  * Created by pjh on 5/12/17.
  */
class WordCount {
  var sc: SparkContext = null

  def WordCount () {
    val hdfs = "hdfs://compute11:9000/user/arcs/"
    val alluxio = "hdfs://compute11:19998/"

    val conf = new SparkConf ().setAppName ("word count");
    var textFile: RDD[String] = null

    sc = new SparkContext (conf)

    textFile = sc.textFile (hdfs + "kdda")
    execute (textFile)
    execute (textFile)

    textFile = sc.textFile (alluxio + "kdda")
    execute (textFile)
    execute (textFile)

    textFile = sc.textFile (hdfs + "kddb")
    execute (textFile)
    execute (textFile)

    textFile = sc.textFile (alluxio + "kddb")
    execute (textFile)
    execute (textFile)
  }

  def execute (input: RDD[String]): Unit = {
    input.flatMap (line => line.split (" "))
         .map (word => (word, 1))
         .reduceByKey {case (x, y) => x + y}
  }

}
