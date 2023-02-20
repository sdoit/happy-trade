package com.lyu.service;

import com.lyu.entity.dto.UserMessageDTO;

import java.util.List;

/**
 * @author LEE
 * @time 2023/2/19 19:02
 */
public interface UserMessageService {
    /**
     * 发送信息
     *
     * @param title
     * @param content
     * @param url
     * @param systemNotify 是否为系统通知
     * @param messageType 消息类型
     * @param uidSend
     * @param uidReceive
     */
    void sendMessage(String title, String content, String url, Boolean systemNotify, String messageType, Long uidSend, Long uidReceive);

    /**
     * 拉取要接受的所有消息
     *
     * @param uid
     * @return
     */
    List<UserMessageDTO> pullMessagesByUidReceiver(Long uid);

    /**
     * 拉取要接受的所有未读消息
     *
     * @param uid
     * @return
     */
    List<UserMessageDTO> pullUnreadMessagesByUidReceiver(Long uid);

    /**
     * 获取指定发送者发送给本登录用户的信息
     *
     * @param uidSender
     * @return
     */
    List<UserMessageDTO> pullMessageBySenderAndMine(Long uidSender);
}
