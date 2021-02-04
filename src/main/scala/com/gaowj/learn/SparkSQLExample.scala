package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * created by gaowj.
  * created on 2021-02-03.
  * function:
  * origin -> Spark源码 2.4.0
  */
object SparkSQLExample {

  case class Person(name: String, age: Long)

  def runBasicDataFrameExample(spark: SparkSession) = {
    val df: DataFrame = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\people.json")
    df.show()
    //    +----+-------+
    //    | age|   name|
    //    +----+-------+
    //    |null|Michael|
    //      |  30|   Andy|
    //      |  19| Justin|
    //      +----+-------+
    import spark.implicits._
    df.printSchema()
    //    root
    //    |-- age: long (nullable = true)
    //    |-- name: string (nullable = true)
    df.select("name").show()
    //    +-------+
    //    |   name|
    //    +-------+
    //    |Michael|
    //    |   Andy|
    //    | Justin|
    //    +-------+
    df.select($"name", $"age" + 1).show()
    //    +-------+---------+
    //    |   name|(age + 1)|
    //      +-------+---------+
    //    |Michael|     null|
    //      |   Andy|       31|
    //      | Justin|       20|
    //      +-------+---------+
    df.filter($"age" > 21).show()
    //    +---+----+
    //    |age|name|
    //    +---+----+
    //    | 30|Andy|
    //      +---+----+
    df.groupBy("age").count().show()
    //    +----+-----+
    //    | age|count|
    //    +----+-----+
    //    |  19|    1|
    //      |null|    1|
    //      |  30|    1|
    //      +----+-----+
    df.createOrReplaceTempView("people")
    val sqlDF: DataFrame = spark.sql("select * from people")
    sqlDF.show()
    //    +----+-------+
    //    | age|   name|
    //    +----+-------+
    //    |null|Michael|
    //      |  30|   Andy|
    //      |  19| Justin|
    //      +----+-------+
    df.createGlobalTempView("people")
    spark.sql("select * from global_temp.people").show()
    //    +----+-------+
    //    | age|   name|
    //    +----+-------+
    //    |null|Michael|
    //      |  30|   Andy|
    //      |  19| Justin|
    //      +----+-------+
    spark.newSession().sql("select * from global_temp.people").show()
    //    +----+-------+
    //    | age|   name|
    //    +----+-------+
    //    |null|Michael|
    //      |  30|   Andy|
    //      |  19| Justin|
    //      +----+-------+
  }

  def runDatasetCreationExample(spark: SparkSession) = {
    import spark.implicits._
    val caseClassDS: Dataset[Person] = Seq(Person("Andy", 32)).toDS()
    caseClassDS.show()
    //    +----+---+
    //    |name|age|
    //    +----+---+
    //    |Andy| 32|
    //      +----+---+
    val primitiveDS: Dataset[Int] = Seq(1, 2, 3).toDS()
    primitiveDS.show()
    //    +-----+
    //    |value|
    //    +-----+
    //    |    1|
    //      |    2|
    //      |    3|
    //      +-----+
    val ints: Array[Int] = primitiveDS.map(_ + 1).collect()

    val path: String = "C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\people.json"
    val peopleDS: Dataset[Person] = spark.read.json(path).as[Person]
    peopleDS.show()
    //    +----+-------+
    //    | age|   name|
    //    +----+-------+
    //    |null|Michael|
    //      |  30|   Andy|
    //      |  19| Justin|
    //      +----+-------+
  }

  def runInferSchemaExample(spark: SparkSession) = {
    // For implicit conversions from RDDs to DataFrames
    import spark.implicits._
    val peopleDF: DataFrame = spark.sparkContext
      .textFile("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\people.txt")
      .map(_.split(","))
      .map(arr => Person(arr(0), arr(1).trim.toInt))
      .toDF()
    peopleDF.show()
  }

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local[*]")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    //    runBasicDataFrameExample(spark)
    //    runDatasetCreationExample(spark)
    runInferSchemaExample(spark)

    spark.stop()
  }
}
