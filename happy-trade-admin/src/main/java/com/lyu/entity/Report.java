package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lyu.common.TargetType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LEE
 * @TableName t_user_report
 */
@TableName(value = "t_user_report")
@Data
public class Report implements Serializable {
    /**
     * 举报编号
     */
    @TableId
    private Long rno;

    /**
     * 举报发起人
     */

    private Long uid;

    /**
     * 被举报主体人
     */
    @NotNull
    private Long uidTarget;

    /**
     * 被举报对象类型
     */
    @NotNull

    private TargetType target;

    /**
     * 被举报对象id
     */
    @NotNull
    private Long targetId;

    /**
     * 举报理由
     */
    @NotNull
    private String reason;

    /**
     * 违规记录编号
     */
    private Long vno;

    /**
     * 举报是否成立
     */
    private Boolean valid;

    /**
     * 管理员回复
     */
    private String reply;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private User userTarget;
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
        Report other = (Report) that;
        return (this.getRno() == null ? other.getRno() == null : this.getRno().equals(other.getRno()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
                && (this.getUidTarget() == null ? other.getUidTarget() == null : this.getUidTarget().equals(other.getUidTarget()))
                && (this.getTarget() == null ? other.getTarget() == null : this.getTarget().equals(other.getTarget()))
                && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
                && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
                && (this.getVno() == null ? other.getVno() == null : this.getVno().equals(other.getVno()))
                && (this.getValid() == null ? other.getValid() == null : this.getValid().equals(other.getValid()))
                && (this.getReply() == null ? other.getReply() == null : this.getReply().equals(other.getReply()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRno() == null) ? 0 : getRno().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getUidTarget() == null) ? 0 : getUidTarget().hashCode());
        result = prime * result + ((getTarget() == null) ? 0 : getTarget().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getVno() == null) ? 0 : getVno().hashCode());
        result = prime * result + ((getValid() == null) ? 0 : getValid().hashCode());
        result = prime * result + ((getReply() == null) ? 0 : getReply().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rno=").append(rno);
        sb.append(", uid=").append(uid);
        sb.append(", uidTarget=").append(uidTarget);
        sb.append(", target=").append(target);
        sb.append(", targetId=").append(targetId);
        sb.append(", reason=").append(reason);
        sb.append(", vno=").append(vno);
        sb.append(", valid=").append(valid);
        sb.append(", reply=").append(reply);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}