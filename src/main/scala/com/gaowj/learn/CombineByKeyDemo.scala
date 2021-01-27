package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * created by gaowj.
  * created on 2021-01-15.
  * function: combinebykey基础用法
  * origin -> https://www.jianshu.com/p/d7552ea4f882
  */

case class Juice(volumn: Int) {
  def add(j: Juice): Juice = Juice(volumn + j.volumn)
  def getV: Int = volumn
}
case class Fruit(kind: String, weight: Int) {
  def makeJuice: Juice = Juice(weight)
}
object CombineByKeyDemo {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("CombineByKeyDemo")
      .config("spark.master", "local")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val apple1 = Fruit("apple", 5)
    val apple2 = Fruit("apple", 8)
    val orange1 = Fruit("orange", 10)

    val fruit: RDD[(String, Fruit)] = sc.parallelize(List(("apple", apple1), ("orange", orange1), ("apple", apple2)))
    val juice: RDD[(String, Juice)] = fruit.combineByKey(
      (v: Fruit) => v.makeJuice,
      (c: Juice, v: Fruit) => c.add(v.makeJuice),
      (c1: Juice, c2: Juice) => c1.add(c2)
    )
    val res: Array[(String, Juice)] = juice.collect()
    res.foreach(tup => {
      println(tup._1, tup._2.getV)
    })

    spark.stop()
  }
}




