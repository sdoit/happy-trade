package com.lyu.service;

import com.lyu.common.Message;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author LEE
 * @time 2023/2/4 11:09
 */
public interface SSEService {
    /**
     * 添加sse客户端
     *
     * @return
     */
    SseEmitter addEmitter();

    /**
     * 发送消息
     *
     * @param uid uid 可空
     * @param messageId 消息id
     * @param message 消息内容
     */
    void sendMessage(String uid, String messageId, String message,String url);
    void sendMessage(String uid, Message message,String url);
}
