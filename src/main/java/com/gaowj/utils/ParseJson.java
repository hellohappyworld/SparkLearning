package com.gaowj.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaowj.bean.RunLog;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * created by gaowj.
 * created on 2020-04-22.
 * function:
 */
public class ParseJson implements FlatMapFunction<Iterator<String>, RunLog> {
    @Override
    public Iterator<RunLog> call(Iterator<String> lines) throws Exception {
        ArrayList<RunLog> runLogs = new ArrayList<>();
        while (lines.hasNext()) {
            String line = lines.next();
            try {
                JSONObject jsonObject = JSONObject.parseObject(line);
                String request = jsonObject.get("request").toString();
                runLogs.add(JSON.parseObject(request, RunLog.class));
            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println("err ---> " + line);
            }
        }

        return runLogs.iterator();
    }
}
