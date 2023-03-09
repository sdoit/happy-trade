package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyu.common.ContentType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @TableName t_user_message
 */
@TableName(value = "t_user_message")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserMessage implements Serializable {
    /**
     * 消息id
     */
    @TableId(type = IdType.AUTO)
    private Long mid;
    /**
     * 两个聊天的用户为一个group
     */
    private Long groupId;
    /**
     * 标题
     */
    private String title;

    /**
     * 消息内容
     */
    @NotNull
    private String content;
    /**
     * 0:文本
     * 1：图片
     * 2：视频
     */
    private ContentType contentType;

    /**
     * 消息引导用户跳转的地址
     */
    private String url;

    /**
     * 消息类型：true-系统通知 false-用户私信
     */
    private Boolean systemNotify;

    /**
     * success、warning、info 和error。
     */
    private String messageType;

    /**
     * 发送人id
     */
    private Long uidSend;

    /**
     * 接收人id 为0时全体接收
     */
    @NotNull
    private Long uidReceive;

    /**
     * 发送时间
     */
    private LocalDateTime time;

    /**
     * 0-未读，1已读
     */
    @TableField(value = "read_already")
    private Boolean read;


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
        UserMessage other = (UserMessage) that;
        return (this.getMid() == null ? other.getMid() == null : this.getMid().equals(other.getMid()))
                && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
                && (this.getSystemNotify() == null ? other.getSystemNotify() == null : this.getSystemNotify().equals(other.getSystemNotify()))
                && (this.getMessageType() == null ? other.getMessageType() == null : this.getMessageType().equals(other.getMessageType()))
                && (this.getUidSend() == null ? other.getUidSend() == null : this.getUidSend().equals(other.getUidSend()))
                && (this.getUidReceive() == null ? other.getUidReceive() == null : this.getUidReceive().equals(other.getUidReceive()))
                && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
                && (this.getRead() == null ? other.getRead() == null : this.getRead().equals(other.getRead()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMid() == null) ? 0 : getMid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getSystemNotify() == null) ? 0 : getSystemNotify().hashCode());
        result = prime * result + ((getMessageType() == null) ? 0 : getMessageType().hashCode());
        result = prime * result + ((getUidSend() == null) ? 0 : getUidSend().hashCode());
        result = prime * result + ((getUidReceive() == null) ? 0 : getUidReceive().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getRead() == null) ? 0 : getRead().hashCode());
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
        sb.append(", url=").append(url);
        sb.append(", systemNotify=").append(systemNotify);
        sb.append(", messageType=").append(messageType);
        sb.append(", uidSend=").append(uidSend);
        sb.append(", uidReceive=").append(uidReceive);
        sb.append(", time=").append(time);
        sb.append(", read=").append(read);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}