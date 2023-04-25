package com.lyu.common;

/**
 * @author LEE
 * @time 2022/12/29 10:46
 */
public class Constant {
    public static final Long COMMODITY_GROUP_ID = 1L;
    public static final Long ORDER_GROUP_ID = 2L;

    public static final Integer HOME_RECOMMENDED_CATEGORY_COUNT = 6;
    /**
     * 商品类别 根分类最大值
     */
    public static final Integer ROOT_TYPE_MAXIMUM = 100;
    /**
     * 商品类别 二级分类最大值
     */
    public static final Integer MIDDLE_TYPE_MAXIMUM = 1000;


    /**
     * 后面需要加 {bid}
     */
    public static final String REDIS_BID_UNPAID_KEY_PRE = "bid:unpaid:";
    /**
     * 后面需要加 {oid}
     */
    public static final String REDIS_ORDER_UNPAID_KEY_PRE = "oid:unpaid:";
    public static final String REDIS_ORDER_MAP_COMMODITY_KEY_PRE = "oid:unpaid:commodity:";
    public static final String REDIS_COMMODITY_KEY_PRE = "commodity:";

    public static final String ORDER_LAST_MARK_KEY = "mark:order:last";
    public static final long ID_OFFSET = -600000000000L;
    public static final long ID_MARK_MAX = 99;



    public static final Integer COMMODITY_PER_PAGE = 28;
    public static final Integer BID_PER_PAGE = 10;
    public static final Integer ORDER_PER_PAGE = 20;
    public static final Integer FAVORITE_PER_PAGE = 30;


    public static final String NGINX_STATIC_SERVER_IMAGE_ADDR = "image";
    public static final String NGINX_STATIC_SERVER_VIDEO_ADDR = "video";
    public static final String FILE_TYPE_IMAGE = "image";
    public static final String FILE_TYPE_VIDEO = "video";




}
