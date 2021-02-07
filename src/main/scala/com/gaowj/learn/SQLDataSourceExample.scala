package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
  * created by gaowj.
  * created on 2021-02-05.
  * function:
  * origin -> Spark源码 2.4.0
  */
object SQLDataSourceExample {

  case class Person(name: String, age: Long)

  def runBasicDataSourceExample(spark: SparkSession) = {
    val usersDF: DataFrame = spark.read.load("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\users.parquet")
    usersDF.show()
    //    +------+--------------+----------------+
    //    |  name|favorite_color|favorite_numbers|
    //    +------+--------------+----------------+
    //    |Alyssa|          null|  [3, 9, 15, 20]|
    //    |   Ben|           red|              []|
    //      +------+--------------+----------------+
    usersDF.select("name", "favorite_color").write.mode(SaveMode.Ignore).save("C:\\workStation\\Test\\namesAndFavColors.parquet")

    val peopleDF: DataFrame = spark.read.format("json").load("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\people.json")
    peopleDF.select("name", "age").write.format("parquet").mode(SaveMode.Ignore).save("C:\\workStation\\Test\\namesAndAges.parquet")

    val peopleDFCsv: DataFrame = spark.read.format("csv")
      //    +------------------+
      //    |               _c0|
      //    +------------------+
      //    |      name;age;job|
      //      |Jorge;30;Developer|
      //      |  Bob;32;Developer|
      //      +------------------+
      .option("sep", ";")
      //    +-----+---+---------+
      //    |  _c0|_c1|      _c2|
      //    +-----+---+---------+
      //    | name|age|      job|
      //    |Jorge| 30|Developer|
      //      |  Bob| 32|Developer|
      //      +-----+---+---------+
      .option("inferSchema", "true")
      //    +-----+---+---------+
      //    |  _c0|_c1|      _c2|
      //    +-----+---+---------+
      //    | name|age|      job|
      //    |Jorge| 30|Developer|
      //      |  Bob| 32|Developer|
      //      +-----+---+---------+
      .option("header", "true")
      //    +-----+---+---------+
      //    | name|age|      job|
      //    +-----+---+---------+
      //    |Jorge| 30|Developer|
      //      |  Bob| 32|Developer|
      //      +-----+---+---------+
      .load("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\people.csv")
    peopleDFCsv.show()

    usersDF.write.mode(SaveMode.Ignore).format("orc")
      .option("orc.bloom.filter.columns", "favorite_color")
      .option("orc.dictionary.key.threshold", "1.0")
      .option("orc.column.encoding.direct", "name")
      .save("C:\\workStation\\Test\\users_with_options.orc")

    val sqlDF: DataFrame = spark.sql("select * from parquet.`C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\users.parquet`")
    sqlDF.show()
    //    +------+--------------+----------------+
    //    |  name|favorite_color|favorite_numbers|
    //    +------+--------------+----------------+
    //    |Alyssa|          null|  [3, 9, 15, 20]|
    //    |   Ben|           red|              []|
    //      +------+--------------+----------------+

    peopleDF.write.bucketBy(42, "name").sortBy("age").saveAsTable("people_bucketed")
    spark.sql("select * from people_bucketed").show()
    //    +----+-------+
    //    | age|   name|
    //    +----+-------+
    //    |null|Michael|
    //      |  30|   Andy|
    //      |  19| Justin|
    //      +----+-------+

    usersDF.write.mode(SaveMode.Ignore).partitionBy("favorite_color").format("parquet").save("C:\\workStation\\Test\\namesPartByColor.parquet")

    spark.sql("DROP TABLE IF EXISTS people_bucketed")
  }

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark SQL data sources example")
      .config("spark.master", "local[*]")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    runBasicDataSourceExample(spark)

    spark.stop()
  }

}
