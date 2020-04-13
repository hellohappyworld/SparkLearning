package com.gaowj.common;

import com.gaowj.utils.SparkUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class JavaWordCount {
    public static void main(String[] args) {
        Logger.getLogger("org.apache.spark").setLevel(Level.ERROR);

        JavaSparkContext sc = SparkUtil.getSc("JavaWordCount");

        JavaRDD<String> lines = sc.textFile("C:\\workStation\\runlogs");
        long userKeyCount = lines.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return s.contains("bdf9faf7ce3136aa");
            }
        }).count();
        System.out.println(userKeyCount);
//        String first = lines.first();
//        System.out.println(first);
        sc.stop();
    }
}
