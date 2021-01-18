package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * created by gaowj.
  * created on 2021-01-18.
  * function:
  * origin -> 
  */
object ReduceByKeyDemo {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("ReduceByKeyDemo")
      .config("spark.master", "local")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    //demo 1
    val source: RDD[(Int, Int)] = sc.parallelize(Seq((1, 1), (1, 2), (2, 2), (2, 3)))
    val res: RDD[(Int, Int)] = source.reduceByKey(_ + _)
    res.foreach(println)
    println("------------")
    val groupByKeyRDD: RDD[(Int, Iterable[Int])] = source.groupByKey()
    groupByKeyRDD.map(tup => (tup._1, tup._2.sum)).foreach(println)

    // demo2
    val y: RDD[(String, Int, Int, Int, Int)] = sc.parallelize(List(
      ("key1", 1, 0, 2, 0),
      ("key1", 1, 0, 2, 0),
      ("key2", 1, 0, 2, 0),
      ("key3", 1, 0, 3, 0),
      ("key2", 1, 0, 3, 0)
    ))
    val byKey: RDD[(String, (Int, Int))] = y.map(
      { case (key, scrsrp, ncrsrp, l_scrsrp, l_ncrsrp) => (key) -> ((((l_scrsrp - l_ncrsrp) - (scrsrp - ncrsrp)) * ((l_scrsrp - l_ncrsrp) - (scrsrp - ncrsrp))), (1)) }
    )
    byKey.foreach(println)
    println("--------")
    byKey.reduceByKey((x1, x2) => (x1._1 + x2._1, x1._2 + x2._2)).foreach(println)

    spark.stop()
  }
}
