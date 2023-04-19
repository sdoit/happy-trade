package com.lyu.common.reason;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author LEE
 * @time 2023/4/9 19:29
 */
public enum GetDownCommodityReason implements Reason {
    /**
     * 违规
     */
    Prohibited_Product_Types("禁止销售的产品类型"),
    Violation("违规");
    @EnumValue
    @JsonValue
    private final String text;

    public String getText() {
        return text;
    }

    GetDownCommodityReason(String text) {
        this.text = text;
    }
}
