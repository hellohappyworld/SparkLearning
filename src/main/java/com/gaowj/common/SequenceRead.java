package com.gaowj.common;

import com.gaowj.utils.SparkUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

/**
 * created by gaowj.
 * created on 2020-04-24.
 * function:
 */
public class SequenceRead {
    public static void main(String[] args) {
        JavaSparkContext sc = SparkUtils.getSc("SequenceRead");
        JavaPairRDD<Text, IntWritable> input = sc.sequenceFile("C:\\workStation\\spark_test\\2020-04-24", Text.class, IntWritable.class);
        JavaPairRDD<String, Integer> result = input.mapToPair(new ConverToNativeTypes());
        System.out.println(result.collect());
        sc.stop();
    }
}

class ConverToNativeTypes implements PairFunction<Tuple2<Text, IntWritable>, String, Integer> {

    @Override
    public Tuple2<String, Integer> call(Tuple2<Text, IntWritable> record) throws Exception {
        return new Tuple2(record._1.toString(), record._2.get());
    }
}