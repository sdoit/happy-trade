package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LEE
 * @TableName t_user_address
 */
@TableName(value = "t_user_address")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAddress implements Serializable {
    /**
     * 收货地址id
     */
    @TableId(type = IdType.AUTO)
    private Long aid;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 收货人姓名
     */
    @NotNull
    private String name;

    /**
     * 收货人电话
     */
    @NotNull
    private String phone;

    /**
     * 街道代码
     */
    @NotNull
    private String streetCode;
    /**
     * 收货人详细地址
     */
    @NotNull
    private String address;

    @JsonIgnore
    private boolean del;

    private String tag;

    /**
     * bool， 1:默认地址 0:非默认地址
     */
    private Boolean defaultAddress;

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
        UserAddress other = (UserAddress) that;
        return (this.getAid() == null ? other.getAid() == null : this.getAid().equals(other.getAid()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getStreetCode() == null ? other.getStreetCode() == null : this.getStreetCode().equals(other.getStreetCode()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getDefaultAddress() == null ? other.getDefaultAddress() == null : this.getDefaultAddress().equals(other.getDefaultAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAid() == null) ? 0 : getAid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getStreetCode() == null) ? 0 : getStreetCode().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getDefaultAddress() == null) ? 0 : getDefaultAddress().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", streetCode=").append(streetCode);
        sb.append(", address=").append(address);
        sb.append(", defaultAddress=").append(defaultAddress);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}