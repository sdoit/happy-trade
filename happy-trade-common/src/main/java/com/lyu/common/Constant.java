package com.lyu.common;

/**
 * @author LEE
 * @time 2022/12/29 10:46
 */
public class Constant {
    public static final Long COMMODITY_GROUP_ID = 1L;
    public static final Long ORDER_GROUP_ID = 2L;
    /**
     * SSE心跳包间隔 单位秒
     */
    public static final Integer SSE_HEARTBEAT_INTERVAL = 100;
    public static final String SSE_MESSAGE_ID_NOTIFY = "0";
    public static final String SSE_MESSAGE_ID_USER_MESSAGE = "1";
    public static final String SSE_PAYMENT_RESULTS_MESSAGE = "2";

    public static final Integer MESSAGE_COUNT_PER_PAGE = 20;

    public static final Integer HOME_RECOMMENDED_CATEGORY_COUNT = 5;
    /**
     * 商品类别 根分类最大值
     */
    public static final Integer ROOT_TYPE_MAXIMUM = 100;
    /**
     * 商品类别 二级分类最大值
     */
    public static final Integer MIDDLE_TYPE_MAXIMUM = 1000;




    /**
     * 一分钟内无需验证的最多的请求次数
     */
    public static final Integer MAXIMUM_NUMBER_OF_REQUESTS_PER_MINUTES = 5;

    /**
     * 记录用户每分钟的请求数 后面需要加 {uid}
     */
    public static final String REDIS_USER_NUMBER_OF_REQUESTS_PER_MINUTE_KEY_PRE = "requests:";
    /**
     * 请求验证数据 {uid}
     */
    public static final String REDIS_REQUEST_CAPTCHA_VALID_DATA_KEY = "captcha:";
    /**
     * 通行证 后加getCaptcha生成的标识token
     */
    public static final String REDIS_PASS_TOKEN_KEY = "captcha:passToken:";

    /**
     * 后面需要加 {cid}
     */
    public static final String REDIS_USER_LOGGED_KEY_PRE = "user:logged:";
    /**
     * 后面需要加 {bid}
     */
    public static final String REDIS_BID_UNPAID_KEY_PRE = "bid:unpaid:";
    /**
     * 后面需要加 {oid}
     */
    public static final String REDIS_ORDER_UNPAID_KEY_PRE = "oid:unpaid:";
    public static final String REDIS_ORDER_MAP_COMMODITY_KEY_PRE = "oid:unpaid:commodity:";

    public static final String ORDER_LAST_MARK_KEY = "mark:order:last";
    public static final long ID_OFFSET = -600000000000L;
    public static final long ID_MARK_MAX = 99;

    public static final String ALIPAY_PAY_STATUS_KEY = "trade_status";
    public static final String ALIPAY_PAY_SUCCESS_VALUE = "TRADE_SUCCESS";
    /**
     * 用户报价预付订单 类型
     */
    public static final String ALIPAY_PAY_TYPE_BID = "BID";
    public static final String ALIPAY_PAY_STATUS_SUCCESS = "SUCCESS";
    public static final String BID_ORDER_PREFIX = "0";
    /**
     * 支付宝支付过期时间 10分钟
     */
    public static final Integer ALIPAY_TIME_EXPIRE = 10;
    public static final Integer ORDER_STATUS_REFUNDED = -2;
    public static final Integer ORDER_STATUS_CLOSED = -1;
    public static final Integer ORDER_STATUS_NORMAL = 0;
    public static final Integer ORDER_STATUS_COMPLETED = 1;


    public static final Integer COMMODITY_PER_PAGE = 28;
    public static final Integer BID_PER_PAGE = 10;
    public static final Integer ORDER_PER_PAGE = 20;
    public static final Integer FAVORITE_PER_PAGE = 30;


    public static final String NGINX_STATIC_SERVER_IMAGE_ADDR = "image";
    public static final String NGINX_STATIC_SERVER_VIDEO_ADDR = "video";
    public static final String FILE_TYPE_IMAGE = "image";
    public static final String FILE_TYPE_VIDEO = "video";
    public static final String REFUND_DUE_TO_DIRECT_PURCHASE = "有用户以商家标价直接购买了商品，导致本商品的所有出价被撤销";
    public static final String REFUND_DUE_TO_SELLER_AGREES_TO_ANOTHER_BID = "卖家同意了其他人的报价，你的报价被自动取消";
    public static final String REFUND_DUE_TO_REJECTED = "卖家拒绝了你的报价";
    public static final String REFUND_DUE_USER_VOLUNTARILY_CANCEL = "用户主动撤销出价";
    public static final String ALIPAY_REFUND_SUCCESSFUL_LOGO = "Y";
    public static final String BID_GET_ALL = "all";
    public static final String BID_GET_NO_RESPONSE = "no-response";
    public static final String BID_GET_RESPONDED = "responded";
    public static final String BID_GET_REJECTED = "rejected";
    public static final String BID_GET_AGREED = "agreed";


}
