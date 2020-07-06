package com.gaowj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SuppressWarnings("all")
public class RedisPool {

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
    private static JedisPool jedisPool18_12_6379 = null;

    private static JedisPool jedisPool14_7001 = null;
    private static JedisPool jedisPool15_7001 = null;
    private static JedisPool jedisPool16_7001 = null;
    private static JedisPool jedisPool17_7001 = null;
    private static JedisPool jedisPool18_7001 = null;
    private static JedisPool jedisPool19_7001 = null;

    private static JedisPool jedisPool14_7002 = null;
    private static JedisPool jedisPool15_7002 = null;
    private static JedisPool jedisPool16_7002 = null;
    private static JedisPool jedisPool17_7002 = null;
    private static JedisPool jedisPool18_7002 = null;
    private static JedisPool jedisPool19_7002 = null;

    private static JedisPool jedisPool14_7001_BL = null;
    private static JedisPool jedisPool15_7001_BL = null;
    private static JedisPool jedisPool16_7001_BL = null;
    private static JedisPool jedisPool17_7001_BL = null;
//    private static JedisPool jedisPool18_7001_BL = null;
//    private static JedisPool jedisPool19_7001_BL = null;

    private static JedisPool jedisPool14_7002_BL = null;
    private static JedisPool jedisPool15_7002_BL = null;
    private static JedisPool jedisPool16_7002_BL = null;
    private static JedisPool jedisPool17_7002_BL = null;
    private static JedisPool jedisPool18_7002_BL = null;
    private static JedisPool jedisPool19_7002_BL = null;

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

            jedisPool18_12_6379 = new JedisPool(config, RedisConst.HOST_18_12, RedisConst.HOST_18_12_PORT, TIMEOUT, RedisConst.HOST_18_12_AUTH, 0);

