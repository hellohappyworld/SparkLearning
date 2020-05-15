package com.gaowj.utils;

import redis.clients.jedis.Jedis;

import java.util.zip.CRC32;

public class RedisUtil {

    /**
     * userRedis --> 用户 |CRC32 % 10| 所在的 redis节点
     */
    public static Jedis getUserJedis(String userkey, int db) {
        CRC32 crc32 = new CRC32();
        crc32.update(userkey.getBytes());

        int nUser = Math.abs((int) crc32.getValue() % 10);

        Jedis jedis = null;
        switch (nUser) {
            case 0:
                jedis = RedisPool.getJedis121_7002(db);
                break;
            case 1:
                jedis = RedisPool.getJedis122_7002(db);
                break;
            case 2:
                jedis = RedisPool.getJedis123_7002(db);
                break;
            case 3:
                jedis = RedisPool.getJedis124_7002(db);
                break;
            case 4:
                jedis = RedisPool.getJedis125_7002(db);
                break;
            case 5:
                jedis = RedisPool.getJedis126_7002(db);
                break;
            case 6:
                jedis = RedisPool.getJedis121_7001(db);
                break;
            case 7:
                jedis = RedisPool.getJedis122_7001(db);
                break;
            case 8:
                jedis = RedisPool.getJedis123_7001(db);
                break;
            case 9:
                jedis = RedisPool.getJedis124_7001(db);
                break;
            default:
                break;
        }
        return jedis;
    }

    /**
     * 判断是否是需要获取铺底信息的用户
     *
     * @param userkey
     * @return
     */
    public static boolean isIllegalUsers(String userkey) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedisGraRecSupport();
            jedis.select(10);
            if (jedis.exists(userkey)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisPool.returnResource(jedis);
        }
    }

    /**
     * 把北京用户添加到黑名单reids里
     *
     * @param userkey
     */
    public static void setIllegalUsers(String userkey) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedisGraRecSupport();
            jedis.select(10);
            if (!jedis.exists(userkey)) {
                jedis.set(userkey, "bj");
                //黑名单设置为永久的
//                jedis.expire(userkey, 86400 * 15);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisPool.returnResource(jedis);
        }

    }
}
