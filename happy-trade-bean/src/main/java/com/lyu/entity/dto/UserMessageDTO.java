package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyu.entity.User;
import com.lyu.entity.UserMessage;
import lombok.Data;

import java.util.List;

/**
 * @author LEE
 * @time 2023/2/19 19:05
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMessageDTO {
    private Long groupId;
    private List<UserMessage> messages;
    private UserMessage lastMessage;
    private User targetUser;
    private Integer unreadCount;
}
