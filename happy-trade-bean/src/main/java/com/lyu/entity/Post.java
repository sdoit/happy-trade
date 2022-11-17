package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_post
*/
public class Post implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long pid;
    /**
    * 
    */
    @ApiModelProperty("")
    private Long uid;
    /**
    * 
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("")
    @Length(max= 50,message="编码长度不能超过50")
    private String title;
    /**
    * 
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("")
    @Length(max= -1,message="编码长度不能超过-1")
    private String content;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String tag;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String img;

    /**
    * 
    */
    private void setPid(Long pid){
    this.pid = pid;
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
    private void setTag(String tag){
    this.tag = tag;
    }

    /**
    * 
    */
    private void setImg(String img){
    this.img = img;
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
    private Long getUid(){
    return this.uid;
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

    /**
    * 
    */
    private String getTag(){
    return this.tag;
    }

    /**
    * 
    */
    private String getImg(){
    return this.img;
    }

}
