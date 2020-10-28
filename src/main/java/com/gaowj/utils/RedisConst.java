package com.gaowj.utils;

/**
 * gaowj
 * created on 2020-01-27
 * redis常量
 * http://know.ifengidc.com/pages/viewpage.action?pageId=28248979
 * http://know.ifengidc.com/pages/viewpage.action?pageId=28255875
 */
@SuppressWarnings("all")
public final class RedisConst {

    /**
     * redis 冷启动黑名单
     * http://know.ifengidc.com/pages/viewpage.action?pageId=28248979
     */
    public static final String HOST_18_12 = "10.90.18.12";
    public static int HOST_18_12_PORT = 6379;
    public static String HOST_18_12_AUTH = "WxDCxfA8qi";

    public static final String HOST_18_14 = "10.90.84.153";
    public static final String HOST_18_15 = "10.90.85.153";
    public static final String HOST_18_16 = "10.90.86.153";
    public static final String HOST_18_17 = "10.90.87.153";
    public static final String HOST_18_18 = "10.90.88.153";
    public static final String HOST_18_19 = "10.90.89.153";

    public static final String HOST_18_14_BL = "10.90.84.153";
    public static final String HOST_18_15_BL = "10.90.85.153";
    public static final String HOST_18_16_BL = "10.90.86.153";
    public static final String HOST_18_17_BL = "10.90.87.153";
    public static final String HOST_18_18_BL = "10.90.88.153";
    public static final String HOST_18_19_BL = "10.90.89.153";

    public static final int PORT_7001 = 7001; // 用来处理用户级别的业务
    public static final int PORT_7002 = 7002; // 用来处理用户级别的业务
    public static final int PORT_7003 = 7003; // 人群以及账号下对应的文章有序列表
    public static final int PORT_7004 = 7004; // 过期以及特殊标记下线文章
    public static final int PORT_7005 = 7005;
    /**
     * host 14~19的7003的redis密码都是这个
     */
    public static final String CLUSTER_PASSWORD = "ioBA6TJ3HZElEcoB";
    /**
     * host 14~19的7001，7002,7004,7005的redis密码都是这个
     */
    public static final String PASSWORD_7001_7002 = "ioBA6TJ3HZElEcoB";

    public static final int DB_1 = 1;
    public static final int DB_2 = 2;
    public static final int DB_3 = 3;
    public static final int DB_4 = 4;
    public static final int DB_5 = 5;
    public static final int DB_6 = 6;
    public static final int DB_7 = 7;
    public static final int DB_8 = 8;
    public static final int DB_9 = 9;
    public static final int DB_10 = 10;
    public static final int DB_11 = 11;
    public static final int DB_12 = 12;
    public static final int DB_13 = 13;
    public static final int DB_14 = 14;
    public static final int DB_15 = 15;
    public static final int DB_16 = 16;
    public static final int DB_17 = 17;

}
