package com.lyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.ContentType;
import com.lyu.common.Message;
import com.lyu.entity.UserMessage;
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
     * @param contentType 内容类型
     * @param url         如果是用户私信，不存在要跳转的url。要把信息的发送者uid填写到url
     * @param messageType 消息类型
     * @param uidSend
     * @param uidReceive
     */
    void sendMessage(String title, String content, ContentType contentType, String url, String messageType, Long uidSend, Long uidReceive);

    /**
     * 发送通知，
     * 如果通知发送成功，标记通知为已读，
     * 如果发送失败，标记为未读，待用户下次登录时消费本通知。
     *
     * @param message    要发送的信息
     * @param url        引导用户跳转的连接
     * @param uidReceive 接收人id
     */
    void sendNotification(Message message, String url, Long uidReceive);

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
     * 拉取要接受的所有通知
     *
     * @param page
     * @param uid
     * @return
     */
    UserMessageDTO pullNotificationsByUidReceiver(Page<UserMessageDTO> page, Long uid);

    /**
     * 拉取用户所有还未读的通知
     *
     * @param uid
     * @return
     */
    List<UserMessage> pullUnreadNotificationsByUidReceiver(Long uid);

    /**
     * 获取指定发送者发送给本登录用户的信息
     *
     * @param uidSender
     * @param page
     * @return
     */
    UserMessageDTO pullMessageBySenderAndMine(Page<UserMessageDTO> page, Long uidSender);

    /**
     * 尝试推送未读的通知到客户端
     *
     * @param uid
     */
    void tryPushUnreadNotifications(Long uid);

    /**
     * 设置消息为已读
     *
     * @param uid 对方uid
     */
    void setReadByTargetUid(Long uid);

    /**
     * 设置通知为已读
     *
     * @param mid 消息id
     */
    void setNotificationRead(Long mid);
}