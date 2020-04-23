package com.gaowj.utils;

import org.apache.spark.Partitioner;

/**
 * created by gaowj.
 * created on 2020-04-22.
 * function: 自定义分区
 */
public class DomainNamePartitioner extends Partitioner {
    @Override
    public int numPartitions() { //返回创建出的分区数
        return 0;
    }

    @Override
    public int getPartition(Object key) { // 返回给定键的分区编号
        return 0;
    }

    @Override
    public boolean equals(Object obj) { // 判断分区器对象是否和其它分区器实例相同
        return super.equals(obj);
    }
}
