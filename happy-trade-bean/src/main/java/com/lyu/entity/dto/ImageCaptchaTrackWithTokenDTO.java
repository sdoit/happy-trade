package com.lyu.entity.dto;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/24 19:11
 */
@Data
public class ImageCaptchaTrackWithTokenDTO extends ImageCaptchaTrack {
    private String captchaToken;

}
