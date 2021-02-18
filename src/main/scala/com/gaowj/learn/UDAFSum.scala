package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, StructField, StructType}

/**
  * created by gaowj.
  * created on 2021-02-18.
  * function: UDAF 累加值
  * origin -> https://developer.aliyun.com/article/680259
  */
object UDAFSum {

  object CustomerSum extends UserDefinedAggregateFunction {
    // 聚合函数的输入参数数据类型
    override def inputSchema: StructType = {
      StructType(StructField("inputColumn", LongType) :: Nil)
    }

    // 中间缓存的数据类型
    override def bufferSchema: StructType = {
      StructType(StructField("sum", LongType) :: Nil)
    }

    // 最终输出结果的数据类型
    override def dataType: DataType = LongType

    override def deterministic: Boolean = true

    // 初始值，DataSet没有数据就返回该值
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0L
    }

    // 将当前分区的每行数据进行计算，计算结果保存到buffer中
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      if (!input.isNullAt(0)) {
        buffer(0) = buffer.getLong(0) + input.getLong(0)
      }
    }

    // 将每个分区的数据进行汇总 buffer1:分区一的数据 buffer2:分区二的数据
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    }

    override def evaluate(buffer: Row): Long = buffer.getLong(0)
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

    val df: DataFrame = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\employees.json")
    df.show()
    //    +-------+------+
    //    |   name|salary|
    //    +-------+------+
    //    |Michael|  3000|
    //      |   Andy|  4500|
    //      | Justin|  3500|
    //      |  Berta|  4000|
    //      +-------+------+
    spark.udf.register("customerSum", CustomerSum)
    df.createOrReplaceTempView("employees")
    spark.sql("select customerSum(salary) as sum from employees").show(false)
    //    +-----+
    //    |sum  |
    //    +-----+
    //    |15000|
    //      +-----+

    spark.stop()
  }
}
