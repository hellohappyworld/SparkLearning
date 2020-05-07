package com.gaowj.common;

import com.gaowj.utils.SparkUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * created by gaowj.
 * created on 2020-04-14.
 * function:
 */
public class PairFunctionDemo implements Serializable {
    static class comparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

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
        JavaSparkContext sc = SparkUtils.getSc("PairFunctionDemo");

        JavaRDD<String> lines = sc.parallelize(Arrays.asList("a_b", "c_d", "e_f", "g_h"));


        JavaPairRDD<String, String> pairRDD = lines.mapToPair(keyData);
        System.out.println(pairRDD.collect());
        JavaPairRDD<String, String> mapValuesRDD = pairRDD.mapValues(addValue);
        System.out.println(mapValuesRDD.collect());
//        JavaPairRDD<String, String> sortPairRDD = mapValuesRDD.sortByKey();
//
//        System.out.println(sortPairRDD.countByKey());
//        System.out.println(sortPairRDD.collectAsMap());
//        System.out.println(sortPairRDD.lookup("a"));

        sc.stop();
    }

}

