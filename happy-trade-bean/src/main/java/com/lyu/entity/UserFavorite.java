package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @TableName t_user_collection
 */
@TableName(value = "t_user_favorites")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFavorite implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long fid;


    private Long uid;

    private Long id;
    @TableField(exist = false)
    private Long cid;
    @TableField(exist = false)
    private Long rid;
    private LocalDateTime time;

    private Boolean isRequest;

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
        UserFavorite other = (UserFavorite) that;
        return (this.getFid() == null ? other.getFid() == null : this.getFid().equals(other.getFid()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
                && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
                && (this.getIsRequest() == null ? other.getIsRequest() == null : this.getIsRequest().equals(other.getIsRequest()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFid() == null) ? 0 : getFid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getIsRequest() == null) ? 0 : getIsRequest().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fid=").append(fid);
        sb.append(", uid=").append(uid);
        sb.append(", id=").append(id);
        sb.append(", time=").append(time);
        sb.append(", isRequest=").append(isRequest);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}