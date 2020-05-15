package com.gaowj.utils;

/**
 * gaowj
 * created on 2020-01-27
 * redis常量
 */
public final class RedisConst {
    public static final String HOST11 = "10.90.18.11";
    //    public static final String HOST11 = "10.90.124.154";
    public static final String HOST12 = "10.90.18.12";
    //    public static final String HOST12 = "10.90.125.154";
    public static final String HOST13 = "10.90.18.13";
    //    public static final String HOST13 = "10.80.32.158";
    public static final int PORT = 6379;
    //    public static final int PORT = 80;
    public static final String PASSWORD = "WxDCxfA8qi";

//    public static final String HOST_121_139 = "10.90.121.139";
//    public static final String HOST_122_138 = "10.90.122.138";
//    public static final String HOST_123_138 = "10.90.123.138";
//    public static final String HOST_124_154 = "10.90.124.154";
//    public static final String HOST_125_154 = "10.90.125.154";
//    public static final String HOST_126_154 = "10.90.126.154";

    public static final String HOST_121_139 = "10.90.18.14";
    public static final String HOST_122_138 = "10.90.18.15";
    public static final String HOST_123_138 = "10.90.18.16";
    public static final String HOST_124_154 = "10.90.18.17";
    public static final String HOST_125_154 = "10.90.18.18";
    public static final String HOST_126_154 = "10.90.18.19";
    public static final int PORT_7001 = 7001;
    public static final int PORT_7002 = 7002;
    public static final int PORT_7003 = 7003;
    public static final int PORT_7004 = 7004;
    public static final int PORT_7005 = 7005;
    //    public static final String CLUSTER_PASSWORD = "aWmPbGRXFvsuZKwS";
    public static final String CLUSTER_PASSWORD = "ioBA6TJ3HZElEcoB";
    /**
     * host 121~126的7001，7002,7004,7005的redis密码都是这个
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


    /*public static final HashMap<String, String> REDISCLUSTER;
    public static final HashMap<String, String> REFGROUR; //文章有序列表
    public static final HashMap<String, String> GROUPDOC_R; //文章有序列表
    public static final HashMap<String, String> DOC_GROUP_R; //文章人群对应关系
    public static final HashMap<String, String> BIGIMGPREVIEWCOMPLETE_R; //视频播放完整版标签
    public static final HashMap<String, String> COUNTPERSONAS_R; //存储用户计算后历史画像
    public static final HashMap<String, String> STARTPERSONAS_R; //存储用户初始历史画像
    public static final HashMap<String, String> DOC_MOULD_R; //存储文章试探流模板
    public static final HashMap<String, String> USER_MOULD_R; //存储用户试探流模板
    public static final HashMap<String, String> USERGROUPNUM_R; //用户各人群推荐次数(画像用)
    public static final HashMap<String, String> DOC_HIS_R; //存储用户推荐历史
    public static final HashMap<String, String> JPDOC_R; //精品文章有序列表
    public static final HashMap<String, String> CLICKGROUPTWO_R; //正反馈人群多干预一刷
    public static final HashMap<String, String> DEFAULT19_R; //编辑强干预前23条default流
    public static final HashMap<String, String> DOCSTART19_R; //素材前23条模板
    public static final HashMap<String, String> USERSTART19_R; //用户首次启动前19条模板
    public static final HashMap<String, String> USERALLGROUPNUM_R; //用户各人群推荐次数(试探流用)
    public static final HashMap<String, String> USERCLICKGROUP_R; //用户正反馈人群
    public static final HashMap<String, String> GROUPMOULDNUM_R; //fan人群最大试探条数
    public static final HashMap<String, String> DOCSHOWTYPE_R; //文章展示样式对应关系
    public static final HashMap<String, String> FORCEGROUP_R; //用户强插新闻、消遣类人群
    public static final HashMap<String, String> PERSONASEXPLOREGROUP_R; //用户画像临近试探人群
    public static final HashMap<String, String> USERREFRESHNUM_R; //用户刷新次数
    public static final HashMap<String, String> TRACKGROUP_R; //用户追踪人群
    public static final HashMap<String, String> ACCOUNTDOC_R; //账号下文章有序列表
    public static final HashMap<String, String> DOCLEFTLOGO_R; //文章左下角标签样式
    public static final HashMap<String, String> TAGID_R; //标签id同名称对应关系
    public static final HashMap<String, String> EXPIREDOC_R; //过期以及特殊标记下线文章
    public static final HashMap<String, String> SIM_R; //替换用相似文章
    public static final HashMap<String, String> MIXED_R; //特殊redis库，主要放单独的key
    public static final HashMap<String, String> USERLOCAL_R; //用户最近一次位置信息

    public static final HashMap<String, String> GROUPID_R; //人群推荐信息标识
    public static final HashMap<String, String> PERSONASEXPLOREGROUPDESC_R; //用户画像临近试探人群衰退系数
    public static final HashMap<String, String> USERACCOUNTGROUP_R; //用户关注账号

    public static final HashMap<String, String> MOULD_DOC_GROUP_R; //待编辑铺底流素材

    static {
        REDISCLUSTER = new HashMap<String, String>();
        REDISCLUSTER.put("host", "10.61.224.3");
        REDISCLUSTER.put("port", PORT);
        REDISCLUSTER.put("password", "88r9cW7BiHkdU46p");

        REFGROUR = new HashMap<String, String>();
        REFGROUR.put("host", HOST13);
        REFGROUR.put("port", PORT);
        REFGROUR.put("db", "38");
        REFGROUR.put("password", "WxDCxfA8qi");

        USERLOCAL_R = new HashMap<String, String>();
        USERLOCAL_R.put("host", HOST13);
        USERLOCAL_R.put("port", PORT);
        USERLOCAL_R.put("db", "21");
        USERLOCAL_R.put("password", "WxDCxfA8qi");

        GROUPDOC_R = new HashMap<String, String>();
        GROUPDOC_R.put("host", HOST13);
        GROUPDOC_R.put("port", PORT);
        GROUPDOC_R.put("db", "0");
        GROUPDOC_R.put("password", "WxDCxfA8qi");

        DOC_GROUP_R = new HashMap<String, String>();
        DOC_GROUP_R.put("host", HOST13);
        DOC_GROUP_R.put("port", PORT);
        DOC_GROUP_R.put("db", "1");
        DOC_GROUP_R.put("password", "WxDCxfA8qi");

        BIGIMGPREVIEWCOMPLETE_R = new HashMap<String, String>();
        BIGIMGPREVIEWCOMPLETE_R.put("host", HOST13);
        BIGIMGPREVIEWCOMPLETE_R.put("port", PORT);
        BIGIMGPREVIEWCOMPLETE_R.put("db", "2");
        BIGIMGPREVIEWCOMPLETE_R.put("password", "WxDCxfA8qi");

        COUNTPERSONAS_R = new HashMap<String, String>();
        COUNTPERSONAS_R.put("host", HOST13);
        COUNTPERSONAS_R.put("port", PORT);
        COUNTPERSONAS_R.put("db", "3");
        COUNTPERSONAS_R.put("password", "WxDCxfA8qi");

        STARTPERSONAS_R = new HashMap<String, String>();
        STARTPERSONAS_R.put("host", HOST13);
        STARTPERSONAS_R.put("port", PORT);
        STARTPERSONAS_R.put("db", "4");
        STARTPERSONAS_R.put("password", "WxDCxfA8qi");

        DOC_MOULD_R = new HashMap<String, String>();
        DOC_MOULD_R.put("host", HOST13);
        DOC_MOULD_R.put("port", PORT);
        DOC_MOULD_R.put("db", "5");
        DOC_MOULD_R.put("password", "WxDCxfA8qi");

        USER_MOULD_R = new HashMap<String, String>();
        USER_MOULD_R.put("host", HOST13);
        USER_MOULD_R.put("port", PORT);
        USER_MOULD_R.put("db", "6");
        USER_MOULD_R.put("password", "WxDCxfA8qi");

        USERGROUPNUM_R = new HashMap<String, String>();
        USERGROUPNUM_R.put("host", HOST13);
        USERGROUPNUM_R.put("port", PORT);
        USERGROUPNUM_R.put("db", "7");
        USERGROUPNUM_R.put("password", "WxDCxfA8qi");

        DOC_HIS_R = new HashMap<String, String>();
        DOC_HIS_R.put("host", HOST13);
        DOC_HIS_R.put("port", PORT);
        DOC_HIS_R.put("db", "8");
        DOC_HIS_R.put("password", "WxDCxfA8qi");

        JPDOC_R = new HashMap<String, String>();
        JPDOC_R.put("host", HOST13);
        JPDOC_R.put("port", PORT);
        JPDOC_R.put("db", "9");
        JPDOC_R.put("password", "WxDCxfA8qi");

        CLICKGROUPTWO_R = new HashMap<String, String>();
        CLICKGROUPTWO_R.put("host", HOST13);
        CLICKGROUPTWO_R.put("port", PORT);
        CLICKGROUPTWO_R.put("db", "10");
        CLICKGROUPTWO_R.put("password", "WxDCxfA8qi");

        DEFAULT19_R = new HashMap<String, String>();
        DEFAULT19_R.put("host", HOST13);
        DEFAULT19_R.put("port", PORT);
        DEFAULT19_R.put("db", "13");
        DEFAULT19_R.put("password", "WxDCxfA8qi");

        DOCSTART19_R = new HashMap<String, String>();
        DOCSTART19_R.put("host", HOST13);
        DOCSTART19_R.put("port", PORT);
        DOCSTART19_R.put("db", "14");
        DOCSTART19_R.put("password", "WxDCxfA8qi");

        USERSTART19_R = new HashMap<String, String>();
        USERSTART19_R.put("host", HOST13);
        USERSTART19_R.put("port", PORT);
        USERSTART19_R.put("db", "15");
        USERSTART19_R.put("password", "WxDCxfA8qi");

        USERALLGROUPNUM_R = new HashMap<String, String>();
        USERALLGROUPNUM_R.put("host", HOST13);
        USERALLGROUPNUM_R.put("port", PORT);
        USERALLGROUPNUM_R.put("db", "16");
        USERALLGROUPNUM_R.put("password", "WxDCxfA8qi");

        USERCLICKGROUP_R = new HashMap<String, String>();
        USERCLICKGROUP_R.put("host", HOST13);
        USERCLICKGROUP_R.put("port", PORT);
        USERCLICKGROUP_R.put("db", "17");
        USERCLICKGROUP_R.put("password", "WxDCxfA8qi");

        GROUPMOULDNUM_R = new HashMap<String, String>();
        GROUPMOULDNUM_R.put("host", HOST13);
        GROUPMOULDNUM_R.put("port", PORT);
        GROUPMOULDNUM_R.put("db", "18");
        GROUPMOULDNUM_R.put("password", "WxDCxfA8qi");

        DOCSHOWTYPE_R = new HashMap<String, String>();
        DOCSHOWTYPE_R.put("host", HOST13);
        DOCSHOWTYPE_R.put("port", PORT);
        DOCSHOWTYPE_R.put("db", "20");
        DOCSHOWTYPE_R.put("password", "WxDCxfA8qi");

        FORCEGROUP_R = new HashMap<String, String>();
        FORCEGROUP_R.put("host", HOST13);
        FORCEGROUP_R.put("port", PORT);
        FORCEGROUP_R.put("db", "23");
        FORCEGROUP_R.put("password", "WxDCxfA8qi");

        PERSONASEXPLOREGROUP_R = new HashMap<String, String>();
        PERSONASEXPLOREGROUP_R.put("host", HOST13);
        PERSONASEXPLOREGROUP_R.put("port", PORT);
        PERSONASEXPLOREGROUP_R.put("db", "24");
        PERSONASEXPLOREGROUP_R.put("password", "WxDCxfA8qi");

        USERREFRESHNUM_R = new HashMap<String, String>();
        USERREFRESHNUM_R.put("host", HOST13);
        USERREFRESHNUM_R.put("port", PORT);
        USERREFRESHNUM_R.put("db", "26");
        USERREFRESHNUM_R.put("password", "WxDCxfA8qi");

        TRACKGROUP_R = new HashMap<String, String>();
        TRACKGROUP_R.put("host", HOST13);
        TRACKGROUP_R.put("port", PORT);
        TRACKGROUP_R.put("db", "28");
        TRACKGROUP_R.put("password", "WxDCxfA8qi");

        ACCOUNTDOC_R = new HashMap<String, String>();
        ACCOUNTDOC_R.put("host", HOST13);
        ACCOUNTDOC_R.put("port", PORT);
        ACCOUNTDOC_R.put("db", "29");
        ACCOUNTDOC_R.put("password", "WxDCxfA8qi");

        DOCLEFTLOGO_R = new HashMap<String, String>();
        DOCLEFTLOGO_R.put("host", HOST13);
        DOCLEFTLOGO_R.put("port", PORT);
        DOCLEFTLOGO_R.put("db", "30");
        DOCLEFTLOGO_R.put("password", "WxDCxfA8qi");

        TAGID_R = new HashMap<String, String>();
        TAGID_R.put("host", HOST13);
        TAGID_R.put("port", PORT);
        TAGID_R.put("db", "31");
        TAGID_R.put("password", "WxDCxfA8qi");

        EXPIREDOC_R = new HashMap<String, String>();
        EXPIREDOC_R.put("host", HOST13);
        EXPIREDOC_R.put("port", PORT);
        EXPIREDOC_R.put("db", "33");
        EXPIREDOC_R.put("password", "WxDCxfA8qi");

        SIM_R = new HashMap<String, String>();
        SIM_R.put("host", HOST13);
        SIM_R.put("port", PORT);
        SIM_R.put("db", "34");
        SIM_R.put("password", "WxDCxfA8qi");

        MIXED_R = new HashMap<String, String>();
        MIXED_R.put("host", HOST13);
        MIXED_R.put("port", PORT);
        MIXED_R.put("db", "49");
        MIXED_R.put("password", "WxDCxfA8qi");

        GROUPID_R = new HashMap<String, String>();
        GROUPID_R.put("host", HOST12);
        GROUPID_R.put("port", PORT);
        GROUPID_R.put("db", "0");
        GROUPID_R.put("password", "WxDCxfA8qi");

        PERSONASEXPLOREGROUPDESC_R = new HashMap<String, String>();
        PERSONASEXPLOREGROUPDESC_R.put("host", HOST12);
        PERSONASEXPLOREGROUPDESC_R.put("port", PORT);
        PERSONASEXPLOREGROUPDESC_R.put("db", "25");
        PERSONASEXPLOREGROUPDESC_R.put("password", "WxDCxfA8qi");

        USERACCOUNTGROUP_R = new HashMap<String, String>();
        USERACCOUNTGROUP_R.put("host", HOST12);
        USERACCOUNTGROUP_R.put("port", PORT);
        USERACCOUNTGROUP_R.put("db", "30");
        USERACCOUNTGROUP_R.put("password", "WxDCxfA8qi");

        MOULD_DOC_GROUP_R = new HashMap<String, String>();
        MOULD_DOC_GROUP_R.put("host", HOST11);
        MOULD_DOC_GROUP_R.put("port", PORT);
        MOULD_DOC_GROUP_R.put("db", "20");
        MOULD_DOC_GROUP_R.put("password", "WxDCxfA8qi");

    }*/
}
