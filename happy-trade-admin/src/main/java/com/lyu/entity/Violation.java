package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lyu.common.PenaltyAction;
import com.lyu.common.TargetType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @TableName t_user_violation
 */
@TableName(value = "t_user_violation")
@Data
public class Violation implements Serializable {
    /**
     * 违规记录编号
     */
    @TableId(type = IdType.AUTO)
    private Long vno;

    /**
     * 0:用户账户，1:商品，2:求购
     */
    @NotNull
    private TargetType target;

    /**
     * 主体人uid
     */
    @NotNull
    private Long uid;

    /**
     * 对象id
     */
    @NotNull
    private Long targetId;

    /**
     * 0:封禁，1:下架，2:删除
     */
    @NotNull
    private PenaltyAction action;

    /**
     * 原因
     */
    @NotNull
    private String reason;

    /**
     * 可用的申诉次数
     */
    @NotNull
    private Integer complaintCount;

    /**
     * 处理时间
     */
    @NotNull
    private LocalDateTime time;

    private Integer duration;

    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Commodity commodity;
    @TableField(exist = false)
    private Request request;
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
        Violation other = (Violation) that;
        return (this.getVno() == null ? other.getVno() == null : this.getVno().equals(other.getVno()))
                && (this.getTarget() == null ? other.getTarget() == null : this.getTarget().equals(other.getTarget()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
                && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
                && (this.getAction() == null ? other.getAction() == null : this.getAction().equals(other.getAction()))
                && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
                && (this.getComplaintCount() == null ? other.getComplaintCount() == null : this.getComplaintCount().equals(other.getComplaintCount()))
                && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVno() == null) ? 0 : getVno().hashCode());
        result = prime * result + ((getTarget() == null) ? 0 : getTarget().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getAction() == null) ? 0 : getAction().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getComplaintCount() == null) ? 0 : getComplaintCount().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", vno=").append(vno);
        sb.append(", target=").append(target);
        sb.append(", uid=").append(uid);
        sb.append(", targetId=").append(targetId);
        sb.append(", action=").append(action);
        sb.append(", reason=").append(reason);
        sb.append(", complaintCount=").append(complaintCount);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}