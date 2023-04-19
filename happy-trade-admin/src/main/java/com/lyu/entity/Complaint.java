package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName t_user_complaint
 */
@TableName(value = "t_user_complaint")
@Data
public class Complaint implements Serializable {
    /**
     * 申诉编号
     */
    @TableId(type = IdType.AUTO)
    private Long cno;

    /**
     * 违规编号
     */
    private Long vno;

    /**
     * 主体用户id
     */
    private Long uid;

    /**
     * 申诉理由
     */
    private String reason;

    /**
     * 是否撤销处理
     */
    private Boolean withdrawal;

    /**
     * 管理员回复
     */
    private String reply;

    /**
     * 申诉时间
     */
    private LocalDateTime time;

    @TableField(exist = false)

    private Violation violation;
    @TableField(exist = false)

    private User user;

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
        Complaint other = (Complaint) that;
        return (this.getCno() == null ? other.getCno() == null : this.getCno().equals(other.getCno()))
                && (this.getVno() == null ? other.getVno() == null : this.getVno().equals(other.getVno()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
                && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
                && (this.getWithdrawal() == null ? other.getWithdrawal() == null : this.getWithdrawal().equals(other.getWithdrawal()))
                && (this.getReply() == null ? other.getReply() == null : this.getReply().equals(other.getReply()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCno() == null) ? 0 : getCno().hashCode());
        result = prime * result + ((getVno() == null) ? 0 : getVno().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getWithdrawal() == null) ? 0 : getWithdrawal().hashCode());
        result = prime * result + ((getReply() == null) ? 0 : getReply().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cno=").append(cno);
        sb.append(", vno=").append(vno);
        sb.append(", uid=").append(uid);
        sb.append(", reason=").append(reason);
        sb.append(", withdrawal=").append(withdrawal);
        sb.append(", reply=").append(reply);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}