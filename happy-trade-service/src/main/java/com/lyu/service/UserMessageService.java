package com.lyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * @param url 如果是用户私信，不存在要跳转的url。要把信息的发送者uid填写到url
     * @param systemNotify 是否为系统通知
     * @param messageType  消息类型
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
     * @param page
     * @return
     */
    UserMessageDTO pullMessageBySenderAndMine(Page<UserMessageDTO> page,Long uidSender);


    /**
     * 获取登录用户的聊天用户列表
     *
     * @return
     */
    List<UserMessageDTO> getChatUserList();

    /**
     * 设置已读
     * @param uid 对方uid
     */
    void setReadByTargetUid(Long uid);
}