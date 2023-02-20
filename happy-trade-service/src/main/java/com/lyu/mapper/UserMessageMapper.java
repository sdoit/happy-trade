package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.entity.UserMessage;
import com.lyu.entity.dto.UserMessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LEE
 * @time 2023/2/19 19:01
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {
    /**
     * 通过接收者uid获取消息
     * @param uid
     * @return
     */
    List<UserMessageDTO> pullMessagesByUidReceiver(Long uid);

    /**
     * 拉取要接受的所有未读消息
     * @param uid
     * @return
     */
    List<UserMessageDTO> pullUnreadMessagesByUidReceiver(Long uid);

    /**
     * 获取两用户之间的message
     * @param uidSender
     * @param uidReceiver
     * @return
     */
    List<UserMessageDTO> pullMessageBySenderAndReceiver(Long uidSender,Long uidReceiver);
}