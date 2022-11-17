package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_user_resource
*/
public class UserResource implements Serializable {

    /**
    * 资源id
    */
    @NotNull(message="[资源id]不能为空")
    @ApiModelProperty("资源id")
    private Long rid;
    /**
    * 资源类型，
0:图片
1:视频
2:音频
    */
    @NotNull(message="[资源类型， 0:图片 1:视频 2:音频]不能为空")
    @ApiModelProperty("资源类型， 0:图片 1:视频 2:音频")
    private Integer type;
    /**
    * 资源路径
    */
    @NotBlank(message="[资源路径]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("资源路径")
    @Length(max= 255,message="编码长度不能超过255")
    private String path;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("")
    @Length(max= 32,message="编码长度不能超过32")
    private String md5;
    /**
    * 创建资源用户的id
    */
    @NotNull(message="[创建资源用户的id]不能为空")
    @ApiModelProperty("创建资源用户的id")
    private Long uid;

    /**
    * 资源id
    */
    private void setRid(Long rid){
    this.rid = rid;
    }

    /**
    * 资源类型，
0:图片
1:视频
2:音频
    */
    private void setType(Integer type){
    this.type = type;
    }

    /**
    * 资源路径
    */
    private void setPath(String path){
    this.path = path;
    }

    /**
    * 
    */
    private void setMd5(String md5){
    this.md5 = md5;
    }

    /**
    * 创建资源用户的id
    */
    private void setUid(Long uid){
    this.uid = uid;
    }


    /**
    * 资源id
    */
    private Long getRid(){
    return this.rid;
    }

    /**
    * 资源类型，
0:图片
1:视频
2:音频
    */
    private Integer getType(){
    return this.type;
    }

    /**
    * 资源路径
    */
    private String getPath(){
    return this.path;
    }

    /**
    * 
    */
    private String getMd5(){
    return this.md5;
    }

    /**
    * 创建资源用户的id
    */
    private Long getUid(){
    return this.uid;
    }

}
