package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author LEE
 * @TableName t_answer
 */
@TableName(value ="t_answer")
@Data
public class Answer implements Serializable {
    /**
     * 回答id
     */
    @TableId
    private Long aid;

    /**
     * 回答人用户id
     */
    private Long uid;

    /**
     * 问题id
     */
    private Long qid;

    /**
     * 回答内容
     */
    private String answer;

    /**
     * 附加图片-> t_user_resource.rid
     */
    private Long rid;

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
        Answer other = (Answer) that;
        return (this.getAid() == null ? other.getAid() == null : this.getAid().equals(other.getAid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getQid() == null ? other.getQid() == null : this.getQid().equals(other.getQid()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getRid() == null ? other.getRid() == null : this.getRid().equals(other.getRid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAid() == null) ? 0 : getAid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getQid() == null) ? 0 : getQid().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getRid() == null) ? 0 : getRid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", uid=").append(uid);
        sb.append(", qid=").append(qid);
        sb.append(", answer=").append(answer);
        sb.append(", rid=").append(rid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}