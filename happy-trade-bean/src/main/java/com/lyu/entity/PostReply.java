package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_post_reply
*/
public class PostReply implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long rId;
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
    private Long pid;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String title;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String content;

    /**
    * 
    */
    private void setRId(Long rId){
    this.rId = rId;
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
    private void setPid(Long pid){
    this.pid = pid;
    }

    /**
    * 
    */
    private void setTitle(String title){
    this.title = title;
    }

    /**
    * 
    */
    private void setContent(String content){
    this.content = content;
    }


    /**
    * 
    */
    private Long getRId(){
    return this.rId;
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
    private Long getPid(){
    return this.pid;
    }

    /**
    * 
    */
    private String getTitle(){
    return this.title;
    }

    /**
    * 
    */
    private String getContent(){
    return this.content;
    }

}
