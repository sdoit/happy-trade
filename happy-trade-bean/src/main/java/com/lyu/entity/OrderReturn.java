package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @author LEE
 * @TableName t_order_return
 */
@TableName(value ="t_order_return")
@Data
public class OrderReturn implements Serializable {
    /**
     * 
     */
    @TableId
    private Long oid;

    /**
     * 
     */
    private String reason;
    private String rejectReason;

    /**
     * 
     */
    private Boolean agree;

    private String shipId;

    /**
     * 申请退货时间
     */
    private LocalDateTime time;

    /**
     * 是否完成
     */
    private Boolean completed;
    /**
     * 回寄地址
     */
    private Long aid;

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
        OrderReturn other = (OrderReturn) that;
        return (this.getOid() == null ? other.getOid() == null : this.getOid().equals(other.getOid()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getAgree() == null ? other.getAgree() == null : this.getAgree().equals(other.getAgree()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getCompleted() == null ? other.getCompleted() == null : this.getCompleted().equals(other.getCompleted()))
            && (this.getShipId() == null ? other.getShipId() == null : this.getShipId().equals(other.getShipId()))
            && (this.getAid() == null ? other.getAid() == null : this.getAid().equals(other.getAid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOid() == null) ? 0 : getOid().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getAgree() == null) ? 0 : getAgree().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getCompleted() == null) ? 0 : getCompleted().hashCode());
        result = prime * result + ((getAid() == null) ? 0 : getAid().hashCode());
        result = prime * result + ((getShipId() == null) ? 0 : getShipId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oid=").append(oid);
        sb.append(", reason=").append(reason);
        sb.append(", agree=").append(agree);
        sb.append(", time=").append(time);
        sb.append(", completed=").append(completed);
        sb.append(", aid=").append(aid);
        sb.append(", shipId=").append(shipId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}