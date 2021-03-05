package com.gaowj.job

import org.apache.hadoop.hbase.KeyValue
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.{HFileOutputFormat2, TableInputFormat}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * created by gaowj.
  * created on 2021-03-03.
  * function: 读取HBase数据生成HFile文件
  * origin -> 
  */
object HBaseToHFile {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .config("spark.master", "yarn-cluster")
      .appName("HBaseToHFile")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val sourceTable = "tongji_doc_test"
    val quorum = "10.80.10.159"
    val port = "2181"

    val conf = HBaseUtils.getHBaseConfiguration(quorum, port, sourceTable)
    conf.set(TableInputFormat.INPUT_TABLE, sourceTable)

    val hBaseRdd: RDD[(ImmutableBytesWritable, Result)] = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[ImmutableBytesWritable],
      classOf[Result]).cache()

    val resultRdd: RDD[(ImmutableBytesWritable, KeyValue)] = hBaseRdd.map(tup => {
      val result = tup._2
      val key = Bytes.toString(result.getRow)
      val value = Bytes.toString(result.getValue("content".getBytes, "info".getBytes))
      (key, value)
    }).reduceByKey((x, y) => y)
      .map(tup => {
        val key = tup._1
        val value = tup._2
        val kv = new KeyValue(Bytes.toBytes(key), "content".getBytes, "info".getBytes, value.getBytes)
        (new ImmutableBytesWritable(Bytes.toBytes(key)), kv)
      }).sortBy(x => x._1, true)

    resultRdd.saveAsNewAPIHadoopFile("/tmp/hive_cp/tongji_doc_test/hfile", classOf[ImmutableBytesWritable], classOf[KeyValue], classOf[HFileOutputFormat2], conf)

    spark.stop()
  }
}
