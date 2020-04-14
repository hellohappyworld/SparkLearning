package com.gaowj.common;

import com.gaowj.utils.SparkUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.DoubleFunction;

import java.util.Arrays;

/**
 * created by gaowj.
 * created on 2020-04-13.
 * function:
 */
public class MapToDouble {
    public static void main(String[] args) {
        Logger.getLogger("org.apache.spark").setLevel(Level.ERROR);
        JavaSparkContext sc = SparkUtil.getSc("MapToDouble");
        JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6));
        JavaDoubleRDD javaDoubleRDD = rdd.mapToDouble(new DoubleFunction<Integer>() {
            @Override
            public double call(Integer integer) throws Exception {
                return (double) integer;
            }
        });
        System.out.println(javaDoubleRDD.mean()); // 平均值
        System.out.println(javaDoubleRDD.variance()); // 方差
        sc.stop();
    }
}
