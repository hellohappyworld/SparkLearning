package com.gaowj.common;

import com.gaowj.utils.SparkUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * created by gaowj.
 * created on 2020-04-14.
 * function:
 */
public class CombineByKeyDemo {
    static class AvgCount implements Serializable {
        public int total;
        public int num;

        public AvgCount(int total, int num) {
            this.total = total;
            this.num = num;
        }

        public double avg() {
            return total / (double) num;
        }
    }


    public static void main(String[] args) {
        JavaSparkContext sc = SparkUtils.getSc("CombineByKeyDemo");

        List<Integer> data = Arrays.asList(1, 2, 4, 3, 5, 6, 7, 1, 2);
        JavaRDD<Integer> javaRDD = sc.parallelize(data);
        JavaPairRDD<Integer, Integer> javaPairRDD = javaRDD.mapToPair(new PairFunction<Integer, Integer, Integer>() {
            @Override
            public Tuple2<Integer, Integer> call(Integer integer) throws Exception {
                return new Tuple2<Integer, Integer>(integer, 1);
            }
        });

        JavaPairRDD<Integer, Integer> combineByKeyRDD = javaPairRDD.combineByKey(
                new Function<Integer, Integer>() {
                    @Override
                    public Integer call(Integer v1) throws Exception {
                        return v1 + 0;
                    }
                }, new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer v1, Integer v2) throws Exception {
                        return v1 + v2;
                    }
                }, new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer v1, Integer v2) throws Exception {
                        return v1 + v2;
                    }
                });

        System.out.println(combineByKeyRDD.collect());

        sc.stop();
    }

}

