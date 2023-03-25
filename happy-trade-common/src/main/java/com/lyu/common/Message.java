package com.lyu.common;


/**
 * @author LEE
 * @time 2023/2/9 10:16
 */
public enum Message {
    /**
     * 后端向前端推送的消息集合
     */
    SSE_ORDER_ALPAY_COMPLETED(SseConstant.SSE_PAYMENT_RESULTS_MESSAGE, "201", "success", "支付已成功", "你的订单已支付成功。"),
    SSE_BID_ALPAY_COMPLETED(SseConstant.SSE_PAYMENT_RESULTS_MESSAGE, "202", "success", "支付已成功", "你的出价已支付成功。"),
    A_BUYER_BOUGHT_YOUR_PRODUCT_DIRECTLY(SseConstant.SSE_MESSAGE_ID_NOTIFY, "203", "success", "购买通知", "有买家直接购买了你的商品。"),
    YOU_HAVE_A_NEW_BID_ON_YOUR_ITEM(SseConstant.SSE_MESSAGE_ID_NOTIFY, "204", "success", "通知", "你的商品有了一个新的出价"),
    BUYER_HAS_CONFIRMED_RECEIPT_OF_GOODS(SseConstant.SSE_MESSAGE_ID_NOTIFY, "205", "success", "通知", "买家已确认收货"),
    SELLER_HAS_FINISHED_RATING_YOU(SseConstant.SSE_MESSAGE_ID_NOTIFY, "206", "success", "通知", "卖家已完成对你的评分"),
    YOUR_ORDER_HAS_BEEN_SHIPPED(SseConstant.SSE_MESSAGE_ID_NOTIFY, "207", "success", "通知", "你的订单已发货"),
    REQUEST_DELETED(SseConstant.SSE_MESSAGE_ID_NOTIFY, "301", "info", "求购已被下架或删除", "你的商品已转为普通商品发布到商品库供其他买家选购");


    private final String notifyType;
    private final String id;
    private final String type;
    private final String title;
    private final String message;

    Message(String notifyType, String id, String type, String title, String message) {
        this.notifyType = notifyType;
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
