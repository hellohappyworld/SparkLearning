package com.gaowj.learn

import java.util.Properties

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

  def runGenericFileSourceOptionsExample(spark: SparkSession) = {
    spark.sql("set spark.sql.files.ignoreCorruptFiles=true") // 当下述读取parquet文件时可以忽略不是parquet格式的文件
    val testCorruptDF: DataFrame = spark.read.parquet(
      "C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\dir1",
      "C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\dir1\\dir2"
    )
    testCorruptDF.show()
    //    +-------------+
    //    |         file|
    //    +-------------+
    //    |file1.parquet|
    //      |file2.parquet|
    //      +-------------+
    val recursiveLoadedDF: DataFrame = spark.read.format("parquet")
      .option("recursiveFileLookup", "true")
      .load("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\dir1")
    recursiveLoadedDF.show()
    //    +-------------+
    //    |         file|
    //    +-------------+
    //    |file1.parquet|
    //      +-------------+
    spark.sql("set spark.sql.files.ignoreCorruptFiles=false")
    val testGlobFilterDF: DataFrame = spark.read.format("parquet")
      .option("pathGlobFilter", "*.parquet") // json file should be filtered out
      .load("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\dir1")
    testGlobFilterDF.show()
  }

  def runBasicParquetExample(spark: SparkSession) = {
    import spark.implicits._

    val peopleDF: DataFrame = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\people.json")
    peopleDF.write.mode(SaveMode.Ignore).parquet("C:\\workStation\\Test\\people.parquet")

    val parquetFileDF: DataFrame = spark.read.parquet("C:\\workStation\\Test\\people.parquet")
    parquetFileDF.createOrReplaceTempView("parquetFile")
    spark.sql("select * from parquetFile").show()
    //    +----+-------+
    //    | age|   name|
    //    +----+-------+
    //    |null|Michael|
    //      |  30|   Andy|
    //      |  19| Justin|
    //      +----+-------+
    val namesDF: DataFrame = spark.sql("select name from parquetFile where age between 13 and 19")
    namesDF.map(attributes => "Name: " + attributes(0)).show()
    //    +------------+
    //    |       value|
    //    +------------+
    //    |Name: Justin|
    //      +------------+
    val nameAndAgeDF: DataFrame = spark.sql("select * from parquetFile")
    nameAndAgeDF.map(tup => "name:" + tup(0) + " age:" + tup(1)).show(false)
    //    +---------------------+
    //    |value                |
    //    +---------------------+
    //    |name:null age:Michael|
    //      |name:30 age:Andy     |
    //      |name:19 age:Justin   |
    //      +---------------------+
  }

  def runParquetSchemaMergingExample(spark: SparkSession) = {
    import spark.implicits._

    val squaresDF: DataFrame = spark.sparkContext.makeRDD(1 to 5).map(i => (i, i * i)).toDF("value", "square")
    squaresDF.write.mode(SaveMode.Ignore).parquet("C:\\workStation\\Test\\data/test_table/key=1")

    val cubesDF: DataFrame = spark.sparkContext.makeRDD(6 to 10).map(i => (i, i * i * i)).toDF("value", "cube")
    cubesDF.write.mode(SaveMode.Ignore).parquet("C:\\workStation\\Test\\data/test_table/key=2")

    val mergedDF: DataFrame = spark.read
      .option("mergeSchema", "true")
      .parquet("C:\\workStation\\Test\\data/test_table")
    mergedDF.printSchema()
    mergedDF.show(false)
    //    root
    //    |-- value: integer (nullable = true)
    //    |-- square: integer (nullable = true)
    //    |-- cube: integer (nullable = true)
    //    |-- key: integer (nullable = true)
    //
    //    +-----+------+----+---+
    //    |value|square|cube|key|
    //    +-----+------+----+---+
    //    |1    |1     |null|1  |
    //      |2    |4     |null|1  |
    //      |3    |9     |null|1  |
    //      |4    |16    |null|1  |
    //      |5    |25    |null|1  |
    //      |6    |null  |216 |2  |
    //      |7    |null  |343 |2  |
    //      |8    |null  |512 |2  |
    //      |9    |null  |729 |2  |
    //      |10   |null  |1000|2  |
    //      +-----+------+----+---+
  }

  def runJsonDatasetExample(spark: SparkSession) = {
    import spark.implicits._

    val peopleDF: DataFrame = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\people.json")
    peopleDF.printSchema()
    //    root
    //    |-- age: long (nullable = true)
    //    |-- name: string (nullable = true)

    peopleDF.createOrReplaceTempView("people")
    val teenagerNamesDF: DataFrame = spark.sql("select * from people")
    teenagerNamesDF.show()
    //    +----+-------+
    //    | age|   name|
    //    +----+-------+
    //    |null|Michael|
    //      |  30|   Andy|
    //      |  19| Justin|
    //      +----+-------+
    val otherPeopleDataset: Dataset[String] = spark.createDataset(
      """{"name":"Yin","address":{"city":"Columbus","state":"Ohio"}}""" ::
        """{"name":"Yin","address":{"city":"Columbus","state":"Ohio"}}""" ::
        """{"name":"Yin","address":{"city":"Columbus","state":"Ohio"},"other":"test"}""" :: Nil)
    val otherPeople: DataFrame = spark.read.json(otherPeopleDataset)
    otherPeople.show(false)
    //    +----------------+----+-----+
    //    |address         |name|other|
    //    +----------------+----+-----+
    //    |[Columbus, Ohio]|Yin |null |
    //      |[Columbus, Ohio]|Yin |null |
    //      |[Columbus, Ohio]|Yin |test |
    //      +----------------+----+-----+
  }

  def runJdbcDatasetExample(spark: SparkSession) = {
    val jdbcDF: DataFrame = spark.read
      .format("jdbc")
      .option("url", "jdbc:postgresql:dbserver")
      .option("dbtable", "schema.tablename")
      .option("user", "username")
      .option("password", "password")
      .load()

    val properties: Properties = new Properties()
    properties.put("user", "username")
    properties.put("password", "password")
    val jdbcDF2: DataFrame = spark.read
      .jdbc("jdbc:postgresql:dbserver", "schema.tablename", properties)
    properties.put("customSchema", "id DECIMAL(38,0), name STRING")
    val jdbcDF3: DataFrame = spark.read
      .jdbc("jdbc.postgresql:dbserver", "schema.tablename", properties)

    jdbcDF.write
      .format("jdbc")
      .option("url", "jdbc:postgresql:dbserver")
      .option("dbtable", "schema.tablename")
      .option("user", "username")
      .option("password", "password")
      .save()

    jdbcDF2.write
      .jdbc("jdbc:postgresql:dbserver", "schema.tablename", properties)

    jdbcDF3.write
      .option("createTableColumnTypes", "name CHAR(64), comments VARCHAR(1024)")
      .jdbc("jdbc:postgresql:dbserver", "schema.tablename", properties)
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

    //    runBasicDataSourceExample(spark)
    //    runGenericFileSourceOptionsExample(spark)
    //    runBasicParquetExample(spark)
    //    runParquetSchemaMergingExample(spark)
    //    runJsonDatasetExample(spark)
    runJdbcDatasetExample(spark)

    spark.stop()
  }

}
