package com.lyu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

/**
 * @author LEE
 * @time 2023/1/12 20:45
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AliPay {
    /**
     * 商户订单号。
     */
    @NotBlank
    private String traceNo;
    /**
     * 订单总金额。
     */
    @Digits(integer = 9, fraction = 2)
    private double totalAmount;
    /**
     * 订单标题
     */
    @NotBlank
    private String subject;
    /**
     *
     */
    private String returnUrl;


    /**
     * 订单类型
     */
    private String type;
}