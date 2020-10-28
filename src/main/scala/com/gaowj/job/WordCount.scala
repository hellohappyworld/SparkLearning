package com.gaowj.job

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * created by gaowj.
  * created on 2020-09-28.
  * function:
  */
object WordCount {
  def main(args: Array[String]): Unit = {
    val path = args(0)

    val conf = new SparkConf()
    conf.setAppName("WordCount")

    val sc = new SparkContext(conf)
    val fileRdd = sc.textFile(path)
    val filterRdd = fileRdd.filter(line => (
      if (line.contains("newsapp") && !line.contains("2851") && !Array("pageinfo", "adinfo", "btslist", "inloc").contains(line))
        true
      else
        false
      ))

    println(path + "路径下符合条件count值:" + filterRdd.count())
    sc.stop()
  }
}
