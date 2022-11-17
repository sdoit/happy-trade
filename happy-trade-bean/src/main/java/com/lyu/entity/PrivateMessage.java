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
* @TableName t_private_message
*/
public class PrivateMessage implements Serializable {

    /**
    * 消息id
    */
    @NotNull(message="[消息id]不能为空")
    @ApiModelProperty("消息id")
    private Long mid;
    /**
    * 标题
    */
    @NotBlank(message="[标题]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("标题")
    @Length(max= 30,message="编码长度不能超过30")
    private String title;
    /**
    * 消息内容
    */
    @NotBlank(message="[消息内容]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("消息内容")
    @Length(max= 255,message="编码长度不能超过255")
    private String content;
    /**
    * 消息类型：0-系统通知 1-用户私信
    */
    @NotNull(message="[消息类型：0-系统通知 1-用户私信]不能为空")
    @ApiModelProperty("消息类型：0-系统通知 1-用户私信")
    private Integer type;
    /**
    * 发送人id
    */
    @NotNull(message="[发送人id]不能为空")
    @ApiModelProperty("发送人id")
    private Long uidSend;
    /**
    * 接收人id
    */
    @NotNull(message="[接收人id]不能为空")
    @ApiModelProperty("接收人id")
    private Long uidReceive;
    /**
    * 发送时间
    */
    @NotNull(message="[发送时间]不能为空")
    @ApiModelProperty("发送时间")
    private Date time;
    /**
    * 0-未读，1已读
    */
    @NotNull(message="[0-未读，1已读]不能为空")
    @ApiModelProperty("0-未读，1已读")
    private Integer readed;

    /**
    * 消息id
    */
    private void setMid(Long mid){
    this.mid = mid;
    }

    /**
    * 标题
    */
    private void setTitle(String title){
    this.title = title;
    }

    /**
    * 消息内容
    */
    private void setContent(String content){
    this.content = content;
    }

    /**
    * 消息类型：0-系统通知 1-用户私信
    */
    private void setType(Integer type){
    this.type = type;
    }

    /**
    * 发送人id
    */
    private void setUidSend(Long uidSend){
    this.uidSend = uidSend;
    }

    /**
    * 接收人id
    */
    private void setUidReceive(Long uidReceive){
    this.uidReceive = uidReceive;
    }

    /**
    * 发送时间
    */
    private void setTime(Date time){
    this.time = time;
    }

    /**
    * 0-未读，1已读
    */
    private void setReaded(Integer readed){
    this.readed = readed;
    }


    /**
    * 消息id
    */
    private Long getMid(){
    return this.mid;
    }

    /**
    * 标题
    */
    private String getTitle(){
    return this.title;
    }

    /**
    * 消息内容
    */
    private String getContent(){
    return this.content;
    }

    /**
    * 消息类型：0-系统通知 1-用户私信
    */
    private Integer getType(){
    return this.type;
    }

    /**
    * 发送人id
    */
    private Long getUidSend(){
    return this.uidSend;
    }

    /**
    * 接收人id
    */
    private Long getUidReceive(){
    return this.uidReceive;
    }

    /**
    * 发送时间
    */
    private Date getTime(){
    return this.time;
    }

    /**
    * 0-未读，1已读
    */
    private Integer getReaded(){
    return this.readed;
    }

}
