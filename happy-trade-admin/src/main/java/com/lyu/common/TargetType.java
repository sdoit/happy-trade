package com.lyu.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author LEE
 * @time 2023/4/10 10:25
 */
public enum TargetType {
    /**
     * 商品
     */
    COMMODITY(1, "商品"),
    /**
     * 求购
     */
    REQUEST(2, "求购"),
    /**
     * 用户
     */
    USER(0, "用户");
    @EnumValue
    private final Integer id;
    @JsonValue
    private final String text;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    TargetType(Integer id, String text) {
        this.id = id;
        this.text = text;
    }
}
