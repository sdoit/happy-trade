package com.lyu.common.reason;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author LEE
 * @time 2023/4/9 19:29
 */
public enum DeleteCommodityReason implements Reason {
    /**
     * 违规
     */
    Violation("违规");
    @EnumValue
    @JsonValue
    private final String text;

    public String getText() {
        return text;
    }

    DeleteCommodityReason(String text) {
        this.text = text;
    }
}
