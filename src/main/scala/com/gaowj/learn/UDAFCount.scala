package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * created by gaowj.
  * created on 2021-02-18.
  * function: UDAF 统计条数
  * origin -> https://developer.aliyun.com/article/680259
  */
object UDAFCount {

  object CustomerCount extends UserDefinedAggregateFunction {
    // 聚合函数的输入参数数据类型
    override def inputSchema: StructType = {
      StructType(StructField("inputColumn", StringType) :: Nil)
    }

    // 中间缓存的数据类型
    override def bufferSchema: StructType = {
      StructType(StructField("sum", LongType) :: Nil)
    }

    // 最终输出结果时的数据类型
    override def dataType: DataType = LongType

    // 聚合函数是否是幂等性的，即相同输入是否总是能得到相同输出
    override def deterministic: Boolean = true

    // 初始时候使用，即没有数据时的值
    // 初始值，要是DataSet没有数据，就放回该值
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0L
    }

    // 方法将每一行的数据进行计算，放到缓冲对象中
    // 相当于把当前分区的，每行数据都需要进行计算，计算的结果保存到buffer中
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      if (!input.isNullAt(0)) {
        buffer(0) = buffer.getLong(0) + 1
      }
    }

    // 把每个分区，缓冲对象进行合并
    // 相当于把每个分区的数据进行合并 buffer1:分区一的数据 buffer2:分区二的数据
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    }

    // 计算结果表达式，把缓冲对象中的数据进行最终计算
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
    df.createOrReplaceTempView("employees")
    df.show(false)
    //    +-------+------+
    //    |name   |salary|
    //    +-------+------+
    //    |Michael|3000  |
    //      |Andy   |4500  |
    //      |Justin |3500  |
    //      |Berta  |4000  |
    //      +-------+------+

    spark.udf.register("customerCount", CustomerCount)
    spark.sql("select customerCount(name) as count from employees").show(false)
    //    +-----+
    //    |count|
    //    +-----+
    //    |4    |
    //      +-----+

    spark.stop()
  }
}
