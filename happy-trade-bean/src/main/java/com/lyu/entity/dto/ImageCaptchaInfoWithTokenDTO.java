package com.lyu.entity.dto;

import cloud.tianai.captcha.generator.common.model.dto.ImageCaptchaInfo;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/24 18:55
 */
@Data
public class ImageCaptchaInfoWithTokenDTO extends ImageCaptchaInfo {
    private String captchaToken;

}
