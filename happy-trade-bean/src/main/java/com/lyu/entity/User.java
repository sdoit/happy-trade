package com.lyu.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName t_user
*/
public class User implements Serializable {

    /**
    * 用户id
    */
    @NotNull(message="[用户id]不能为空")
    @ApiModelProperty("用户id")
    private Long uid;
    /**
    * 用户名，必须字母开头，可作为登录凭证
    */
    @NotBlank(message="[用户名，必须字母开头，可作为登录凭证]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("用户名，必须字母开头，可作为登录凭证")
    @Length(max= 20,message="编码长度不能超过20")
    private String username;
    /**
    * 密码
    */
    @NotBlank(message="[密码]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("密码")
    @Length(max= 20,message="编码长度不能超过20")
    private String password;
    /**
    * 手机号码，可作为登录凭证
    */
    @NotNull(message="[手机号码，可作为登录凭证]不能为空")
    @ApiModelProperty("手机号码，可作为登录凭证")
    private String phone;
    /**
    * 昵称
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("昵称")
    @Length(max= 20,message="编码长度不能超过20")
    private String nickname;
    /**
    * 用户头像
    */
    @NotBlank(message="[用户头像]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("用户头像")
    @Length(max= 50,message="编码长度不能超过50")
    private String avatar;
    /**
    * 个人简介
    */
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("个人简介")
    @Length(max= 200,message="编码长度不能超过200")
    private String introduction;

    /**
    * 用户id
    */
    private void setUid(Long uid){
    this.uid = uid;
    }

    /**
    * 用户名，必须字母开头，可作为登录凭证
    */
    private void setUsername(String username){
    this.username = username;
    }

    /**
    * 密码
    */
    private void setPassword(String password){
    this.password = password;
    }

    /**
    * 手机号码，可作为登录凭证
    */
    private void setPhone(String phone){
    this.phone = phone;
    }

    /**
    * 昵称
    */
    private void setNickname(String nickname){
    this.nickname = nickname;
    }

    /**
    * 用户头像
    */
    private void setAvatar(String avatar){
    this.avatar = avatar;
    }

    /**
    * 个人简介
    */
    private void setIntroduction(String introduction){
    this.introduction = introduction;
    }


    /**
    * 用户id
    */
    private Long getUid(){
    return this.uid;
    }

    /**
    * 用户名，必须字母开头，可作为登录凭证
    */
    private String getUsername(){
    return this.username;
    }

    /**
    * 密码
    */
    private String getPassword(){
    return this.password;
    }

    /**
    * 手机号码，可作为登录凭证
    */
    private String getPhone(){
    return this.phone;
    }

    /**
    * 昵称
    */
    private String getNickname(){
    return this.nickname;
    }

    /**
    * 用户头像
    */
    private String getAvatar(){
    return this.avatar;
    }

    /**
    * 个人简介
    */
    private String getIntroduction(){
    return this.introduction;
    }

}
