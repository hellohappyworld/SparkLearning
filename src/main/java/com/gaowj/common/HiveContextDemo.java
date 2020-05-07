package com.gaowj.common;


import com.gaowj.utils.SparkUtils;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.hive.HiveContext;

/**
 * created by gaowj.
 * created on 2020-04-26.
 * function:
 */
public class HiveContextDemo {
    public static void main(String[] args) {
        JavaSparkContext sc = SparkUtils.getSc("HiveContextDemo");
        HiveContext hiveContext = new HiveContext(sc);
        Dataset<Row> runlog = hiveContext.jsonFile("C:\\Users\\gaowj\\Desktop\\test2.txt");
        runlog.registerTempTable("runlogTable");
        Dataset<Row> desc_runlogTable = hiveContext.sql("desc runlogTable");
        System.out.println(desc_runlogTable.collect());
        sc.stop();
    }
}
