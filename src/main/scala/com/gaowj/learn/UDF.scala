package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * created by gaowj.
  * created on 2021-02-18.
  * function: UDF(统计字段长度)(字段转成大写)
  * origin -> https://developer.aliyun.com/article/680259
  */
object UDF {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("UserDefinedTypedAggregation")
      .config("spark.master", "local[*]")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val ds: DataFrame = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\employees.json")
    ds.show()
    //    +-------+------+
    //    |   name|salary|
    //    +-------+------+
    //    |Michael|  3000|
    //      |   Andy|  4500|
    //      | Justin|  3500|
    //      |  Berta|  4000|
    //      +-------+------+
    spark.udf.register("strLength", (str: String) => str.length)
    ds.createOrReplaceTempView("employees")

    spark.sql("select name,salary,strLength(name) as name_length from employees").show(false)
    //    +-------+------+-----------+
    //    |name   |salary|name_length|
    //    +-------+------+-----------+
    //    |Michael|3000  |7          |
    //      |Andy   |4500  |4          |
    //      |Justin |3500  |6          |
    //      |Berta  |4000  |5          |
    //      +-------+------+-----------+

    import org.apache.spark.sql.functions._
    val strUpper: UserDefinedFunction = udf((str: String) => str.toUpperCase())
    import spark.implicits._
    ds.withColumn("toUpperCase", strUpper($"name")).show(false)
    //    +-------+------+-----------+
    //    |name   |salary|toUpperCase|
    //    +-------+------+-----------+
    //    |Michael|3000  |MICHAEL    |
    //      |Andy   |4500  |ANDY       |
    //      |Justin |3500  |JUSTIN     |
    //      |Berta  |4000  |BERTA      |
    //      +-------+------+-----------+

    spark.stop()
  }
}
