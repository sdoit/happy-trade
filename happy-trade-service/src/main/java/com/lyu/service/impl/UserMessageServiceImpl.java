package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.UserMessage;
import com.lyu.entity.dto.UserMessageDTO;
import com.lyu.exception.GlobalException;
import com.lyu.mapper.UserMessageMapper;
import com.lyu.service.SseService;
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
    private SseService sseService;

    @Override
    @Async("asyncPoolTaskExecutor")
    public void sendMessage(String title, String content, String url, Boolean systemNotify, String messageType, Long uidSend, Long uidReceive) {
        UserMessage userMessage = new UserMessage();
        userMessage.setGroupId(getGroupId(uidSend, uidReceive));
        log.debug("GroupId:" + userMessage.getGroupId());
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
        sseService.sendCustomMsgToClientByClientId(
                String.valueOf(userMessage.getUidReceive()),
                "0",
                systemNotify ? Constant.SSE_MESSAGE_ID_NOTIFY : Constant.SSE_MESSAGE_ID_USER_MESSAGE,
                userMessage.getTitle(),
                userMessage.getContent(),
                userMessage.getUrl(),
                null);
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
    public UserMessageDTO pullMessageBySenderAndMine(Page<UserMessageDTO> page, Long uidSender) {
        long uidReceiver = StpUtil.getLoginIdAsLong();
        List<UserMessageDTO> records = userMessageMapper.pullMessageBySenderAndReceiver(page, getGroupId(uidReceiver, uidSender)).getRecords();

        return records.isEmpty() ? null : records.get(0);
    }

    @Override
    public List<UserMessageDTO> getChatUserList() {
        return userMessageMapper.getChatUserList(StpUtil.getLoginIdAsLong());
    }

    @Override
    public void setReadByTargetUid(Long uid) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid == null) {
            throw new GlobalException(CodeAndMessage.WRONG_PASSWORD.getCode(), CodeAndMessage.WRONG_PASSWORD.getMessage());
        }
        long groupId = getGroupId(uid, uidLogin);
        userMessageMapper.update(null, new UpdateWrapper<UserMessage>().set("read_already", 1).
                eq("group_id", groupId).eq("uid_receive", uidLogin));
    }

    private long getGroupId(Long uid1, Long uid2) {
        long max = NumberUtil.max(uid1, uid2);
        long min = NumberUtil.min(uid1, uid2);
        return (min << Long.toBinaryString(max).length()) + max;
    }
}
