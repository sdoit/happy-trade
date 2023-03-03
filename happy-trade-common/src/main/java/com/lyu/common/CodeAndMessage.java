package com.lyu.common;

/**
 * @author LEE
 * @time 2022/12/26 12:21
 */
public enum CodeAndMessage {
    /**
     * 全部返回代码
     */
    INCORRECT_USERNAME_OR_PASSWORD(false, 100, "账号或密码错误"),

    UNEXPECTED_ERROR(false, 0, "发生意外的错误，请稍后重试"),
    INCONCLUSIVE_RESULT(false, 1, "Operation complete, but not sure if successful"),
    THIS_OPERATION_NEEDS_FURTHER_VERIFICATION(false,2,"本次操作需要进行验证"),

    ORDER_NEED_PAY(true, 300, "订单待支付"),

    SUCCESS(true, 200, "成功"),

    VERIFICATION_PASSED(true,200,"验证通过"),
    VERIFICATION_FAILURE(true,0,"验证失败，请重试"),

    /**
     * ======================================================================
     * =============异常==================
     * ======================================================================
     */
    ITEM_SOLD(false, 1001, "商品已售罄"),
    /**
     * 用户
     */
    BANED_USER(false, 2001, "用户已被封禁"),

    USER_NOT_EXIST(false, 2002, "用户不存在"),
    WRONG_PASSWORD(false, 2003, "账号或密码错误"),
    USER_ALREADY_EXISTS(false, 2004, "用户名已存在"),
    USER_NOT_LOGIN(false, 2005, "用户未登录"),
    ACTIONS_WITHOUT_ACCESS(false, 2006, "用户没有权限操作"),
    /**
     * 订单
     */
    NON_CANCELLABLE_ORDER(false, 3001, "订单不可取消"),
    INVALID_ORDER_ID(false, 3002, "无效的订单号"),
    CANT_BUY_OWN_PRODUCT(false, 3003, "不能购买自己的商品"),
    INCORRECT_SHIPPING_ADDRESS(false, 3004, "错误的收货地址"),
    ORDER_IS_NOT_COMPLETED(false, 3005, "订单还未完成"),
    ORDER_IS_NOT_SHIP(false, 3006, "订单还未发货"),

    INVALID_BID_ID(false, 3050, "无效的报价号"),

    BID_ALREADY_EXISTS(false, 3051, "你已经对这个商品有一个报价了"),
    BID_NOT_VALID_FOR_24_HOURS(false, 3052, "24小时内不能撤销出价"),
    BID_HAS_BEEN_CANCELED_OR_PROCESSED(false, 3053, "出价已被取消或已被处理"),
    /**
     * alipay
     */
    ALIPAY_WRONG_PAYMENT_PARAMETER(false, 4001, "支付参数错误"),
    INSUFFICIENT_AMOUNT_AVAILABLE(false, 7001, "用户可用金额不足"),

    UNBOUND_ALIPAY(false, 7002, "用户未绑定支付宝"),

    /**
     * 商品
     */

    NO_SUCH_COMMODITY(false, 5001, "不存在该商品"),
    COMMODITY_IS_ALREADY_OUT_THE_SHELF(false, 5002, "商品已经处在下架的状态中"),
    COMMODITY_IS_ALREADY_ON_THE_SHELF(false, 5003, "商品已经处在上架的状态中"),
    NO_SUCH_COMMODITY_SNAPSHOT(false, 5004, "快照不存在"),



    WRONG_REQUEST_PARAMETER(false, 6001, "请求参数错误"),

    /**
     * SSE
     */
    SSE_WENT_WRONG(false, 8001, "SSE推送服务创建失败");

    private final int code;
    private final boolean flag;

    private final String message;

    CodeAndMessage(boolean flag, int code, String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    public boolean getFlag() {
        return flag;
    }
}
