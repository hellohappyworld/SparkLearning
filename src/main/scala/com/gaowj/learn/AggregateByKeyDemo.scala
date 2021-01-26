package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * created by gaowj.
  * created on 2021-01-26.
  * function:
  * origin -> 
  */
object AggregateByKeyDemo {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("ReduceByKeyDemo")
      .config("spark.master", "local")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val sourceRdd = sc.parallelize(Seq(("a", 1), ("a", 2), ("b", 2), ("b", 3)))
    val resRdd: RDD[(String, Int)] = sourceRdd.aggregateByKey(0)(
      (acc: Int, V: Int) => (acc + V),
      (acc1: Int, acc2: Int) => (acc1 + acc2)
    )
    resRdd.foreach(println)

    spark.stop()
  }
}
