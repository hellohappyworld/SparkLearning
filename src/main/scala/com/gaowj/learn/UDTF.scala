package com.gaowj.learn

import java.util

import org.apache.hadoop.hive.ql.exec.UDFArgumentException
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory
import org.apache.hadoop.hive.serde2.objectinspector.{ObjectInspector, ObjectInspectorFactory, StructObjectInspector}
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * created by gaowj.
  * created on 2021-02-19.
  * function:
  * origin -> 
  */
object UDTF {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark SQL UDTF")
      .config("spark.master", "local[*]")
      .config("spark.driver.host", "localhost")
      .enableHiveSupport()
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val df: DataFrame = spark.read.json("C:\\workStation\\ProjectStation\\SparkLearning\\src\\main\\resources\\employeesUDTF.json")
    df.createOrReplaceTempView("student")
    df.show(false)
    //    +----------------+----+
    //    |class           |name|
    //      +----------------+----+
    //    |Hadoop scala    |zs  |
    //      |Hadoop kafka    |ls  |
    //      |spark hive sqoop|ww  |
    //    +----------------+----+
    spark.sql("""create temporary function udtf as 'com.gaowj.learn.MyUDTF'""")
    spark.sql("select name,udtf(class) from student").show(false)

    spark.stop()
  }
}

class MyUDTF extends GenericUDTF {
  override def initialize(argOIs: Array[ObjectInspector]): StructObjectInspector = {
    // 判断传入的参数是否只有一个
    if (argOIs.length != 1)
      throw new UDFArgumentException("有且只能有一个参数!")
    // 判断参数类型
    if (argOIs(0).getCategory != ObjectInspector.Category.PRIMITIVE)
      throw new UDFArgumentException("参数类型不匹配")

    val fieldNames = new util.ArrayList[String]()
    val fieldOIs = new util.ArrayList[ObjectInspector]()
    fieldNames.add("type")
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector)
    ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs)
  }

  override def process(objects: Array[AnyRef]): Unit = {
    // 将传入的数据拆分，形成一个Array数组
    val strings: Array[String] = objects(0).toString.split(" ")
    for (elem <- strings) {
      // 每次循环都创建一个新数组，长度为1
      val arr = new Array[String](1)
      arr(0) = elem
      // 显示出去，必须传入的是数组
      forward(arr)
    }
  }

  // 关闭方法
  override def close(): Unit = {}
}
