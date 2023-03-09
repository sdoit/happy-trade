package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author LEE
 * @time 2023/1/1 10:32
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDTO{
    /**
     * 可能username、phone
     */
    @NotBlank(message = "登录凭证不可为空")
    private String certificate;
    @ApiModelProperty("密码")
    private String password;


    /**
     * 手机登录验证码
     */
    private String validationCode;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "certificate='" + certificate + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
