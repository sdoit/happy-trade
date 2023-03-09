package com.lyu.common;

/**
 * @author LEE
 * @time 2023/3/4 9:19
 */
public class CaptchaConstant {

    /**
     * 一分钟内无需验证的最多的请求次数
     */
    public static final Integer MAXIMUM_NUMBER_OF_REQUESTS_PER_MINUTES = 5;

    /**
     * 记录用户每分钟的请求数 后面需要加 {uid}
     */
    public static final String REDIS_USER_NUMBER_OF_REQUESTS_PER_MINUTE_KEY_PRE = "requests:";
    /**
     * 请求验证数据 {uid}
     */
    public static final String REDIS_REQUEST_CAPTCHA_VALID_DATA_KEY = "captcha:";
    /**
     * 通行证 后加getCaptcha生成的标识token
     */
    public static final String REDIS_PASS_TOKEN_KEY = "captcha:passToken:";

}
