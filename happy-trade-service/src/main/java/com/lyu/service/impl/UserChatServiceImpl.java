package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyu.common.ContentType;
import com.lyu.common.Message;
import com.lyu.entity.UserChat;
import com.lyu.mapper.UserChatMapper;
import com.lyu.service.UserChatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LEE
 * @time 2023/3/3 12:21
 */
@Service
public class UserChatServiceImpl implements UserChatService {
    @Resource
    private UserChatMapper userChatMapper;

    @Override
    public List<UserChatDTO> getAllUserChat() {
        long uidLogin = StpUtil.getLoginIdAsLong();
        return userChatMapper.getAllUserChat(uidLogin);
    }

    @Override
    public void deleteUserFromList(Long uidTarget) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        long groupId = getGroupId(uidLogin, uidTarget);
        userChatMapper.delete(new QueryWrapper<UserChat>().eq("group_id", groupId).eq("uid", uidLogin));
    }


    @Override
    public void addOrUpdateUserChat(Long uid, Long uidTarget, String lastMessage, ContentType contentType) {
        long groupId = getGroupId(uid, uidTarget);
        if (BooleanUtil.isTrue(userChatMapper.existChat(groupId, uid))) {
            userChatMapper.update(null, new UpdateWrapper<UserChat>().set("last_message", lastMessage).eq("group_id", groupId).eq("uid", uid));
        } else {
            UserChat userChat = new UserChat();
            userChat.setUid(uid);
            userChat.setUidTarget(uidTarget);
            userChat.setGroupId(groupId);
            userChat.setLastMessage(lastMessage);
            userChat.setContentType(contentType);
            userChat.setTime(LocalDateTime.now());
            userChatMapper.insert(userChat);
        }
        if (BooleanUtil.isTrue(userChatMapper.existChat(groupId, uidTarget))) {
            userChatMapper.update(null, new UpdateWrapper<UserChat>().set("last_message", lastMessage).eq("group_id", groupId).eq("uid", uidTarget));
        } else {
            UserChat userChat = new UserChat();
            userChat.setUid(uidTarget);
            userChat.setUidTarget(uid);
            userChat.setGroupId(groupId);
            userChat.setContentType(contentType);
            userChat.setLastMessage(lastMessage);
            userChat.setTime(LocalDateTime.now());
            userChatMapper.insert(userChat);
        }
    }

    @Override
    public void addOrUpdateUserChatForNotification(Long uidTarget, Message message) {
        if (BooleanUtil.isTrue(userChatMapper.existChat(uidTarget, uidTarget))) {
            userChatMapper.update(null, new UpdateWrapper<UserChat>()
                    .set("last_message", message.getMessage())
                    .eq("group_id", uidTarget)
                    .eq("uid", uidTarget));

        }else{
            UserChat userChat = new UserChat();
            userChat.setUid(uidTarget);
            userChat.setUidTarget(null);
            userChat.setGroupId(uidTarget);
            userChat.setLastMessage(message.getMessage());
            userChat.setTime(LocalDateTime.now());
            userChatMapper.insert(userChat);
        }
    }

    private long getGroupId(Long uid1, Long uid2) {
        long max = NumberUtil.max(uid1, uid2);
        long min = NumberUtil.min(uid1, uid2);
        return (min << Long.toBinaryString(max).length()) + max;
    }
}
