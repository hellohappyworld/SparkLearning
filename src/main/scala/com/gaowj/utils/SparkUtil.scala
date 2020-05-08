package com.gaowj.utils

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession


/**
  * created by gaowj.
  * created on 2020-05-06.
  * function:
  */
class SparkUtil {
  def getContext(appName: String): (SparkContext, SparkSession) = {
    System.setProperty("HADOOP_USER_NAME", "prod")
    val session = SparkSession
      .builder()
      .appName(appName)
      .enableHiveSupport()
      .config("spark.debug.maxToStringFields", "100")
      .config("spark.shuffle.consolidateFiles", "true")
      .config("spark.rdd.compress", "true")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .config("spark.speculation", "true")
      .config("spark.kryoserializer.buffer.max", "1024m")
      .config("spark.scheduler.listenerbus.eventqueue.size", "100000")
      //      .config("es.nodes", "10.80.29.157,10.80.30.157,10.80.31.157,10.80.32.157") //es的节点，多个用逗号分隔
      //      .config("es.port", "9200") //端口号
      //      .config("es.index.auto.create", "true")
      //      .config("es.nodes.wan.only","false")
      .config("spark.driver.maxResultSize", "2g")
      .config("spark.shuffle.file.buffer", "128k")
      .config("spark.default.parallelism", "800")
      .config("spark.yarn.executor.memoryOverhead", "4096")
      .config("spark.driver.maxResultSize", "5G")
      .config("spark.sql.autoBroadcastJoinThreshold", "200000000")
      .getOrCreate()
    val conf = session.conf
    val sc = session.sparkContext
    sc.setLogLevel("ERROR")


    println(s"spark $appName init @ prod")
    (sc, session)


    /*System.setProperty("HADOOP_USER_NAME", "prod")
    val session = SparkSession
      .builder()
      .appName(appName)
      .enableHiveSupport()
      .config("spark.debug.maxToStringFields", "100")
      .config("spark.shuffle.consolidateFiles", "true")
      .config("spark.rdd.compress", "true")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .config("spark.speculation", "true")
      .config("spark.akka.frameSize", "100")
      .config("spark.shuffle.manager", "SORT")
      .config("spark.kryoserializer.buffer.max", "1024m")
      .config("spark.scheduler.listenerbus.eventqueue.size", "100000")
      //      .config("es.nodes", "10.80.29.157,10.80.30.157,10.80.31.157,10.80.32.157") //es的节点，多个用逗号分隔
      //      .config("es.port", "9200") //端口号
      //      .config("es.index.auto.create", "true")
      //      .config("es.nodes.wan.only","false")
      .config("spark.driver.maxResultSize", "8g")
      .config("spark.shuffle.file.buffer", "128k")
      .config("spark.default.parallelism", "1000")
      .config("spark.yarn.executor.memoryOverhead", "10960")
      .config("spark.driver.maxResultSize", "7G")
      .config("spark.sql.autoBroadcastJoinThreshold", "200000000")
      .getOrCreate()
    val conf = session.conf
    val sc = session.sparkContext
    sc.setLogLevel("ERROR")


    println(s"spark $appName init @ prod")
    (sc, session)*/
  }

}
