package com.gaowj.common

import com.gaowj.utils.SparkUtils

/**
  * created by gaowj.
  * created on 2020-04-24.
  * function:
  */
object SequenceSinkFile {
  def main(args: Array[String]): Unit = {
    val sc = SparkUtils.getSc("SequenceFile")
    val data = sc.parallelize(List(("Panda", 3), ("Key", 6), ("Snail", 2)))
    data.saveAsSequenceFile("C:\\workStation\\spark_test\\2020-04-24")
    sc.stop()
  }
}
