package com.lyu.entity.dto;

import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/24 19:37
 */
@Data
public class ValidCaptchaResultDTO {
    private Boolean pass;
    private String passToken;
}
