package com.gaowj.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkUtils {
    public static JavaSparkContext getSc(String name) {
        Logger.getLogger("org.apache.spark").setLevel(Level.ERROR);
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
