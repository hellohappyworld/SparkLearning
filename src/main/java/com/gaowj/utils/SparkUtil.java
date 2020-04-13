package com.gaowj.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkUtil {
    public static JavaSparkContext getSc(String name) {
        SparkConf conf = new SparkConf()
                .setAppName(name)
                .setMaster("local[*]")
                .set("spark.driver.host", "localhost");
        JavaSparkContext sc = new JavaSparkContext(conf);
        return sc;
    }

    public static void stop(JavaSparkContext sc) {
        sc.stop();
    }
}
