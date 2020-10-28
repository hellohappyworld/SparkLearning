package com.gaowj.common;

import com.gaowj.utils.SparkUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.List;

public class JavaWordCount {
    public static void main(String[] args) {
        Logger.getLogger("org.apache.spark").setLevel(Level.ERROR);

        JavaSparkContext sc = SparkUtils.getSc("JavaWordCount");

//        JavaRDD<String> lines = sc.textFile("hdfs://10.90.7.168:8020/user/source/appsta_test/20200928/0000.1601222400093.log");
        JavaRDD<String> lines = sc.textFile("C:\\tengxunyun\\1000.1601258400036");

        List<String> collect = lines.collect();

        for (String str : collect) {
            System.out.println(str);
        }

        sc.stop();
    }
}
