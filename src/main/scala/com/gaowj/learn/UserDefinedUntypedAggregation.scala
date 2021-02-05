package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.sql.expressions.{Aggregator, MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql._

/**
  * created by gaowj.
  * created on 2021-02-04.
  * function:
  * origin -> 
  */
object UserDefinedUntypedAggregation {

  object MyAverage extends UserDefinedAggregateFunction {
    override def inputSchema: StructType = StructType(StructField("inputColumn", LongType) :: Nil)

    override def bufferSchema: StructType = {
      StructType(StructField("sum", LongType) :: StructField("count", LongType) :: Nil)
    }

    override def dataType: DataType = DoubleType

    override def deterministic: Boolean = true

    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0L
      buffer(1) = 0L
    }

    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      if (!input.isNullAt(0)) {
        buffer(0) = buffer.getLong(0) + input.getLong(0)
        buffer(1) = buffer.getLong(1) + 1
      }
    }

    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
      buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
    }

    override def evaluate(buffer: Row): Double = buffer.getLong(0).toDouble / buffer.getLong(1)
  }


  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark SQL user-defined DataFrames aggregation example")
      .config("spark.master", "local[*]")
      .config("spark.driver.host", "localhost")
      .getOrCreate()

    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    spark.udf.register("myAverage", MyAverage)

    val df: DataFrame = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\employees.json")
    df.createOrReplaceTempView("employees")
    df.show()
    //    +-------+------+
    //    |   name|salary|
    //    +-------+------+
    //    |Michael|  3000|
    //      |   Andy|  4500|
    //      | Justin|  3500|
    //      |  Berta|  4000|
    //      +-------+------+
    val result: DataFrame = spark.sql("select myAverage(salary) as average_salary from employees")
    result.show()
    //    +--------------+
    //    |average_salary|
    //    +--------------+
    //    |        3750.0|
    //      +--------------+

    spark.stop()
  }
}
