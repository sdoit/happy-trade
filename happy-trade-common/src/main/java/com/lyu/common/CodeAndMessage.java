package com.lyu.common;

/**
 * @author LEE
 * @time 2022/12/26 12:21
 */
public enum CodeAndMessage {
    /**
     * 用户名或密码错误
     */
    INCORRECT_USERNAME_OR_PASSWORD(false, 100, "账号或密码错误"),

    UNEXPECTED_ERROR(false, 000, "发生意外的错误，请稍后重试"),

    ORDER_NEED_PAY(true, 300, "订单待支付"),

    SUCCESS(true, 200, "成功"),

    /**
     * ======================================================================
     * =============异常==================
     * ======================================================================
     */
    ITEM_SOLD(false, 1001, "商品已售罄"),
    BANED_USER(false, 2001, "用户已被封禁"),

    USER_NOT_EXIST(false, 2002, "用户不存在"),
    WRONG_PASSWORD(false, 2003, "账号或密码错误"),
    USER_ALREADY_EXISTS(false, 2004, "用户名已存在"),
    USER_NOT_LOGIN(false, 2005, "用户未登录"),
    ACTIONS_WITHOUT_ACCESS(false, 2006, "用户没有权限操作"),

    NON_CANCELLABLE_ORDER(false, 3001, "订单不可取消"),
    INVALID_ORDER_ID(false, 3002, "无效的订单号"),


    ALIPAY_WRONG_PAYMENT_PARAMETER(false, 4001, "支付参数错误");

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
