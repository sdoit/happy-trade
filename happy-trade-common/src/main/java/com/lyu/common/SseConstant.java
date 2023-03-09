package com.lyu.common;

/**
 * @author LEE
 * @time 2023/3/4 9:18
 */
public class SseConstant {
    /**
     * SSE心跳包间隔 单位秒
     */
    public static final Integer SSE_HEARTBEAT_INTERVAL = 100;
    public static final String SSE_MESSAGE_ID_NOTIFY = "0";
    public static final String SSE_MESSAGE_ID_USER_MESSAGE = "1";
    public static final String SSE_PAYMENT_RESULTS_MESSAGE = "2";
}
