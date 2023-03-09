package com.lyu.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author LEE
 * @time 2023/3/4 9:34
 */
public enum ContentType {
    /**
     * 文本
     */
    TEXT(0,"TEXT"),
    /**
     * 图片
     */
    IMAGE(1,"IMAGE"),
    /**
     * 视频
     */
    VIDEO(2,"VIDEO");


    @EnumValue
    private final Integer code;
    @JsonValue
    private final String name;

    ContentType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
