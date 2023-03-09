package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.ContentType;
import com.lyu.common.Message;
import com.lyu.common.SseConstant;
import com.lyu.entity.UserMessage;
import com.lyu.entity.dto.UserMessageDTO;
import com.lyu.exception.GlobalException;
import com.lyu.mapper.UserMessageMapper;
import com.lyu.service.SseService;
import com.lyu.service.UserChatService;
import com.lyu.service.UserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/19 19:15
 */
@Service
@Slf4j
public class UserMessageServiceImpl implements UserMessageService {
    @Resource
    private UserMessageMapper userMessageMapper;
    @Resource
    private UserChatService userChatService;
    @Resource
    private SseService sseService;

    @Override
    @Async("asyncPoolTaskExecutor")
    public void sendMessage(String title, String content, ContentType contentType, String url, String messageType, Long uidSend, Long uidReceive) {
        UserMessage userMessage = new UserMessage();
        userMessage.setGroupId(getGroupId(uidSend, uidReceive));
        log.debug("GroupId:" + userMessage.getGroupId());
        userMessage.setTitle(title);
        userMessage.setContent(content);
        userMessage.setContentType(contentType);
        userMessage.setUrl(url);
        userMessage.setSystemNotify(false);
        userMessage.setMessageType(messageType);
        userMessage.setUidSend(uidSend);
        userMessage.setUidReceive(uidReceive);
        userMessage.setTime(LocalDateTime.now());
        userMessage.setRead(false);
        //修改用户聊天列表最后一条消息
        userChatService.addOrUpdateUserChat(uidSend, uidReceive, content,contentType);
        //尝试直接推送
        sseService.sendCustomMsgToClientByClientId(String.valueOf(userMessage.getUidReceive()), "0", SseConstant.SSE_MESSAGE_ID_USER_MESSAGE, userMessage.getTitle(), userMessage.getContent(), userMessage.getUrl(), null);
        userMessageMapper.insert(userMessage);
    }

    @Override
    @Async("asyncPoolTaskExecutor")
    public void sendNotification(Message message, String url, Long uidReceive) {
        UserMessage userMessage = new UserMessage();
        userMessage.setGroupId(getGroupId(0L, uidReceive));
        userMessage.setTitle(message.getTitle());
        userMessage.setContent(message.getMessage());
        userMessage.setUrl(url);
        userMessage.setSystemNotify(true);
        userMessage.setMessageType(message.getType());
        userMessage.setUidSend(0L);
        userMessage.setUidReceive(uidReceive);
        userMessage.setTime(LocalDateTime.now());
        userMessage.setRead(false);
        //修改用户通知最后一条消息
        userChatService.addOrUpdateUserChatForNotification(uidReceive, message);
        //尝试直接推送
        if (sseService.sendCustomMsgToClientByClientId(String.valueOf(userMessage.getUidReceive()), "0", SseConstant.SSE_MESSAGE_ID_NOTIFY, userMessage.getTitle(), userMessage.getContent(), userMessage.getUrl(), userMessage.getMessageType())) {
            //标记通知为已读
            userMessage.setRead(true);
        }
        userMessageMapper.insert(userMessage);
    }

    @Override
    public List<UserMessageDTO> pullMessagesByUidReceiver(Long uid) {
        return userMessageMapper.pullMessagesByUidReceiver(uid);
    }

    @Override
    public List<UserMessageDTO> pullUnreadMessagesByUidReceiver(Long uid) {
        return userMessageMapper.pullUnreadMessagesByUidReceiver(uid);
    }

    @Override
    public UserMessageDTO pullNotificationsByUidReceiver(Page<UserMessageDTO> page, Long uid) {
        List<UserMessageDTO> records = userMessageMapper.pullNotificationsByUidReceiver(page, uid).getRecords();
        return records.isEmpty() ? null : records.get(0);
    }

    @Override
    public List<UserMessage> pullUnreadNotificationsByUidReceiver(Long uid) {
        return userMessageMapper.pullUnreadNotificationsByUidReceiver(uid);
    }

    @Override
    public UserMessageDTO pullMessageBySenderAndMine(Page<UserMessageDTO> page, Long uidSender) {
        long uidReceiver = StpUtil.getLoginIdAsLong();
        List<UserMessageDTO> records = userMessageMapper.pullMessageBySenderAndReceiver(page, getGroupId(uidReceiver, uidSender)).getRecords();
        return records.isEmpty() ? null : records.get(0);
    }

    @Override
    @Async("asyncPoolTaskExecutor")
    public void tryPushUnreadNotifications(Long uid) {
        List<UserMessage> userMessages = pullUnreadNotificationsByUidReceiver(uid);
        userMessages.forEach(userMessage -> {
            boolean sendResult = sseService.sendCustomMsgToClientByClientId(String.valueOf(uid), String.valueOf(userMessage.getMid()), "0", userMessage.getTitle(), userMessage.getContent(), userMessage.getUrl(), userMessage.getMessageType());
            if (sendResult) {
                setNotificationRead(userMessage.getMid());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void setReadByTargetUid(Long uid) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid == null) {
            throw new GlobalException(CodeAndMessage.WRONG_PASSWORD.getCode(), CodeAndMessage.WRONG_PASSWORD.getMessage());
        }
        long groupId = getGroupId(uid, uidLogin);
        userMessageMapper.update(null, new UpdateWrapper<UserMessage>().set("read_already", 1).eq("group_id", groupId).eq("uid_receive", uidLogin));
    }

    @Override
    public void setNotificationRead(Long mid) {
        userMessageMapper.update(null, new UpdateWrapper<UserMessage>().set("read_already", 1).eq("mid", mid));
    }


    private long getGroupId(Long uid1, Long uid2) {
        long max = NumberUtil.max(uid1, uid2);
        long min = NumberUtil.min(uid1, uid2);
        return (min << Long.toBinaryString(max).length()) + max;
    }
}
