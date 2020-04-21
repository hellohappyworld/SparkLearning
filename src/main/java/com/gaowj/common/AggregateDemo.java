package com.gaowj.common;

import com.gaowj.utils.SparkUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import java.io.Serializable;
import java.util.Arrays;

public class AggregateDemo {
    public static Function2<AvgCount, Integer, AvgCount> addAndCount =
            new Function2<AvgCount, Integer, AvgCount>() {
                @Override
                public AvgCount call(AvgCount a, Integer x) throws Exception {
                    a.total += x;
                    a.num += 1;
                    return a;
                }
            };
    public static Function2<AvgCount, AvgCount, AvgCount> combine =
            new Function2<AvgCount, AvgCount, AvgCount>() {
                @Override
                public AvgCount call(AvgCount a, AvgCount b) throws Exception {
                    a.total += b.total;
                    a.num += b.num;
                    return a;
                }
            };

    public static void main(String[] args) {
        Logger.getLogger("org.apache.spark").setLevel(Level.ERROR);
        JavaSparkContext sc = SparkUtil.getSc("AggregateDemo");

        JavaRDD<Integer> parallelize = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6));

        AvgCount initial = new AvgCount(0, 0);
        AvgCount result = parallelize.aggregate(initial, addAndCount, combine);
        System.out.println(result.avg());

        sc.stop();
    }

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
}

