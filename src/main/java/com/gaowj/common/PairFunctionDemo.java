package com.gaowj.common;

import com.gaowj.utils.SparkUtil;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;

/**
 * created by gaowj.
 * created on 2020-04-14.
 * function:
 */
public class PairFunctionDemo {
    public static PairFunction<String, String, String> keyData = new PairFunction<String, String, String>() {
        @Override
        public Tuple2<String, String> call(String s) throws Exception {
            String[] split = s.split("_");
            return new Tuple2<>(split[0], split[1]);
        }
    };

    public static Function<String, String> addValue = new Function<String, String>() {
        @Override
        public String call(String s) throws Exception {
            return s + "_" + s;
        }
    };

    public static void main(String[] args) {
        JavaSparkContext sc = SparkUtil.getSc("PairFunctionDemo");

        JavaRDD<String> lines = sc.parallelize(Arrays.asList("a_b", "c_d", "e_f", "g_h"));


        JavaPairRDD<String, String> pairRDD = lines.mapToPair(keyData);
        JavaPairRDD<String, String> mapValuesRDD = pairRDD.mapValues(addValue);

        System.out.println(mapValuesRDD.collect());

        sc.stop();
    }
}
