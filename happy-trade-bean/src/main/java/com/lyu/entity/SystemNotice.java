package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName t_system_notice
 */
@TableName(value ="t_system_notice")
@Data
public class SystemNotice implements Serializable {
    /**
     * 通知id
     */
    @TableId
    private Long nid;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 接收本通知的群体，single:指定用户，all:全体用户，[roll]其他角色用户
     */
    private String recipientType;

    /**
     * bool  是否被拉取过 0:未被拉取过,1:已被拉取过
     */
    private Integer state;

    /**
     * 当通知单用户时有效，指定接收者id
     */
    private Long recipientUid;

    /**
     * 发布时间
     */
    private LocalDateTime time;

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
        SystemNotice other = (SystemNotice) that;
        return (this.getNid() == null ? other.getNid() == null : this.getNid().equals(other.getNid()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getRecipientType() == null ? other.getRecipientType() == null : this.getRecipientType().equals(other.getRecipientType()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getRecipientUid() == null ? other.getRecipientUid() == null : this.getRecipientUid().equals(other.getRecipientUid()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNid() == null) ? 0 : getNid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getRecipientType() == null) ? 0 : getRecipientType().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getRecipientUid() == null) ? 0 : getRecipientUid().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", recipientType=").append(recipientType);
        sb.append(", state=").append(state);
        sb.append(", recipientUid=").append(recipientUid);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}