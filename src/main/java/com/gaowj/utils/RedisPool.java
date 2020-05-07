package com.gaowj.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    //连接实例的最大连接数
    private static int MAX_ACTIVE = 3000;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 1200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
//    private static int MAX_WAIT = 10000;
    //连接超时的时间　　
    private static int TIMEOUT = 12000000;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;


    /**
     * 初始化Redis连接池
     */

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
//        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);
        jedisPool = new JedisPool(config, "10.90.124.154", 80, TIMEOUT, "WxDCxfA8qi");
    }

    public static Jedis getJedisPool() {
        try {
            if (jedisPool != null)
                return jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     *
     * 释放资源
     */

    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }

    }

}
