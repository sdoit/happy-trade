package com.lyu.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author LEE
 * @time 2023/4/10 10:37
 */
public enum PenaltyAction {
    /**
     * 删除处理
     */
    DELETE(2, "删除"),
    /**
     * 下架处理
     */
    DOWN(1, "下架"),
    /**
     * 封号处理
     */
    BAN(0, "封禁账户");
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

    PenaltyAction(Integer id, String text) {
        this.id = id;
        this.text = text;
    }
}
