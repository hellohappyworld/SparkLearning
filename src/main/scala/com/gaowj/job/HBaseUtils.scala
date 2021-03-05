package com.gaowj.job

import org.apache.hadoop.hbase.HBaseConfiguration

/**
  * created by gaowj.
  * created on 2021-03-03.
  * function: HBase配置类
  * origin -> 
  */
object HBaseUtils {
  def getHBaseConfiguration(quorum: String, port: String, tableName: String) = {
    val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum", quorum)
    conf.set("hbase.zookeeper.property.clientPort", port)
    conf
  }
}
