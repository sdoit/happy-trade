package com.lyu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

/**
 * @author LEE
 * @time 2023/1/12 20:45
 */
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

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AliPay{" +
                "traceNo='" + traceNo + '\'' +
                ", totalAmount=" + totalAmount +
                ", subject='" + subject + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}