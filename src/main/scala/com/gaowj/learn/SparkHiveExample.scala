package com.gaowj.learn

import java.io.File

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
  * created by gaowj.
  * created on 2021-02-08.
  * function:
  * origin -> Spark源码 1.4.0
  */
object SparkHiveExample {

  case class Record(key: Int, value: String)

  // When working with Hive, one must instantiate `SparkSession` with Hive support, including
  // connectivity to a persistent Hive metastore, support for Hive serdes, and Hive user-defined
  // functions. Users who do not have an existing Hive deployment can still enable Hive support.
  // When not configured by the hive-site.xml, the context automatically creates `metastore_db`
  // in the current directory and creates a directory configured by `spark.sql.warehouse.dir`,
  // which defaults to the directory `spark-warehouse` in the current directory that the spark
  // application is started.
  def main(args: Array[String]): Unit = {
    val warehouseLocation: String = new File("spark-warehouse").getAbsolutePath
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark Hive Example")
      .config("spark.master", "local[*]")
      .config("spark.driver.host", "localhost")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    //    sc.setLogLevel("ERROR")

    import spark.implicits._
    import spark.sql

    sql("create table if not exists src (key int,value string) using hive")
    sql("load data local inpath 'C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\kv1.txt' into table src")

    sql("select * from src").show()

    spark.stop()
  }

}
