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
* @TableName t_system_notice
*/
public class SystemNotice implements Serializable {

    /**
    * 通知id
    */
    @NotNull(message="[通知id]不能为空")
    @ApiModelProperty("通知id")
    private Long nid;
    /**
    * 通知标题
    */
    @NotBlank(message="[通知标题]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("通知标题")
    @Length(max= 30,message="编码长度不能超过30")
    private String title;
    /**
    * 通知内容
    */
    @NotBlank(message="[通知内容]不能为空")
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("通知内容")
    @Length(max= 200,message="编码长度不能超过200")
    private String content;
    /**
    * 接收本通知的群体，single:指定用户，all:全体用户，[roll]其他角色用户
    */
    @NotBlank(message="[接收本通知的群体，single:指定用户，all:全体用户，[roll]其他角色用户]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("接收本通知的群体，single:指定用户，all:全体用户，[roll]其他角色用户")
    @Length(max= 30,message="编码长度不能超过30")
    private String recipientType;
    /**
    * bool  是否被拉取过 0:未被拉取过,1:已被拉取过
    */
    @NotNull(message="[bool  是否被拉取过 0:未被拉取过,1:已被拉取过]不能为空")
    @ApiModelProperty("bool  是否被拉取过 0:未被拉取过,1:已被拉取过")
    private Integer state;
    /**
    * 当通知单用户时有效，指定接收者id
    */
    @NotNull(message="[当通知单用户时有效，指定接收者id]不能为空")
    @ApiModelProperty("当通知单用户时有效，指定接收者id")
    private Long recipientUid;
    /**
    * 发布时间
    */
    @NotNull(message="[发布时间]不能为空")
    @ApiModelProperty("发布时间")
    private Date time;

    /**
    * 通知id
    */
    private void setNid(Long nid){
    this.nid = nid;
    }

    /**
    * 通知标题
    */
    private void setTitle(String title){
    this.title = title;
    }

    /**
    * 通知内容
    */
    private void setContent(String content){
    this.content = content;
    }

    /**
    * 接收本通知的群体，single:指定用户，all:全体用户，[roll]其他角色用户
    */
    private void setRecipientType(String recipientType){
    this.recipientType = recipientType;
    }

    /**
    * bool  是否被拉取过 0:未被拉取过,1:已被拉取过
    */
    private void setState(Integer state){
    this.state = state;
    }

    /**
    * 当通知单用户时有效，指定接收者id
    */
    private void setRecipientUid(Long recipientUid){
    this.recipientUid = recipientUid;
    }

    /**
    * 发布时间
    */
    private void setTime(Date time){
    this.time = time;
    }


    /**
    * 通知id
    */
    private Long getNid(){
    return this.nid;
    }

    /**
    * 通知标题
    */
    private String getTitle(){
    return this.title;
    }

    /**
    * 通知内容
    */
    private String getContent(){
    return this.content;
    }

    /**
    * 接收本通知的群体，single:指定用户，all:全体用户，[roll]其他角色用户
    */
    private String getRecipientType(){
    return this.recipientType;
    }

    /**
    * bool  是否被拉取过 0:未被拉取过,1:已被拉取过
    */
    private Integer getState(){
    return this.state;
    }

    /**
    * 当通知单用户时有效，指定接收者id
    */
    private Long getRecipientUid(){
    return this.recipientUid;
    }

    /**
    * 发布时间
    */
    private Date getTime(){
    return this.time;
    }

}
