package com.gaowj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private static final Logger logger = LoggerFactory.getLogger(RedisPool.class);
    //服务器IP地址
    private static String ADDR = "10.90.18.16";
    //抓取文章列表
    private static String ADDR_GRA_DATA = "10.90.7.183";

    //抓取文章列表
    private static String ADDR_GRA_REC = "10.90.18.13";
    //保存非实时数据
    private static String ADDR_GRA_NOT_RT = "10.90.18.11";
    private static String ADDR_GRA_REC_SUPPORT = "10.90.18.12";
    //抓取文章列表
    private static String ADDR_GRA_REC_TEST = "10.80.27.158";
    //端口
    private static int PORT = 6379;
    //密码
    private static String AUTH = "password";
    private static String GRA_REC_AUTH = "WxDCxfA8qi";
    //连接实例的最大连接数
    private static int MAX_ACTIVE = 30000;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 800;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
    private static int MAX_WAIT = 10000;
    //连接超时的时间　　
    private static int TIMEOUT = 10000;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;
    private static JedisPool jedisPoolGra = null;
    private static JedisPool jedisPoolGraRec = null;
    private static JedisPool jedisPoolGraNotRt = null;
    private static JedisPool jedisPoolGraRecTest = null;
    private static JedisPool jedisPoolGraRecSupport = null;

    private static JedisPool jedisPool11 = null;
    private static JedisPool jedisPool12 = null;
    private static JedisPool jedisPool13 = null;

    private static JedisPool jedisPool121_7001 = null;
    private static JedisPool jedisPool122_7001 = null;
    private static JedisPool jedisPool123_7001 = null;
    private static JedisPool jedisPool124_7001 = null;
    private static JedisPool jedisPool125_7001 = null;
    private static JedisPool jedisPool126_7001 = null;

    private static JedisPool jedisPool121_7002 = null;
    private static JedisPool jedisPool122_7002 = null;
    private static JedisPool jedisPool123_7002 = null;
    private static JedisPool jedisPool124_7002 = null;
    private static JedisPool jedisPool125_7002 = null;
    private static JedisPool jedisPool126_7002 = null;


    /**
     * 初始化Redis连接池
     */

    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
            jedisPoolGra = new JedisPool(config, ADDR_GRA_DATA, PORT, TIMEOUT, AUTH, 6);
            jedisPoolGraRec = new JedisPool(config, ADDR_GRA_REC, PORT, TIMEOUT, GRA_REC_AUTH, 0);
            jedisPoolGraNotRt = new JedisPool(config, ADDR_GRA_NOT_RT, PORT, TIMEOUT, GRA_REC_AUTH, 0);
            jedisPoolGraRecTest = new JedisPool(config, ADDR_GRA_REC_TEST, PORT, TIMEOUT, AUTH, 10);
            jedisPoolGraRecSupport = new JedisPool(config, ADDR_GRA_REC_SUPPORT, PORT, TIMEOUT, GRA_REC_AUTH, 0);

            jedisPool11 = new JedisPool(config, RedisConst.HOST11, RedisConst.PORT, TIMEOUT, RedisConst.PASSWORD);
            jedisPool12 = new JedisPool(config, RedisConst.HOST12, RedisConst.PORT, TIMEOUT, RedisConst.PASSWORD);
            jedisPool13 = new JedisPool(config, RedisConst.HOST13, RedisConst.PORT, TIMEOUT, RedisConst.PASSWORD);

            jedisPool121_7001 = new JedisPool(config, RedisConst.HOST_121_139, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool122_7001 = new JedisPool(config, RedisConst.HOST_122_138, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool123_7001 = new JedisPool(config, RedisConst.HOST_123_138, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool124_7001 = new JedisPool(config, RedisConst.HOST_124_154, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool125_7001 = new JedisPool(config, RedisConst.HOST_125_154, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool126_7001 = new JedisPool(config, RedisConst.HOST_126_154, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);

            jedisPool121_7002 = new JedisPool(config, RedisConst.HOST_121_139, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool122_7002 = new JedisPool(config, RedisConst.HOST_122_138, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool123_7002 = new JedisPool(config, RedisConst.HOST_123_138, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool124_7002 = new JedisPool(config, RedisConst.HOST_124_154, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool125_7002 = new JedisPool(config, RedisConst.HOST_125_154, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool126_7002 = new JedisPool(config, RedisConst.HOST_126_154, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Jedis getJedis121_7001(int db) {
        try {
            if (jedisPool121_7001 != null) {
                Jedis jedis = jedisPool121_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis122_7001(int db) {
        try {
            if (jedisPool122_7001 != null) {
                Jedis jedis = jedisPool122_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis123_7001(int db) {
        try {
            if (jedisPool123_7001 != null) {
                Jedis jedis = jedisPool123_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis124_7001(int db) {
        try {
            if (jedisPool124_7001 != null) {
                Jedis jedis = jedisPool124_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis125_7001(int db) {
        try {
            if (jedisPool125_7001 != null) {
                Jedis jedis = jedisPool125_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis126_7001(int db) {
        try {
            if (jedisPool126_7001 != null) {
                Jedis jedis = jedisPool126_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis121_7002(int db) {
        try {
            if (jedisPool121_7002 != null) {
                Jedis jedis = jedisPool121_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis122_7002(int db) {
        try {
            if (jedisPool122_7002 != null) {
                Jedis jedis = jedisPool122_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis123_7002(int db) {
        try {
            if (jedisPool123_7002 != null) {
                Jedis jedis = jedisPool123_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis124_7002(int db) {
        try {
            if (jedisPool124_7002 != null) {
                Jedis jedis = jedisPool124_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis125_7002(int db) {
        try {
            if (jedisPool125_7002 != null) {
                Jedis jedis = jedisPool125_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis126_7002(int db) {
        try {
            if (jedisPool126_7002 != null) {
                Jedis jedis = jedisPool126_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Jedis getJedisPool11() {

        try {
            if (jedisPool11 != null) {
                Jedis resource = jedisPool11.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Jedis getJedisPool12() {

        try {
            if (jedisPool12 != null) {
                Jedis resource = jedisPool12.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Jedis getJedisPool13() {

        try {
            if (jedisPool13 != null) {
                Jedis resource = jedisPool13.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 获取Jedis实例
     */

    public static Jedis getJedis() {

        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取Jedis实例
     */

    public static Jedis getJedisGra() {

        try {
            if (jedisPoolGra != null) {
                Jedis resource = jedisPoolGra.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取Jedis实例
     */

    public static Jedis getJedisGraRec() {

        try {
            if (jedisPoolGraRec != null) {
                Jedis resource = jedisPoolGraRec.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取Jedis实例
     */

    public static Jedis getJedisGraRecTest() {

        try {
            if (jedisPoolGraRecTest != null) {
                Jedis resource = jedisPoolGraRecTest.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取Jedis实例
     */

    public static Jedis getJedisGraNotRt() {

        try {
            if (jedisPoolGraNotRt != null) {
                Jedis resource = jedisPoolGraNotRt.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 获取Jedis实例
     */

    public static Jedis getJedisGraRecSupport() {

        try {
            if (jedisPoolGraRecSupport != null) {
                return jedisPoolGraRecSupport.getResource();
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

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
