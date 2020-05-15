package com.gaowj.utils

import java.lang
import java.util.zip.CRC32

import io.rebloom.client.Command
import org.json.JSONObject
import org.slf4j.LoggerFactory
import redis.clients.jedis.{Client, Jedis}


/**
  * created by gaowj.
  * created on 2020-05-06.
  * function:
  */
object RedisBloomUtil {
  val logger = LoggerFactory.getLogger("RedisBloomUtil")

  /**
    *
    * @param args 0.hiveSql  1.redis host 2.redis port 3.redis auth 4.redis DB
    *             5.key name 6.value name 7.bulkSize
    *             8.EXPIRE -1 为不设置
    *             9.操作类型 支持 set sadd lpush del(当使用del时，需要虚拟一个value)
    *             10.分区数量
    */
  def main(args: Array[String]): Unit = {
    val sparkUtil = new SparkUtil
    val ss = sparkUtil.getContext(args(1))
    val sc = ss._1
    val session = ss._2
    val sql = args(0)
    val redisHost = args(1)
    val redisPort = args(2).toInt
    val redisAuth = args(3)
    val redisDB = args(4).toInt
    val key = args(5)
    val value = args(6)
    val bulksize = args(7).toInt
    val expire = args(8).toInt
    val command = args(9)
    val partition = args(10).toInt

    //单节点写13的布隆库
    session.sql(sql).toJSON.rdd.coalesce(partition).foreachPartition(datas => {
      //      var jedis = RedisPool.getJedisPool()
      //      jedis.select(redisDB)
      //      val client = jedis.getClient
      for (data <- datas) {
        val dataJson = new JSONObject(data)
        val k = dataJson.getString(key)
        val v = dataJson.getString(value)

        val crc32 = new CRC32
        crc32.update(k.getBytes)
        val nUser = Math.abs(crc32.getValue.toInt % 10)
        val db = 1;
        var jedis: Jedis = null
        nUser match {
          case 0 =>
            jedis = RedisPool.getJedis121_7002(db)
          case 1 =>
            jedis = RedisPool.getJedis122_7002(db)
          case 2 =>
            jedis = RedisPool.getJedis123_7002(db)
          case 3 =>
            jedis = RedisPool.getJedis124_7002(db)
          case 4 =>
            jedis = RedisPool.getJedis125_7002(db)
          case 5 =>
            jedis = RedisPool.getJedis126_7002(db)
          case 6 =>
            jedis = RedisPool.getJedis121_7001(db)
          case 7 =>
            jedis = RedisPool.getJedis122_7001(db)
          case 8 =>
            jedis = RedisPool.getJedis123_7001(db)
          case 9 =>
            jedis = RedisPool.getJedis124_7001(db)
        }

        try {
          val longTtl = jedis.ttl(k)
          if (-2L == longTtl) {
            jedis.sendCommand(Command.RESERVE, k, "0.02", "100000")
            jedis.expire(k, 86400 * 90)
          }
          jedis.sendCommand(Command.ADD, k, v)
        } catch {
          case e: Exception => {
            e.printStackTrace()
          }
        } finally {
          jedis.close()
        }
      }
    }
    )

    session.stop()
  }

}
