package com.lyu.common;

/**
 * @author LEE
 * @time 2022/12/29 10:46
 */
public class Constant {
    public static final Long COMMODITY_GROUP_ID = 1L;
    public static final Long ORDER_GROUP_ID = 2L;
    public static final String COMMODITY_LAST_MARK_KEY = "mark:commodity:last";
    public static final String ORDER_LAST_MARK_KEY = "mark:order:last";
    public static final long ID_OFFSET = -600000000000L;
    public static final long ID_MARK_MAX = 99;

    public static final String ALIPAY_PAY_STATUS_KEY = "trade_status";
    public static final String ALIPAY_PAY_SUCCESS_VALUE = "TRADE_SUCCESS";
    public static final Integer ORDER_STATUS_REFUNDED = -2;
    public static final Integer ORDER_STATUS_CLOSED = -1;
    public static final Integer ORDER_STATUS_NORMAL = 0;
    public static final Integer ORDER_STATUS_COMPLETED = 1;
    public static final Integer COMMODITY_PER_PAGE = 28;


    public static final String NGINX_STATIC_SERVER_IMAGE_ADDR = "image";
    public static final String FILE_TYPE_IMAGE ="image";
    public static final String FILE_TYPE_VIDEO ="video";


}
