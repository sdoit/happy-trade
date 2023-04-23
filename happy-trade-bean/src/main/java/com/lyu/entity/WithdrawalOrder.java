package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * @author LEE
 * @TableName t_withdrawal_order
 */
@TableName(value ="t_withdrawal_order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WithdrawalOrder implements Serializable {
    /**
     * 提现订单号
     */
    @TableId
    private Long wid;

    /**
     * 
     */
    private Long uid;

    /**
     * 提现金额
     */
    @NotNull
    private BigDecimal amount;

    /**
     * 提现申请时间
     */
    private LocalDateTime time;

    /**
     * 支付宝转账订单号
     */
    private String alipayOrderId;

    /**
     * 支付宝支付资金流水号
     */
    private String alipayFundOrderId;

    /**
     * 
     */
    private String status;

    /**
     * 订单支付时间
     */
    private LocalDateTime transDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getAlipayOrderId() {
        return alipayOrderId;
    }

    public void setAlipayOrderId(String alipayOrderId) {
        this.alipayOrderId = alipayOrderId;
    }

    public String getAlipayFundOrderId() {
        return alipayFundOrderId;
    }

    public void setAlipayFundOrderId(String alipayFundOrderId) {
        this.alipayFundOrderId = alipayFundOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransDate() {
        return transDate;
    }

    public void setTransDate(LocalDateTime transDate) {
        this.transDate = transDate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WithdrawalOrder other = (WithdrawalOrder) that;
        return (this.getWid() == null ? other.getWid() == null : this.getWid().equals(other.getWid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getAlipayOrderId() == null ? other.getAlipayOrderId() == null : this.getAlipayOrderId().equals(other.getAlipayOrderId()))
            && (this.getAlipayFundOrderId() == null ? other.getAlipayFundOrderId() == null : this.getAlipayFundOrderId().equals(other.getAlipayFundOrderId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTransDate() == null ? other.getTransDate() == null : this.getTransDate().equals(other.getTransDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWid() == null) ? 0 : getWid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getAlipayOrderId() == null) ? 0 : getAlipayOrderId().hashCode());
        result = prime * result + ((getAlipayFundOrderId() == null) ? 0 : getAlipayFundOrderId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTransDate() == null) ? 0 : getTransDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", wid=").append(wid);
        sb.append(", uid=").append(uid);
        sb.append(", amount=").append(amount);
        sb.append(", time=").append(time);
        sb.append(", alipayOrderId=").append(alipayOrderId);
        sb.append(", alipayFundOrderId=").append(alipayFundOrderId);
        sb.append(", status=").append(status);
        sb.append(", transDate=").append(transDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}