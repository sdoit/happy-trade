package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author LEE
 * @time 2023/1/1 10:32
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDTO {
    /**
     * 可能是uid、username、phone
     */
    @NotBlank(message = "登录凭证不可为空")
    private String certificate;
    @NotBlank(message = "[密码]不能为空")
    @ApiModelProperty("密码")
    private String password;

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

    @Override
    public String toString() {
        return "UserDTO{" +
                "certificate='" + certificate + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
