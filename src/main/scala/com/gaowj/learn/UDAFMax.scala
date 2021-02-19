package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, StructField, StructType}

/**
  * created by gaowj.
  * created on 2021-02-19.
  * function: UDAF (group by max) 按性别分组统计收入最高是多少，即统计男，女，各收入最高是多少
  * origin -> https://developer.aliyun.com/article/680259
  */
object UDAFMax {

  object CustomerMax extends UserDefinedAggregateFunction {
    // 聚合函数的输入参数数据类型
    override def inputSchema: StructType = {
      StructType(StructField("inputColumn", LongType) :: Nil)
    }

    // 中间缓存的数据类型
    override def bufferSchema: StructType = {
      StructType(StructField("sum", LongType) :: StructField("count", LongType) :: Nil)
    }

    // 最终输出结果的数据类型
    override def dataType: DataType = LongType

    // 聚合函数是否是幂等性的，即相同输入是否总是能得到相同输出
    override def deterministic: Boolean = true

    // 初始值，要是DataSet没有数据，就返回该值
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0L
    }

    // 相当于把当前分区的每行数据进行计算，并将计算的结果保存到buffer中
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      if (!input.isNullAt(0)) {
        if (input.getLong(0) > buffer.getLong(0)) {
          buffer(0) = input.getLong(0)
        }
      }
    }

    // 相当于将每个分区的数据进行汇总 buffer1:分区一的数据 buffer2:分区二的数据
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      if (buffer2.getLong(0) > buffer1.getLong(0))
        buffer1(0) = buffer2.getLong(0)
    }

    // 计算最终的结果
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

    spark.udf.register("customerMax", CustomerMax)

    val df: DataFrame = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\employeesCN.json")
    df.createOrReplaceTempView("employees")
    df.show(false)
    //    +------+----+------+
    //    |gender|name|salary|
    //    +------+----+------+
    //    |男    |小王|30000 |
    //      |女    |小丽|50000 |
    //      |男    |小军|80000 |
    //      |女    |小李|90000 |
    //      +------+----+------+
    val sqlDF: DataFrame = spark.sql("select gender,customerMax(salary) as max from employees group by gender")
    sqlDF.show(false)
    //    +------+-----+
    //    |gender|max  |
    //    +------+-----+
    //    |男    |80000|
    //      |女    |90000|
    //      +------+-----+

    spark.stop()
  }
}
