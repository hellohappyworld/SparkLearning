package com.gaowj.learn

import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Aggregator


/**
  * created by gaowj.
  * created on 2021-02-05.
  * function:
  * origin -> 
  */
object UserDefinedTypedAggregation {

  case class Employee(name: String, salary: Long)

  case class Average(var sum: Long, var count: Long)

  object MyAverage extends Aggregator[Employee, Average, Double] {
    override def zero: Average = Average(0L, 0L)

    override def reduce(buffer: Average, employee: Employee): Average = {
      buffer.sum += employee.salary
      buffer.count += 1
      buffer
    }

    override def merge(b1: Average, b2: Average): Average = {
      b1.sum += b2.sum
      b1.count += b2.count
      b1
    }

    override def finish(reduction: Average): Double = reduction.sum.toDouble / reduction.count

    override def bufferEncoder: Encoder[Average] = Encoders.product

    override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
  }

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("UserDefinedTypedAggregation")
      .config("spark.master", "local[*]")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    import spark.implicits._
    val ds: Dataset[Employee] = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\employees.json")
      .as[Employee]
    ds.show()
    //    +-------+------+
    //    |   name|salary|
    //    +-------+------+
    //    |Michael|  3000|
    //      |   Andy|  4500|
    //      | Justin|  3500|
    //      |  Berta|  4000|
    //      +-------+------+
    val averageSalary: TypedColumn[Employee, Double] = MyAverage.toColumn.name("average_salary")
    val result: Dataset[Double] = ds.select(averageSalary)
    result.show()
    //    +--------------+
    //    |average_salary|
    //    +--------------+
    //    |        3750.0|
    //      +--------------+

    spark.stop()
  }
}
