package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_question
*/
public class Question implements Serializable {

    /**
    * 问题id
    */
    @NotNull(message="[问题id]不能为空")
    @ApiModelProperty("问题id")
    private Long qid;
    /**
    * 提问者id
    */
    @NotNull(message="[提问者id]不能为空")
    @ApiModelProperty("提问者id")
    private Long uid;
    /**
    * 问题内容
    */
    @NotBlank(message="[问题内容]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("问题内容")
    @Length(max= 255,message="编码长度不能超过255")
    private String question;
    /**
    * 附加图片
    */
    @ApiModelProperty("附加图片")
    private Object img;
    /**
    * 赏金
    */
    @ApiModelProperty("赏金")
    private Object bounty;

    /**
    * 问题id
    */
    private void setQid(Long qid){
    this.qid = qid;
    }

    /**
    * 提问者id
    */
    private void setUid(Long uid){
    this.uid = uid;
    }

    /**
    * 问题内容
    */
    private void setQuestion(String question){
    this.question = question;
    }

    /**
    * 附加图片
    */
    private void setImg(Object img){
    this.img = img;
    }

    /**
    * 赏金
    */
    private void setBounty(Object bounty){
    this.bounty = bounty;
    }


    /**
    * 问题id
    */
    private Long getQid(){
    return this.qid;
    }

    /**
    * 提问者id
    */
    private Long getUid(){
    return this.uid;
    }

    /**
    * 问题内容
    */
    private String getQuestion(){
    return this.question;
    }

    /**
    * 附加图片
    */
    private Object getImg(){
    return this.img;
    }

    /**
    * 赏金
    */
    private Object getBounty(){
    return this.bounty;
    }

}
