package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.entity.UserChat;
import com.lyu.service.impl.UserChatDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author LEE
* @description 针对表【t_user_chat_list】的数据库操作Mapper
* @createDate 2023-03-03 12:11:28
* @Entity com.lyu.entity.UserChatList
*/
@Mapper
public interface UserChatMapper extends BaseMapper<UserChat> {
    /**
     * 获取用户的聊天用户列表
     * @param uid
     * @return
     */
     List<UserChatDTO> getAllUserChat(Long uid);
     /**
     * 聊天是否存在该用户
     * @param groupId
     * @param uid
     * @return
     */
    Boolean existChat(Long groupId,Long uid);

}




