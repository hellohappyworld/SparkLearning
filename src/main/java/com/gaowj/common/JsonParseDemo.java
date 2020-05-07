package com.gaowj.common;

import com.gaowj.bean.RunLog;
import com.gaowj.utils.ParseJson;
import com.gaowj.utils.SparkUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.ArrayList;

/**
 * created by gaowj.
 * created on 2020-04-22.
 * function:
 */
public class JsonParseDemo {
    public static void main(String[] args) {
        JavaSparkContext sc = SparkUtils.getSc("JsonParseDemo");
        ArrayList<String> list = new ArrayList<>();
        list.add("{\"request\":\"{\\\"operation\\\":\\\"default\\\",\\\"province\\\":\\\"\\\",\\\"city\\\":\\\"\\\",\\\"netStatus\\\":\\\"wifi\\\",\\\"gv\\\":\\\"6.7.72\\\",\\\"os\\\":\\\"android_29\\\",\\\"publishid\\\":\\\"6109\\\",\\\"loginid\\\":\\\"\\\",\\\"pullNum\\\":0,\\\"userId\\\":\\\"9ee91c3bfeb5d76e\\\",\\\"size\\\":11,\\\"reason\\\":\\\"\\\",\\\"ref\\\":\\\"\\\",\\\"kind\\\":\\\"direct\\\"}\"}");
        JavaRDD<String> input = sc.parallelize(list);

        JavaRDD<RunLog> result = input.mapPartitions(new ParseJson());
        JavaRDD<String> userId = result.map(new Function<RunLog, String>() {
            @Override
            public String call(RunLog runLog) throws Exception {
                return runLog.getUserId();
            }
        });

        result.saveAsTextFile("C:\\workStation\\spark_test\\2020-04-23");
        sc.stop();
    }
}
