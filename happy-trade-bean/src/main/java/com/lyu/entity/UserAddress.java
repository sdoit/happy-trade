package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_user_address
*/
public class UserAddress implements Serializable {

    /**
    * 收货地址id
    */
    @NotNull(message="[收货地址id]不能为空")
    @ApiModelProperty("收货地址id")
    private Long aid;
    /**
    * 用户id
    */
    @NotNull(message="[用户id]不能为空")
    @ApiModelProperty("用户id")
    private Long uid;
    /**
    * 收货人姓名
    */
    @NotNull(message="[收货人姓名]不能为空")
    @ApiModelProperty("收货人姓名")
    private String name;
    /**
    * 收货人电话
    */
    @NotNull(message="[收货人电话]不能为空")
    @ApiModelProperty("收货人电话")
    private String phone;
    /**
    * 收货人省份
    */
    @NotBlank(message="[收货人省份]不能为空")
    @Size(max= 5,message="编码长度不能超过5")
    @ApiModelProperty("收货人省份")
    @Length(max= 5,message="编码长度不能超过5")
    private String province;
    /**
    * 收货人城市
    */
    @NotBlank(message="[收货人城市]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @ApiModelProperty("收货人城市")
    @Length(max= 10,message="编码长度不能超过10")
    private String city;
    /**
    * 收货人区县
    */
    @NotBlank(message="[收货人区县]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("收货人区县")
    @Length(max= 20,message="编码长度不能超过20")
    private String district;
    /**
    * 收货人详细地址
    */
    @NotBlank(message="[收货人详细地址]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("收货人详细地址")
    @Length(max= 50,message="编码长度不能超过50")
    private String address;
    /**
    * bool， 1:默认地址 0:非默认地址
    */
    @NotNull(message="[bool， 1:默认地址 0:非默认地址]不能为空")
    @ApiModelProperty("bool， 1:默认地址 0:非默认地址")
    private Integer defaultAddress;

    /**
    * 收货地址id
    */
    private void setAid(Long aid){
    this.aid = aid;
    }

    /**
    * 用户id
    */
    private void setUid(Long uid){
    this.uid = uid;
    }

    /**
    * 收货人姓名
    */
    private void setName(String name){
    this.name = name;
    }

    /**
    * 收货人电话
    */
    private void setPhone(String phone){
    this.phone = phone;
    }

    /**
    * 收货人省份
    */
    private void setProvince(String province){
    this.province = province;
    }

    /**
    * 收货人城市
    */
    private void setCity(String city){
    this.city = city;
    }

    /**
    * 收货人区县
    */
    private void setDistrict(String district){
    this.district = district;
    }

    /**
    * 收货人详细地址
    */
    private void setAddress(String address){
    this.address = address;
    }

    /**
    * bool， 1:默认地址 0:非默认地址
    */
    private void setDefaultAddress(Integer defaultAddress){
    this.defaultAddress = defaultAddress;
    }


    /**
    * 收货地址id
    */
    private Long getAid(){
    return this.aid;
    }

    /**
    * 用户id
    */
    private Long getUid(){
    return this.uid;
    }

    /**
    * 收货人姓名
    */
    private String getName(){
    return this.name;
    }

    /**
    * 收货人电话
    */
    private String getPhone(){
    return this.phone;
    }

    /**
    * 收货人省份
    */
    private String getProvince(){
    return this.province;
    }

    /**
    * 收货人城市
    */
    private String getCity(){
    return this.city;
    }

    /**
    * 收货人区县
    */
    private String getDistrict(){
    return this.district;
    }

    /**
    * 收货人详细地址
    */
    private String getAddress(){
    return this.address;
    }

    /**
    * bool， 1:默认地址 0:非默认地址
    */
    private Integer getDefaultAddress(){
    return this.defaultAddress;
    }

}
