package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_browsing_history
*/
public class BrowsingHistory implements Serializable {

    /**
    * 商品id
    */
    @NotNull(message="[商品id]不能为空")
    @ApiModelProperty("商品id")
    private Long cid;
    /**
    * 用户id
    */
    @NotNull(message="[用户id]不能为空")
    @ApiModelProperty("用户id")
    private Long uid;
    /**
    * 浏览时间
    */
    @NotNull(message="[浏览时间]不能为空")
    @ApiModelProperty("浏览时间")
    private Date time;

    /**
    * 商品id
    */
    private void setCid(Long cid){
    this.cid = cid;
    }

    /**
    * 用户id
    */
    private void setUid(Long uid){
    this.uid = uid;
    }

    /**
    * 浏览时间
    */
    private void setTime(Date time){
    this.time = time;
    }


    /**
    * 商品id
    */
    private Long getCid(){
    return this.cid;
    }

    /**
    * 用户id
    */
    private Long getUid(){
    return this.uid;
    }

    /**
    * 浏览时间
    */
    private Date getTime(){
    return this.time;
    }

}
