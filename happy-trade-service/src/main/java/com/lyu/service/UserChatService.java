package com.lyu.service;

import com.lyu.common.ContentType;
import com.lyu.common.Message;
import com.lyu.service.impl.UserChatDTO;

import java.util.List;

/**
 * @author LEE
 * @time 2023/3/3 12:15
 */
public interface UserChatService {
    /**
     * 获取与当前登录用户聊天的用户列表
     *
     * @return
     */
    List<UserChatDTO> getAllUserChat();


    /**
     * 从聊天用户列表中删除此用户
     *
     * @param uidTarget
     */
    void deleteUserFromList(Long uidTarget);


    /**
     * 添加或更新最后一次聊天信息
     *
     * @param uidTarget
     * @param lastMessage
     * @param contentType
     */
    void addOrUpdateUserChat(Long uid, Long uidTarget, String lastMessage, ContentType contentType);
    /**
     * 添加或更新最后一次通知信息
     *
     * @param uidTarget
     * @param message
     */
    void addOrUpdateUserChatForNotification(Long uidTarget, Message message);
}
