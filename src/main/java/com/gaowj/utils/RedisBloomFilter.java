package com.gaowj.utils;

import io.rebloom.client.Command;
import redis.clients.jedis.Client;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * gaowj
 * created on 2020-01-24
 * 布隆过滤器
 */
public class RedisBloomFilter {

    /**
     * 添加
     *
     * @param jedis
     * @param key
     * @param value
     * @return true 添加成功 false 添加失败（已经添加过）
     */
    public static void add(Jedis jedis, String key, String value) {
        try {
            Connection client = jedis.getClient();
            client.sendCommand(Command.ADD, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量添加
     *
     * @param jedis
     * @param key
     * @param value
     * @return
     */
    public static void addMulti(Jedis jedis, String key, String... value) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(key);
        arr.addAll(Arrays.asList(value));

        Connection client = jedis.getClient();
        client.sendCommand(Command.MADD, (String[]) arr.toArray((String[]) value));

    }

    /**
     * 判断是否存在
     *
     * @param jedis
     * @param key
     * @param value
     * @return
     */
    public static boolean exists(Jedis jedis, String key, String value) {
        Client client = jedis.getClient();
        client.sendCommand(Command.EXISTS, key, value);
        return client.getIntegerReply() == 1;
    }

    /**
     * 批量判断是否存在
     *
     * @param jedis
     * @param key
     * @param value
     * @return
     */
    public static List<Long> existsMulti(Jedis jedis, String key, String... value) {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(key);
        arr.addAll(Arrays.asList(value));

        Connection client = jedis.getClient();
        client.sendCommand(Command.MEXISTS, (String[]) arr.toArray((String[]) value));
        List<Long> replyLongList = client.getIntegerMultiBulkReply();

        return replyLongList;
    }

    /**
     * 创建名为key的过滤器
     *
     * @param jedis
     * @param key
     * @param initCapacity 预计放入的元素数量
     * @param errorRate    错误率
     * @return 成功返回'ok' 失败抛出异常
     */
    public static void createFilter(Jedis jedis, String key, long initCapacity, double errorRate) {
        try {
            Client client = jedis.getClient();
            client.sendCommand(Command.RESERVE, key, String.valueOf(errorRate), String.valueOf(initCapacity));
            jedis.expire(key, 86400 * 90);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
