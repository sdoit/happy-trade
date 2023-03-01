package com.lyu.controller;

import cloud.tianai.captcha.generator.common.model.dto.ImageCaptchaInfo;
import cn.hutool.core.util.StrUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.dto.ImageCaptchaTrackWithTokenDTO;
import com.lyu.entity.dto.ValidCaptchaResultDTO;
import com.lyu.util.CaptchaUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/2/23 14:01
 */
@Validated
@ApiOperation("验证码api")
@CrossOrigin(origins = "${vue.address}")
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {
    @PostMapping
    public CommonResult<Object> verify(@NotNull @RequestBody ImageCaptchaTrackWithTokenDTO imageCaptchaTrack) {
        ValidCaptchaResultDTO validCaptchaResultDTO = CaptchaUtil.validCaptcha(imageCaptchaTrack);
        if (StrUtil.isNotBlank(validCaptchaResultDTO.getPassToken())){
            return validCaptchaResultDTO.getPass() ? CommonResult.Result(CodeAndMessage.VERIFICATION_PASSED, validCaptchaResultDTO) :
                    CommonResult.Result(CodeAndMessage.VERIFICATION_FAILURE, false);
        }
        return validCaptchaResultDTO.getPass() ? CommonResult.Result(CodeAndMessage.VERIFICATION_PASSED, true) :
                CommonResult.Result(CodeAndMessage.VERIFICATION_FAILURE, false);
    }

    @GetMapping
    public CommonResult<ImageCaptchaInfo> refreshCaptcha() {
        ImageCaptchaInfo captcha = CaptchaUtil.getCaptcha();
        return CommonResult.Result(CodeAndMessage.SUCCESS, captcha);
    }
}
