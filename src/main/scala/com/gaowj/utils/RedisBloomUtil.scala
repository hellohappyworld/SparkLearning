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
    val key = args(2)
    val value = args(3)
    val partition = args(4).toInt
    //    val dbSingleArgs = args(5).toInt
    val dbTenArgs = args(5).toInt

    //单节点写13的布隆库
    session.sql(sql).toJSON.rdd.coalesce(partition).foreachPartition(datas => {
      for (data <- datas) {
        val dataJson = new JSONObject(data)
        val k = dataJson.getString(key)
        val v = dataJson.getString(value)
        //判断用户是python用户还是java用户
        //        val dbSingle = dbSingleArgs;
        val dbTen = dbTenArgs
        var jedis: Jedis = null
        /*val uidHcode = Math.abs(k.hashCode)
        val num = uidHcode % 100
        if (num > 94 || k.startsWith("debugcoldpy") || k.startsWith("debugcoldtest")) {
          //python用户
          jedis = RedisPool.getJedisPool13
          jedis.select(dbSingle)
        } else {*/
        //java用户
        val crc32 = new CRC32
        crc32.update(k.getBytes)
        val nUser = Math.abs(crc32.getValue.toInt % 10)
        nUser match {
          case 0 =>
            jedis = RedisPool.getJedis14_7002_BL(dbTen)
          case 1 =>
            jedis = RedisPool.getJedis15_7002_BL(dbTen)
          case 2 =>
            jedis = RedisPool.getJedis16_7002_BL(dbTen)
          case 3 =>
            jedis = RedisPool.getJedis17_7002_BL(dbTen)
          case 4 =>
            jedis = RedisPool.getJedis18_7002_BL(dbTen)
          case 5 =>
            jedis = RedisPool.getJedis19_7002_BL(dbTen)
          case 6 =>
            jedis = RedisPool.getJedis14_7001_BL(dbTen)
          case 7 =>
            jedis = RedisPool.getJedis15_7001_BL(dbTen)
          case 8 =>
            jedis = RedisPool.getJedis16_7001_BL(dbTen)
          case 9 =>
            jedis = RedisPool.getJedis17_7001_BL(dbTen)
        }
        //      }

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
