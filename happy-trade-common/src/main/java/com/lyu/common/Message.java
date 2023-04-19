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
    REQUEST_DELETED(SseConstant.SSE_MESSAGE_ID_NOTIFY, "301", "info", "求购已被下架或删除", "你的商品已转为普通商品发布到商品库供其他买家选购"),
    NEW_COMMODITY_FOR_REQUEST(SseConstant.SSE_MESSAGE_ID_NOTIFY, "302", "info", "新商品", "你的求购有了新商品"),

    /**
     * ***************************************************
     */
    COMMODITY_ARE_FORCED_DOWN(SseConstant.SSE_MESSAGE_ID_NOTIFY, "501", "error", "新的处罚消息", "你的商品存在违规内容，现已强制下架，请整改后重新申请上架。"),
    COMMODITY_ARE_FORCED_DELETE(SseConstant.SSE_MESSAGE_ID_NOTIFY, "502", "error", "新的处罚消息", "你的商品存在违规内容，现已强制删除"),
    REQUEST_ARE_FORCED_DOWN(SseConstant.SSE_MESSAGE_ID_NOTIFY, "503", "error", "新的处罚消息", "你的求购存在违规内容，现已强制下架，请整改后重新申请上架。"),
    REQUEST_ARE_FORCED_DELETE(SseConstant.SSE_MESSAGE_ID_NOTIFY, "504", "error", "新的处罚消息", "你的求购存在违规内容，现已强制删除"),
    USER_ARE_BLOCKED(SseConstant.SSE_MESSAGE_ID_NOTIFY, "505", "error", "新的处罚消息", "用户存在严重违规行为，现已被封禁"),
    PENALTY_WAS_REVOKED(SseConstant.SSE_MESSAGE_ID_NOTIFY, "605", "info", "处罚撤销通知", "你的违规处罚已被撤销"),
    REPORT_PASS(SseConstant.SSE_MESSAGE_ID_NOTIFY, "701", "info", "举报通过", "你的举报通过，平台以对该用户进行相应的处理"),
    REPORT_FAILED(SseConstant.SSE_MESSAGE_ID_NOTIFY, "702", "info", "举报未通过", "你的举报未能通过"),
    COMPLAINT_PASS(SseConstant.SSE_MESSAGE_ID_NOTIFY, "703", "info", "申诉通过", "你的申诉已通过"),
    COMPLAINT_FAILED(SseConstant.SSE_MESSAGE_ID_NOTIFY, "704", "info", "申诉未通过", "你的申诉未能通过");

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
