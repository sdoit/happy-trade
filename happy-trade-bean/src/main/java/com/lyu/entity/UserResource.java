package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @TableName t_user_resource
 */
@TableName(value = "t_user_resource")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResource implements Serializable {
    /**
     * 资源id
     */
    @TableId(type = IdType.AUTO)
    private Long rid;

    /**
     * 资源类型:
     * image
     * video
     * ……
     */
    private String type;

    /**
     * 文件名
     */
    private String fileName;

    /**
     *
     */
    private String md5;

    /**
     * 创建资源用户的id
     */
    private Long uid;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDateTime date;

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
        UserResource other = (UserResource) that;
        return (this.getRid() == null ? other.getRid() == null : this.getRid().equals(other.getRid()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
                && (this.getMd5() == null ? other.getMd5() == null : this.getMd5().equals(other.getMd5()))
                && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRid() == null) ? 0 : getRid().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getMd5() == null) ? 0 : getMd5().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rid=").append(rid);
        sb.append(", type=").append(type);
        sb.append(", fileName=").append(fileName);
        sb.append(", md5=").append(md5);
        sb.append(", date=").append(date);
        sb.append(", uid=").append(uid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}