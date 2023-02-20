package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lyu.entity.UserMessage;
import com.lyu.entity.dto.UserMessageDTO;
import com.lyu.mapper.UserMessageMapper;
import com.lyu.service.SSEService;
import com.lyu.service.UserMessageService;
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
public class UserMessageServiceImpl implements UserMessageService {
    @Resource
    private UserMessageMapper userMessageMapper;
    @Resource
    private SSEService sseService;

    @Override
    @Async("asyncPoolTaskExecutor")
    public void sendMessage(String title, String content, String url, Boolean systemNotify, String messageType, Long uidSend, Long uidReceive) {
        UserMessage userMessage = new UserMessage();
        userMessage.setTitle(title);
        userMessage.setContent(content);
        userMessage.setUrl(url);
        userMessage.setSystemNotify(systemNotify);
        userMessage.setMessageType(messageType);
        userMessage.setUidSend(uidSend);
        userMessage.setUidReceive(uidReceive);
        userMessage.setTime(LocalDateTime.now());
        userMessage.setRead(false);
        //尝试直接推送
        sseService.sendCustomMsgToClientByClientId(String.valueOf(userMessage.getUidReceive()), String.valueOf(userMessage.getMid()), userMessage.getMessageType(),
                userMessage.getTitle(), userMessage.getContent(), userMessage.getUrl());
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
    public List<UserMessageDTO> pullMessageBySenderAndMine(Long uidSender) {
        long uidReceiver = StpUtil.getLoginIdAsLong();
        return userMessageMapper.pullMessageBySenderAndReceiver(uidSender, uidReceiver);
    }
}
