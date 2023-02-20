package com.lyu.entity.dto;

import com.lyu.entity.User;
import com.lyu.entity.UserMessage;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/19 19:05
 */
@Data
public class UserMessageDTO extends UserMessage {
    private User sender;
    private User receiver;
}
