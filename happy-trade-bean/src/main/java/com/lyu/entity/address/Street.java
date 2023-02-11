package com.lyu.entity.address;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author LEE
 * @TableName t_street
 */
@TableName(value ="t_street")
@Data
public class Street implements Serializable {
    /**
     * 
     */
    @TableId
    private String code;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String aCode;

    /**
     * 
     */
    private String pCode;

    /**
     * 
     */
    private String cCode;

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
        Street other = (Street) that;
        return (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getACode() == null ? other.getACode() == null : this.getACode().equals(other.getACode()))
            && (this.getPCode() == null ? other.getPCode() == null : this.getPCode().equals(other.getPCode()))
            && (this.getCCode() == null ? other.getCCode() == null : this.getCCode().equals(other.getCCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getACode() == null) ? 0 : getACode().hashCode());
        result = prime * result + ((getPCode() == null) ? 0 : getPCode().hashCode());
        result = prime * result + ((getCCode() == null) ? 0 : getCCode().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", aCode=").append(aCode);
        sb.append(", pCode=").append(pCode);
        sb.append(", cCode=").append(cCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}