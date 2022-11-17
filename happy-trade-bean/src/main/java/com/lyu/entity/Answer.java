package com.lyu.entity;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @author LEE
*/
public class Answer implements Serializable {

    /**
    * 回答id
    */
    @NotNull(message="[回答id]不能为空")
    @ApiModelProperty("回答id")
    private Long aid;
    /**
    * 回答人用户id
    */
    @NotNull(message="[回答人用户id]不能为空")
    @ApiModelProperty("回答人用户id")
    private Long uid;
    /**
    * 问题id
    */
    @ApiModelProperty("问题id")
    private Long qid;
    /**
    * 回答内容
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("回答内容")
    @Length(max= -1,message="编码长度不能超过-1")
    private String answer;
    /**
    * 附加图片-> t_user_resource.rid
    */
    @ApiModelProperty("附加图片-> t_user_resource.rid")
    private Long rid;

    /**
    * 回答id
    */
    private void setAid(Long aid){
    this.aid = aid;
    }

    /**
    * 回答人用户id
    */
    private void setUid(Long uid){
    this.uid = uid;
    }

    /**
    * 问题id
    */
    private void setQid(Long qid){
    this.qid = qid;
    }

    /**
    * 回答内容
    */
    private void setAnswer(String answer){
    this.answer = answer;
    }

    /**
    * 附加图片-> t_user_resource.rid
    */
    private void setRid(Long rid){
    this.rid = rid;
    }


    /**
    * 回答id
    */
    private Long getAid(){
    return this.aid;
    }

    /**
    * 回答人用户id
    */
    private Long getUid(){
    return this.uid;
    }

    /**
    * 问题id
    */
    private Long getQid(){
    return this.qid;
    }

    /**
    * 回答内容
    */
    private String getAnswer(){
    return this.answer;
    }

    /**
    * 附加图片-> t_user_resource.rid
    */
    private Long getRid(){
    return this.rid;
    }

}
