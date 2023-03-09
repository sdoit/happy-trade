package com.lyu.service.impl;

import com.lyu.entity.User;
import com.lyu.entity.UserChat;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/3/3 12:17
 */
@Data
public class UserChatDTO extends UserChat {

    private User userTarget;
    private Integer unreadCount;


}
