package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author LEE
 * @TableName t_user_amount
 */
@TableName(value ="t_user_amount")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAmount implements Serializable {
    /**
     * 
     */
    @TableId
    private Long uid;

    /**
     * 有效余额
     */
    private BigDecimal amountEffective;

    /**
     * 冻结金额
     */
    private BigDecimal amountFrozen;

    /**
     * 用户支付宝账号uid
     */
    private String alipayUid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        UserAmount other = (UserAmount) that;
        return (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getAmountEffective() == null ? other.getAmountEffective() == null : this.getAmountEffective().equals(other.getAmountEffective()))
            && (this.getAlipayUid() == null ? other.getAlipayUid() == null : this.getAlipayUid().equals(other.getAlipayUid()))
            && (this.getAmountFrozen() == null ? other.getAmountFrozen() == null : this.getAmountFrozen().equals(other.getAmountFrozen()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getAmountEffective() == null) ? 0 : getAmountEffective().hashCode());
        result = prime * result + ((getAlipayUid() == null) ? 0 : getAlipayUid().hashCode());
        result = prime * result + ((getAmountFrozen() == null) ? 0 : getAmountFrozen().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", amountEffective=").append(amountEffective);
        sb.append(", amountFrozen=").append(amountFrozen);
        sb.append(", alipayUid=").append(alipayUid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}