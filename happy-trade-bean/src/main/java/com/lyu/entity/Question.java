package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName t_question
 */
@TableName(value ="t_question")
@Data
public class Question implements Serializable {
    /**
     * 问题id
     */
    @TableId
    private Long qid;

    /**
     * 提问者id
     */
    private Long uid;

    /**
     * 问题内容
     */
    private String question;

    /**
     * 附加图片
     */
    private Object img;

    /**
     * 赏金
     */
    private Object bounty;

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
        Question other = (Question) that;
        return (this.getQid() == null ? other.getQid() == null : this.getQid().equals(other.getQid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getQuestion() == null ? other.getQuestion() == null : this.getQuestion().equals(other.getQuestion()))
            && (this.getImg() == null ? other.getImg() == null : this.getImg().equals(other.getImg()))
            && (this.getBounty() == null ? other.getBounty() == null : this.getBounty().equals(other.getBounty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getQid() == null) ? 0 : getQid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getQuestion() == null) ? 0 : getQuestion().hashCode());
        result = prime * result + ((getImg() == null) ? 0 : getImg().hashCode());
        result = prime * result + ((getBounty() == null) ? 0 : getBounty().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", qid=").append(qid);
        sb.append(", uid=").append(uid);
        sb.append(", question=").append(question);
        sb.append(", img=").append(img);
        sb.append(", bounty=").append(bounty);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}