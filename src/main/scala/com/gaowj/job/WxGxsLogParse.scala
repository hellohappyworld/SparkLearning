package com.gaowj.job

import java.net.URLDecoder
import java.util

import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SaveMode, SparkSession}

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

/**
  * created by gaowj.
  * created on 2021-03-08.
  * function: 微信供销社离线解析逻辑
  * origin -> 
  */
object WxGxsLogParse {
  val wxsta_list: Array[String] = Array("datatype", "ip", "mos", "softversion", "publishid", "userkey", "ua", "net", "logintime", "session", "etc", "loginname", "shareroot", "shareref", "sharelv", "shareid", "sharefrom")
  val pattSessionnum: Regex = new Regex("#") // 匹配Session字符串中的‘#’字符

  def getMapData(line: String) = {
    val map = new util.HashMap[String, String]()
    val jsonObj: JSONObject = JSON.parseObject(line)
    map.put("ip", jsonObj.getString("realip"))
    map.put("datatype", "wx_gxs")
    val reqBody = jsonObj.getString("req_body")
    val reqBodyObj = JSON.parseObject(reqBody)
    val keySet: util.Set[String] = reqBodyObj.keySet()
    for (key <- keySet) {
      val v: String = reqBodyObj.getString(key).trim
      if ("".equals(v))
        map.put(key, "#")
      else
        map.put(key, v)
    }

    map
  }

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .config("spark.master", "yarn-cluster")
      .appName("WxGxsLogParse")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val inPath = args(0)
    val outPath = args(1)

    //    val sourceRDD = sc.textFile("file:///C:/workStation/wx_gxs/0350.1615146602358*")
    //    val sourceRDD = sc.textFile("file:///" + inPath)
    val sourceRDD = sc.textFile(inPath)
    //    sourceRDD.foreach(println)
    val resultRDD = sourceRDD.flatMap(input => {
      val result: ArrayBuffer[String] = ArrayBuffer()

      try {
        //判断日志是否正常
        val lineObj: JSONObject = JSON.parseObject(input)
        val isBool = if (lineObj.containsKey("realip") && lineObj.containsKey("request") && lineObj.containsKey("req_body")) {
          if (lineObj.getString("realip").trim.length > 0 && lineObj.getString("request").contains("datatype=wx_gxs")
            && JSON.parseObject(lineObj.getString("req_body")).keySet().size() > 0)
            true
          else
            false
        } else {
          false
        }
        if (!isBool)
          result.append("errLog1:" + input)
        else {
          //日志解析为KV
          val map = getMapData(input)
          //校对KV数据
          val buffer: ArrayBuffer[String] = ArrayBuffer()
          for (k <- map.keySet.toArray) {
            val value: String = map.get(k)
            if (k != "ua") {
              map.put(k.toString, URLDecoder.decode(value.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8"))
            } else {
              map.put(k.toString, URLDecoder.decode(URLDecoder.decode(value.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8").replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8"))
            }
            if (!wxsta_list.contains(k)) {
              val v: String = map.get(k)
              buffer.append(s"$k=$v")
            }
          }
          val paras: String = buffer.toArray.mkString("&")
          if (paras.length > 0) map.put("etc", paras)
          //匹配wxsta_list
          val tuple: ArrayBuffer[String] = ArrayBuffer()

          wxsta_list.foreach(key => {
            if (!"session".equals(key)) {
              val v: String = map.getOrDefault(key, "#")
              tuple.append(v)
            }
          })
          map.get("session").split("@").foreach(se => {
            val spliStr: String = pattSessionnum.findAllIn(se).mkString("").trim
            if (spliStr.length == 2) {
              val arr: Array[String] = se.trim.split("#")
              val buffer: ArrayBuffer[String] = ArrayBuffer()
              if (arr.length == 0) {
                buffer.append("#", "#", "#")
              } else if (arr.length == 1) {
                buffer.append(arr(0), "#", "#")
              } else if (arr.length == 2) {
                buffer.append(arr(0), arr(1), "#")
              } else if (arr.length >= 3 && arr(2).length == 0) {
                buffer.append(arr(0), arr(1), "#")
              } else if (arr.length >= 3 && arr(2).length != 0) {
                buffer.append(arr(0), arr(1), arr(2))
              }
              val sss: Array[String] = buffer.toArray
              val ts: Array[String] = Array.concat(tuple.toArray, sss)

              result.append(ts.mkString("\t"))
            }
          })
        }
      } catch {
        case ex: Exception =>
          ex.printStackTrace()
          result.append("errLog2:" + input)
      }

      result
    })

    //    resultRDD.foreach(println)
    val errOutRDD = resultRDD.filter(_.contains("errLog"))
    errOutRDD.saveAsTextFile(outPath + "/errOutLog")
    //    errOutRDD.foreach(println)
    val outRDD = resultRDD.filter(!_.contains("errLog"))
    //    outRDD.foreach(println)
    outRDD.saveAsTextFile(outPath + "/outLog")

    spark.stop()
  }
}
