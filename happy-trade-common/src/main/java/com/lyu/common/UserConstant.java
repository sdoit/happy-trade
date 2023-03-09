package com.lyu.common;

/**
 * @author LEE
 * @time 2023/3/4 9:20
 */
public class UserConstant {

    /**
     * 用户信息缓存的时长 单位（分钟）
     */
    public static final Integer USER_INFORMATION_CACHE_DURATION = 30;

    /**
     * 后面需要加 {cid}
     */
    public static final String REDIS_USER_LOGGED_KEY_PRE = "user:logged:";
    /**
     * 后加{phone}
     */
    public static final String REDIS_USER_VALIDATION_CODE_KEY_PRE = "user:VALIDATION_CODE:";
    /**
     * 手机验证码有效期 5分钟
     */
    public static final Integer USER_VALIDATION_CODE_EXPIRATION_TIME= 300;
    /**
     * 获取验证码间隔时间
     */
    public static final Integer GET_CODE_INTERVAL_TIME= 180;
    /**
     * 验证码过期
     */
    public static final Integer REDIS_CODE_EXPIRED_TIME= -2;
    /**
     * 阿里云验证码发送成功 code
     */
    public static final String ALIYUN_CODE_SEND_SUCCESS= "OK";

}
