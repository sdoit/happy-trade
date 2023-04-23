package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lyu.common.ContentType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 保存用户聊天对象的信息（用户联系人列表）
 * @author LEE
 * @TableName t_user_chat_list
 */
@TableName(value ="t_user_chat_list")
public class UserChat implements Serializable {

    private Long groupId;

    private Long uid;

    /**
     * 
     */
    private Long uidTarget;

    /**
     * 
     */
    private String lastMessage;

    private ContentType contentType;

    /**
     * 
     */
    private LocalDateTime time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUidTarget() {
        return uidTarget;
    }

    public void setUidTarget(Long uidTarget) {
        this.uidTarget = uidTarget;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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
        UserChat other = (UserChat) that;
        return (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getUidTarget() == null ? other.getUidTarget() == null : this.getUidTarget().equals(other.getUidTarget()))
            && (this.getLastMessage() == null ? other.getLastMessage() == null : this.getLastMessage().equals(other.getLastMessage()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getUidTarget() == null) ? 0 : getUidTarget().hashCode());
        result = prime * result + ((getLastMessage() == null) ? 0 : getLastMessage().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", uidTarget=").append(uidTarget);
        sb.append(", lastMessage=").append(lastMessage);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}