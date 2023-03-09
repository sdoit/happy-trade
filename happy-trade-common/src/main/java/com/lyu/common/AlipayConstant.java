package com.lyu.common;

/**
 * @author LEE
 * @time 2023/3/4 9:22
 */
public class AlipayConstant {
    /**
     * 支付宝支付过期时间 10分钟
     */
    public static final Integer ALIPAY_TIME_EXPIRE = 10;
    public static final Integer ORDER_STATUS_REFUNDED = -2;
    public static final Integer ORDER_STATUS_CLOSED = -1;
    public static final Integer ORDER_STATUS_NORMAL = 0;
    public static final Integer ORDER_STATUS_COMPLETED = 1;
    public static final String REFUND_DUE_TO_DIRECT_PURCHASE = "有用户以商家标价直接购买了商品，导致本商品的所有出价被撤销";
    public static final String REFUND_DUE_TO_SELLER_AGREES_TO_ANOTHER_BID = "卖家同意了其他人的报价，你的报价被自动取消";
    public static final String REFUND_DUE_TO_REJECTED = "卖家拒绝了你的报价";
    public static final String REFUND_DUE_USER_VOLUNTARILY_CANCEL = "用户主动撤销出价";
    public static final String ALIPAY_REFUND_SUCCESSFUL_LOGO = "Y";
    public static final String ALIPAY_PAY_STATUS_KEY = "trade_status";
    public static final String ALIPAY_PAY_SUCCESS_VALUE = "TRADE_SUCCESS";
    /**
     * 用户报价预付订单 类型
     */
    public static final String ALIPAY_PAY_TYPE_BID = "BID";
    public static final String ALIPAY_PAY_STATUS_SUCCESS = "SUCCESS";
    public static final String BID_ORDER_PREFIX = "0";

}
