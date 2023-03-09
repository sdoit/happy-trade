package com.lyu.util;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.generator.ImageCaptchaGenerator;
import cloud.tianai.captcha.generator.common.model.dto.ImageCaptchaInfo;
import cloud.tianai.captcha.validator.ImageCaptchaValidator;
import cloud.tianai.captcha.validator.impl.BasicCaptchaTrackValidator;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lyu.common.CaptchaConstant;
import com.lyu.entity.dto.ImageCaptchaInfoWithTokenDTO;
import com.lyu.entity.dto.ImageCaptchaTrackWithTokenDTO;
import com.lyu.entity.dto.ValidCaptchaResultDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author LEE
 * @time 2023/2/23 12:55
 */
@Slf4j
public class CaptchaUtil {

    private static final ImageCaptchaGenerator imageCaptchaGenerator = SpringUtil.getBean("imageCaptchaGenerator");

    /**
     * 判断本次操作是否需要验证（验证码）
     * 【验证依据：每分钟内的请求超过一定值】
     *
     * @return
     */
    public static boolean needVerify() {
        String uidLogin = StpUtil.getLoginIdAsString();
        Integer requestCount = (Integer) RedisUtil.get(CaptchaConstant.REDIS_USER_NUMBER_OF_REQUESTS_PER_MINUTE_KEY_PRE + uidLogin);
        log.info(uidLogin + "-请求数量：" + requestCount);
        if (requestCount != null) {
            RedisUtil.increment(CaptchaConstant.REDIS_USER_NUMBER_OF_REQUESTS_PER_MINUTE_KEY_PRE + uidLogin, 1);
            return requestCount > CaptchaConstant.MAXIMUM_NUMBER_OF_REQUESTS_PER_MINUTES;
        }
        RedisUtil.set(CaptchaConstant.REDIS_USER_NUMBER_OF_REQUESTS_PER_MINUTE_KEY_PRE + uidLogin, 1, 60);
        return false;
    }

    public static boolean needVerify(String uidLogin, String passToken) {
        Object passTokenInRedisObj = RedisUtil.get(CaptchaConstant.REDIS_PASS_TOKEN_KEY + uidLogin);
        if (passTokenInRedisObj == null) {
            return true;
        }
        String passTokenInRedis = passTokenInRedisObj.toString();
        if (StrUtil.isNotBlank(passTokenInRedis)) {
            return (!passTokenInRedis.equals(passToken));
        }
        return true;
    }

    public static ImageCaptchaInfo getCaptcha() {
                /*
                生成滑块验证码图片, 可选项
                SLIDER (滑块验证码)
                ROTATE (旋转验证码)
                CONCAT (滑动还原验证码)
                WORD_IMAGE_CLICK (文字点选验证码)
         */
        ImageCaptchaInfo imageCaptchaInfo = imageCaptchaGenerator.generateCaptchaImage(CaptchaTypeConstant.SLIDER);
        System.out.println(imageCaptchaInfo);

        // 负责计算一些数据存到缓存中，用于校验使用
        // ImageCaptchaValidator负责校验用户滑动滑块是否正确和生成滑块的一些校验数据; 比如滑块到凹槽的百分比值
        ImageCaptchaValidator imageCaptchaValidator = new BasicCaptchaTrackValidator();
        // 这个map数据应该存到缓存中，校验的时候需要用到该数据
        Map<String, Object> validDataMap = imageCaptchaValidator.generateImageCaptchaValidData(imageCaptchaInfo);

        String uidLogin;
        if (StpUtil.isLogin()) {
            uidLogin = StpUtil.getLoginIdAsString();
        } else {
            //如果没有登录说本次请求为登录验证，随机生成一个token作为key
            uidLogin = RandomUtil.randomString(30);
            ImageCaptchaInfoWithTokenDTO imageCaptchaInfoWithTokenDTO = new ImageCaptchaInfoWithTokenDTO();
            BeanUtil.copyProperties(imageCaptchaInfo, imageCaptchaInfoWithTokenDTO);
            imageCaptchaInfoWithTokenDTO.setCaptchaToken(uidLogin);
            imageCaptchaInfo = imageCaptchaInfoWithTokenDTO;
        }
        //储存到redis
        RedisUtil.addMap(CaptchaConstant.REDIS_REQUEST_CAPTCHA_VALID_DATA_KEY + uidLogin, validDataMap);
        //等待验证时间3分钟
        RedisUtil.expire(CaptchaConstant.REDIS_REQUEST_CAPTCHA_VALID_DATA_KEY + uidLogin, 180);
        return imageCaptchaInfo;
    }

    public static ValidCaptchaResultDTO validCaptcha(ImageCaptchaTrackWithTokenDTO imageCaptchaTrack) {
        String uidLogin;
        if (StpUtil.isLogin()) {
            uidLogin = StpUtil.getLoginIdAsString();
        } else {
            uidLogin = imageCaptchaTrack.getCaptchaToken();
        }
        ImageCaptchaValidator sliderCaptchaValidator = new BasicCaptchaTrackValidator();
        Map<String, Object> validDataMap = RedisUtil.getHashEntries(CaptchaConstant.REDIS_REQUEST_CAPTCHA_VALID_DATA_KEY + uidLogin);
        boolean validResult = sliderCaptchaValidator.valid(imageCaptchaTrack, validDataMap);
        ValidCaptchaResultDTO validCaptchaResultDTO = new ValidCaptchaResultDTO();
        if (validResult) {
            RedisUtil.delete(CaptchaConstant.REDIS_USER_NUMBER_OF_REQUESTS_PER_MINUTE_KEY_PRE + uidLogin);
            if (!StpUtil.isLogin()) {
                //如果是登录验证，生成一个通行证发送给客户端，并存放在redis中，避免无限验证。
                String passToken = RandomUtil.randomString(30);
                validCaptchaResultDTO.setPassToken(passToken);
                RedisUtil.set(CaptchaConstant.REDIS_PASS_TOKEN_KEY + uidLogin, passToken, 180);
            }
        }
        validCaptchaResultDTO.setPass(validResult);
        return validCaptchaResultDTO;
    }
}
