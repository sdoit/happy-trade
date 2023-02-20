package com.lyu.service;

import com.lyu.common.Message;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author LEE
 * @time 2023/2/4 11:09
 */
public interface SSEService {
    /**
     * 创建连接
     * @return
     */
    SseEmitter createSseConnect();

    /**
     * 关闭链接
     * @param clientId
     */
    void closeSseConnect(String clientId);

    /**
     * 根据id获取SseEmitter
     * @param clientId
     * @return
     */
    SseEmitter getSseEmitterByClientId(String clientId);

    /**
     * 推送消息到所有在线的客户端
     * @param message
     * @param url
     */
    void sendMsgToAllClients(Message message, String url);


    /**
     * 推送消息到客户端
     * @param clientId
     * @param message
     * @param url 跳转的地址
     */
    void sendMsgToClientByClientId(String clientId, Message message,String url);
    /**
     * 推送消息到客户端
     * @param clientId
     * @param message
     * @param url 跳转的地址
     */
    void sendCustomMsgToClientByClientId(String clientId, String messageId,String type, String title, String message,String url);

}
