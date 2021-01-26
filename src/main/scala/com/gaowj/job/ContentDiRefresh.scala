package com.gaowj.job

import com.alibaba.fastjson.JSON
import com.gaowj.utils.SparkUtilRefresh
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row}

/**
  * created by gaowj.
  * created on 2021-01-25.
  * function: 内容增量表(cdm.dim_content_di)错误日志刷新
  */
object ContentDiRefresh {
  def getResponse(url: String, header: String = null): String = {
    val httpClient = HttpClients.createDefault() // 创建 client 实例
    val get = new HttpGet(url) // 创建 get 实例

    if (header != null) { // 设置 header
      val json = JSON.parseObject(header)
      json.keySet().toArray.map(_.toString).foreach(key => get.setHeader(key, json.getString(key)))
    }

    val response = httpClient.execute(get) // 发送请求
    EntityUtils.toString(response.getEntity) // 获取返回结果
  }

  def main(args: Array[String]): Unit = {
    //获取spark实例
    val dtStr = args(0)
    val exeType = args(1)
    val sparkUtil = new SparkUtilRefresh
    val ss = sparkUtil.getContext(exeType)
    val sc = ss._1
    val spark = ss._2
    /*val spark: SparkSession = SparkSession
      .builder()
      .appName("ReduceByKeyDemo")
      .config("spark.master", "local")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")*/

    /*val sourceRdd0: RDD[String] = sc.parallelize(List("video_0038edff-5956-4957-b107-f7ff1500f0a4",
      "video_0068562b-bee7-4ca8-9186-6c4ffd803cd5",
      "video_03fba603-01c5-46df-8b19-9fc69e51a15b"))
    val sourceRdd: RDD[Row] = sourceRdd0.map(line => Row(line))
    val sourceRddSchema: StructType = StructType(
      List(StructField("longId", StringType, true))
    )
    val longIdDf: DataFrame = spark.createDataFrame(sourceRdd, sourceRddSchema)*/
    //select distinct(id) from tmp.dim_content_di_20210125 where dt='$dtStr' and ucmsid = 'ucms_'
    val longIdDf: DataFrame = spark.sql(
      s"""
select distinct(id) from tmp.dim_content_di_20210125 where dt='$dtStr'
      """.stripMargin)
    println("--------------longIdDf-------------")
    longIdDf.printSchema()

    val longIdRdd: RDD[Row] = longIdDf.rdd
    val shortIdRdd: RDD[Row] = longIdRdd.map(row => {
      val id = row.getString(0)
      var shortId = "#"
      try {
        val longId = id.split("_")(1).trim
        val jsonStr = getResponse("https://ucms.ifeng.com/api/data/provider/getDocById?id=" + longId)
        val jsonObj = JSON.parseObject(jsonStr)
        if (jsonObj.getIntValue("code") == 0) {
          val data = jsonObj.getString("data")
          val dataObj = JSON.parseObject(data)
          shortId = "ucms_" + dataObj.getString("base62")
        }
      } catch {
        case ex: Exception => {
          ex.printStackTrace()
          println("err id:" + id)
        }
      }
      Row(id, shortId)
    })
    //    shortIdRdd.foreach(println)
    val shortIdSchema: StructType = StructType(
      List(StructField("longId", StringType, true),
        StructField("shortId", StringType, true)
      )
    )
    val shortIdDf: DataFrame = spark.createDataFrame(shortIdRdd, shortIdSchema)
    println("--------------shortIdDf-------------")
    shortIdDf.printSchema()
    //    shortIdDf.show()
    shortIdDf.registerTempTable("longAndShortId")
    spark.sql("select * from longAndShortId limit 10").show(false)
    println("----------------开始入库-----------------")
    spark.sql(
      s"""insert overwrite table tmp.dim_content_di_20210125 partition (dt='$dtStr')
         |select a.id,title,editor,fhh,joinid,src,srcmap,pagetype,domain,yc,eaccid,accid,accounttype,datasource,level,dept,issuetype,issuetime,fhtid,url_pc,url_iifeng,
         |source_link,num,scword,scrate,cword,crate,word_num,jppool_ch,jppool_username,isjp,original,original_wemedia,biz,dataprovider,
         |case
         |when b.longId is not null then b.shortId
         |else a.ucmsid end ucmsid,tm2,updater,sp_name
         |from (select * from tmp.dim_content_di_20210125 where dt='$dtStr') a left join longAndShortId b on a.id=b.longId""".stripMargin)
    println("----------------入库结束-----------------")

    spark.stop()
  }
}
