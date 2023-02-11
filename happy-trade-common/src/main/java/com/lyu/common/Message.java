package com.lyu.common;

/**
 * @author LEE
 * @time 2023/2/9 10:16
 */
public enum Message {
    /**
     * 后端向前端推送的消息集合
     */
    SSE_ALPAY_COMPLETED("201", "支付已成功"),
    ITEM_HAS_A_NEW_BID("202", "你的商品有一个信息出价");


    private final String id;

    private final String message;

    Message(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

}