            jedisPool14_7001 = new JedisPool(config, RedisConst.HOST_18_14, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool15_7001 = new JedisPool(config, RedisConst.HOST_18_15, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool16_7001 = new JedisPool(config, RedisConst.HOST_18_16, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool17_7001 = new JedisPool(config, RedisConst.HOST_18_17, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool18_7001 = new JedisPool(config, RedisConst.HOST_18_18, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool19_7001 = new JedisPool(config, RedisConst.HOST_18_19, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);

            jedisPool14_7002 = new JedisPool(config, RedisConst.HOST_18_14, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool15_7002 = new JedisPool(config, RedisConst.HOST_18_15, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool16_7002 = new JedisPool(config, RedisConst.HOST_18_16, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool17_7002 = new JedisPool(config, RedisConst.HOST_18_17, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool18_7002 = new JedisPool(config, RedisConst.HOST_18_18, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool19_7002 = new JedisPool(config, RedisConst.HOST_18_19, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);

            jedisPool14_7001_BL = new JedisPool(config, RedisConst.HOST_18_14_BL, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool15_7001_BL = new JedisPool(config, RedisConst.HOST_18_15_BL, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool16_7001_BL = new JedisPool(config, RedisConst.HOST_18_16_BL, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool17_7001_BL = new JedisPool(config, RedisConst.HOST_18_17_BL, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
//            jedisPool18_7001_BL = new JedisPool(config, RedisConst.HOST_18_18_BL, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);
//            jedisPool19_7001_BL = new JedisPool(config, RedisConst.HOST_18_19_BL, RedisConst.PORT_7001, TIMEOUT, RedisConst.PASSWORD_7001_7002);

            jedisPool14_7002_BL = new JedisPool(config, RedisConst.HOST_18_14_BL, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool15_7002_BL = new JedisPool(config, RedisConst.HOST_18_15_BL, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool16_7002_BL = new JedisPool(config, RedisConst.HOST_18_16_BL, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool17_7002_BL = new JedisPool(config, RedisConst.HOST_18_17_BL, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool18_7002_BL = new JedisPool(config, RedisConst.HOST_18_18_BL, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);
            jedisPool19_7002_BL = new JedisPool(config, RedisConst.HOST_18_19_BL, RedisConst.PORT_7002, TIMEOUT, RedisConst.PASSWORD_7001_7002);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Jedis getJedis14_7001(int db) {
        try {
            if (jedisPool14_7001 != null) {
                Jedis jedis = jedisPool14_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis15_7001(int db) {
        try {
            if (jedisPool15_7001 != null) {
                Jedis jedis = jedisPool15_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis16_7001(int db) {
        try {
            if (jedisPool16_7001 != null) {
                Jedis jedis = jedisPool16_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis17_7001(int db) {
        try {
            if (jedisPool17_7001 != null) {
                Jedis jedis = jedisPool17_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis18_7001(int db) {
        try {
            if (jedisPool18_7001 != null) {
                Jedis jedis = jedisPool18_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis19_7001(int db) {
        try {
            if (jedisPool19_7001 != null) {
                Jedis jedis = jedisPool19_7001.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis14_7002(int db) {
        try {
            if (jedisPool14_7002 != null) {
                Jedis jedis = jedisPool14_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis15_7002(int db) {
        try {
            if (jedisPool15_7002 != null) {
                Jedis jedis = jedisPool15_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis16_7002(int db) {
        try {
            if (jedisPool16_7002 != null) {
                Jedis jedis = jedisPool16_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis17_7002(int db) {
        try {
            if (jedisPool17_7002 != null) {
                Jedis jedis = jedisPool17_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis18_7002(int db) {
        try {
            if (jedisPool18_7002 != null) {
                Jedis jedis = jedisPool18_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis19_7002(int db) {
        try {
            if (jedisPool19_7002 != null) {
                Jedis jedis = jedisPool19_7002.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * redis黑名单用户
     *
     * @return
     */
    public static Jedis getJedisGraRecSupport() {
        try {
            if (jedisPool18_12_6379 != null) {
                return jedisPool18_12_6379.getResource();
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

    public static Jedis getJedis14_7001_BL(int db) {
        try {
            if (jedisPool14_7001_BL != null) {
                Jedis jedis = jedisPool14_7001_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis15_7001_BL(int db) {
        try {
            if (jedisPool15_7001_BL != null) {
                Jedis jedis = jedisPool15_7001_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis16_7001_BL(int db) {
        try {
            if (jedisPool16_7001_BL != null) {
                Jedis jedis = jedisPool16_7001_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis17_7001_BL(int db) {
        try {
            if (jedisPool17_7001_BL != null) {
                Jedis jedis = jedisPool17_7001_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static Jedis getJedis18_7001_BL(int db) {
//        try {
//            if (jedisPool18_7001_BL != null) {
//                Jedis jedis = jedisPool18_7001_BL.getResource();
//                jedis.select(db);
//                return jedis;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static Jedis getJedis19_7001_BL(int db) {
//        try {
//            if (jedisPool19_7001_BL != null) {
//                Jedis jedis = jedisPool19_7001_BL.getResource();
//                jedis.select(db);
//                return jedis;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static Jedis getJedis14_7002_BL(int db) {
        try {
            if (jedisPool14_7002_BL != null) {
                Jedis jedis = jedisPool14_7002_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis15_7002_BL(int db) {
        try {
            if (jedisPool15_7002_BL != null) {
                Jedis jedis = jedisPool15_7002_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis16_7002_BL(int db) {
        try {
            if (jedisPool16_7002_BL != null) {
                Jedis jedis = jedisPool16_7002_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis17_7002_BL(int db) {
        try {
            if (jedisPool17_7002_BL != null) {
                Jedis jedis = jedisPool17_7002_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis18_7002_BL(int db) {
        try {
            if (jedisPool18_7002_BL != null) {
                Jedis jedis = jedisPool18_7002_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Jedis getJedis19_7002_BL(int db) {
        try {
            if (jedisPool19_7002_BL != null) {
                Jedis jedis = jedisPool19_7002_BL.getResource();
                jedis.select(db);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
