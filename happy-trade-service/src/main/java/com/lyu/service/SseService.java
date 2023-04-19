package com.lyu.service;

import com.lyu.common.ContentType;
import com.lyu.common.Message;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author LEE
 * @time 2023/2/4 11:09
 */
public interface SseService {
    /**
     * 创建连接
     *
     * @return
     */
    SseEmitter createSseConnect();

    /**
     * 关闭链接
     *
     * @param clientId
     */
    void closeSseConnect(String clientId);

    /**
     * 推送消息到所有在线的客户端
     *
     * @param message
     * @param url
     */
    void sendMsgToAllClients(Message message, String url);


    /**
     * 推送消息到客户端
     *
     * @param clientId
     * @param message
     * @param url      跳转的地址
     * @return 发送成功？
     */
    boolean sendMsgToClientByClientId(String clientId, Message message, String url);

    /**
     * 推送消息到客户端
     *
     * @param clientId
     * @param messageId
     * @param type      重要：区分为系统中通知和用户私信 当值为constant.SSE_MESSAGE_ID_NOTIFY时为系统通知，当值为constant.SSE_MESSAGE_ID_USER_MESSAGE为用户私信
     * @param title
     * @param message
     * @param url       如果是系统通知，url为要引导用户跳转的地址；如果是用户私信，url为发送者的uid
     * @param flag      当为系统通知时，通过本flag渲染通知框的类型
     * @param contentType      消息内容类型
     * @return 发送成功？
     */
    boolean sendCustomMsgToClientByClientId(String clientId, String messageId, String type, String title, String message, String url, String flag, ContentType contentType);

    /**
     * 关闭SSE连接
     *
     * @param clientId
     */

    void close(String clientId);

}
