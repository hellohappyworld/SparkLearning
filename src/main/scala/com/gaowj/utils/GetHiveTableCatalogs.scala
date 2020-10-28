package com.gaowj.utils

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * created by gaowj.
  * created on 2020-08-10.
  * function: 获取Hive表结构
  */
object GetHiveTableCatalogs {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .config("spark.driver.host", "localhost")
      .appName("GetHiveTableCatalogs")
      .getOrCreate()
    val frame: Dataset[String] = spark.read.textFile("C:\\tengxunyun")
    frame.show(5)
    spark.stop()
  }
}
