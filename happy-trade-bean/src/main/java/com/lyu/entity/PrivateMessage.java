package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName t_private_message
 */
@TableName(value ="t_private_message")
@Data
public class PrivateMessage implements Serializable {
    /**
     * 消息id
     */
    @TableId
    private Long mid;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型：0-系统通知 1-用户私信
     */
    private Integer type;

    /**
     * 发送人id
     */
    private Long uidSend;

    /**
     * 接收人id
     */
    private Long uidReceive;

    /**
     * 发送时间
     */
    private LocalDateTime time;

    /**
     * 0-未读，1已读
     */
    private Integer readed;

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
        PrivateMessage other = (PrivateMessage) that;
        return (this.getMid() == null ? other.getMid() == null : this.getMid().equals(other.getMid()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getUidSend() == null ? other.getUidSend() == null : this.getUidSend().equals(other.getUidSend()))
            && (this.getUidReceive() == null ? other.getUidReceive() == null : this.getUidReceive().equals(other.getUidReceive()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getReaded() == null ? other.getReaded() == null : this.getReaded().equals(other.getReaded()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMid() == null) ? 0 : getMid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getUidSend() == null) ? 0 : getUidSend().hashCode());
        result = prime * result + ((getUidReceive() == null) ? 0 : getUidReceive().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getReaded() == null) ? 0 : getReaded().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mid=").append(mid);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", uidSend=").append(uidSend);
        sb.append(", uidReceive=").append(uidReceive);
        sb.append(", time=").append(time);
        sb.append(", readed=").append(readed);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}