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
    SSE_ALPAY_COMPLETED("201", "success", "支付已成功", "你的订单已支付成功。"),
    A_BUYER_BOUGHT_YOUR_PRODUCT_DIRECTLY("202", "success", "购买通知", "有买家直接购买了你的商品。"),
    YOU_HAVE_A_NEW_BID_ON_YOUR_ITEM("301", "success", "通知", "你的商品有了一个新的出价");


    private final String id;
    private final String type;
    private final String title;
    private final String message;

    Message(String id, String type, String title, String message) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
    }

}
