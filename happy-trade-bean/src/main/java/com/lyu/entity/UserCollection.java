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
* @TableName t_user_collection
*/
public class UserCollection implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long coid;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long uid;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Date time;

    /**
    * 
    */
    private void setCoid(Long coid){
    this.coid = coid;
    }

    /**
    * 
    */
    private void setUid(Long uid){
    this.uid = uid;
    }

    /**
    * 
    */
    private void setTime(Date time){
    this.time = time;
    }


    /**
    * 
    */
    private Long getCoid(){
    return this.coid;
    }

    /**
    * 
    */
    private Long getUid(){
    return this.uid;
    }

    /**
    * 
    */
    private Date getTime(){
    return this.time;
    }

}
