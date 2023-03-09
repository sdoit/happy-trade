package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * 拉取要接受的所有未读通知
     * @param uid
     * @return
     */
    List<UserMessage> pullUnreadNotificationsByUidReceiver(Long uid);
    /**
     * 拉取要接受的所有通知
     * @param page
     * @param uid
     * @return
     */
    IPage<UserMessageDTO> pullNotificationsByUidReceiver(Page<UserMessageDTO> page, Long uid);


    /**
     * 获取两用户之间的message
     * @param page
     * @param groupId
     * @return
     */
     IPage<UserMessageDTO> pullMessageBySenderAndReceiver(Page<UserMessageDTO> page, Long groupId);

//    /**
//     * 获取用户的聊天用户列表
//     * @param uid
//     * @return 携带另一用户对象
//     */
//    List<UserMessageDTO> getChatUserList(Long uid);
}
