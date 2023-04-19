package com.lyu.common.reason;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author LEE
 * @time 2023/4/9 19:28
 */
public enum BanUserReason implements Reason{

    /**
     * 用户存在严重违规行为
     */
    USER_HAS_SERIOUS_VIOLATIONS("用户存在严重的违规行为");
    @EnumValue
    @JsonValue
    private final String text;

    public String getText() {
        return text;
    }

    BanUserReason(String text){
        this.text=text;
    }
}
