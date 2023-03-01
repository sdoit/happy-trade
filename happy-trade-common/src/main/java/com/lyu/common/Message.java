package com.lyu.common;

import lombok.Getter;

/**
 * @author LEE
 * @time 2023/2/9 10:16
 */
@Getter
public enum Message {
    /**
     * 后端向前端推送的消息集合
     */
    SSE_ORDER_ALPAY_COMPLETED(Constant.SSE_PAYMENT_RESULTS_MESSAGE, "201",  "success", "支付已成功", "你的订单已支付成功。"),
    SSE_BID_ALPAY_COMPLETED(Constant.SSE_PAYMENT_RESULTS_MESSAGE, "202",  "success", "支付已成功", "你的出价已支付成功。"),
    A_BUYER_BOUGHT_YOUR_PRODUCT_DIRECTLY(Constant.SSE_MESSAGE_ID_NOTIFY,"203", "success", "购买通知", "有买家直接购买了你的商品。"),
    YOU_HAVE_A_NEW_BID_ON_YOUR_ITEM(Constant.SSE_MESSAGE_ID_NOTIFY, "204","success", "通知", "你的商品有了一个新的出价");


    private final String notifyType;
    private final String id;
    private final String type;
    private final String title;
    private final String message;

    Message(String notifyType, String id,String type, String title, String message) {
        this.notifyType = notifyType;
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
    }

}
