package com.gaowj.learn

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, Partitioner, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.reflect.ClassTag

/**
  * created by gaowj.
  * created on 2021-01-27.
  * function:
  * origin -> 
  */
object SortByKeyDemo {
  // 打印分区数据分布情况
  def printPartAndElement[T: ClassTag](rdd: RDD[T]): Unit = {
    val parts: Array[Partition] = rdd.partitions
    for (p <- parts) {
      val partIndex = p.index
      val partRdd: RDD[T] = rdd.mapPartitionsWithIndex {
        case (index: Int, value: Iterator[T]) =>
          if (index == partIndex)
            value
          else
            Iterator()
      }
      println("分区id为：" + partIndex)
      partRdd.foreach(println)
    }
  }

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("CombineByKeyDemo")
      .config("spark.master", "local[*]")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val sourceRdd1: RDD[(String, Int)] = sc.parallelize(Seq(("a", 2), ("c", 27), ("f", 3), ("f", 0), ("f", 9),
      ("d", 3), ("d", 2), ("c", 9), ("a", 1), ("b", 2), ("b", 3), ("b", 3), ("c", 2)))
    val sortRdd1: RDD[(String, Int)] = sourceRdd1.sortByKey(false, 2) // 设置2个分区，倒序
    println("打印sortRdd1的元素分区情况：")
    printPartAndElement(sortRdd1)
    val sourceRdd2: RDD[((String, Int), Int)] = sc.parallelize(Seq((("a", 2), 9), (("c", 27), 3), (("f", 3), 4),
      (("f", 0), 0), (("f", 9), 1), (("d", 3), 7),
      (("d", 2), 2), (("c", 9), 12), (("a", 1), 4), (("b", 2), 34), (("b", 3), 1), (("b", 3), 6), (("c", 2), 8)))
    val sortRdd2: RDD[((String, Int), Int)] = sourceRdd2.sortByKey(false, 3) // 设置3个分区，倒序
    println("-------------------------------------------------")
    println("打印sortRdd2的元素分区情况：")
    printPartAndElement(sortRdd2)

    spark.stop()
  }
}
