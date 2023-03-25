package com.lyu.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.UserConstant;
import com.lyu.entity.User;
import com.lyu.exception.UserException;
import com.lyu.mapper.UserMapper;
import com.lyu.service.UserService;
import com.lyu.util.RedisUtil;
import com.lyu.util.aliyun.Sms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2022/12/28 14:22
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private Sms sms;

    @Override
    public User login(User user) throws UserException {
        //从数据库获取用户 匹配 uid，username，phone
        User userInDb = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone()));
        if (userInDb == null) {
            //用户不存在
            throw new UserException(CodeAndMessage.WRONG_PASSWORD.getCode(), CodeAndMessage.WRONG_PASSWORD.getMessage());
        }

        if (StrUtil.isNotBlank(user.getValidationCode())) {
            //本次登录为手机验证码登录
            Object codeObj = RedisUtil.get(UserConstant.REDIS_USER_VALIDATION_CODE_KEY_PRE + user.getPhone());
            if (codeObj == null) {
                throw new UserException(CodeAndMessage.CODE_NOT_FOUND.getCode(), CodeAndMessage.CODE_NOT_FOUND.getMessage());
            }
            if (!user.getValidationCode().equals(codeObj.toString())) {
                throw new UserException(CodeAndMessage.CODE_IS_INCORRECT.getCode(), CodeAndMessage.CODE_IS_INCORRECT.getMessage());
            }
        } else {
            //对比密码
            String s = SaSecureUtil.sha1(user.getPassword());
            if (!userInDb.getPassword().equals(s)) {
                //密码错误
                throw new UserException(CodeAndMessage.WRONG_PASSWORD.getCode(), CodeAndMessage.WRONG_PASSWORD.getMessage());
            }
        }
        if (userInDb.getBanedTime() > 0) {
            //用户已封禁
            throw new UserException(CodeAndMessage.BANED_USER.getCode(), CodeAndMessage.BANED_USER.getMessage());
        }
        StpUtil.login(userInDb.getUid());
        userInDb.setPassword(null);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        userInDb.setTokenName(tokenInfo.getTokenName());
        userInDb.setTokenValue(tokenInfo.getTokenValue());
        //存放User对象到Redis
        RedisUtil.set(UserConstant.REDIS_USER_LOGGED_KEY_PRE + userInDb.getUid(), userInDb);
        RedisUtil.expire(UserConstant.REDIS_USER_LOGGED_KEY_PRE + userInDb.getUid(), UserConstant.USER_INFORMATION_CACHE_DURATION * 60);
        //登录成功
        return userInDb;
    }

    @Override
    public void logout() {
        String uid = StpUtil.getLoginIdAsString();
        //删除Redis中的User
        String key = UserConstant.REDIS_USER_LOGGED_KEY_PRE + uid;
        RedisUtil.getAndDelete(key);
        StpUtil.logout();
    }

    @Override
    public void sendCode(String phone) throws UserException {
        //判断是否已发验证码
        long time = RedisUtil.getTime(UserConstant.REDIS_USER_VALIDATION_CODE_KEY_PRE + phone);
        if (time != UserConstant.REDIS_CODE_EXPIRED_TIME && UserConstant.USER_VALIDATION_CODE_EXPIRATION_TIME - time < UserConstant.GET_CODE_INTERVAL_TIME) {
            //验证码已发送，但重试时间小于验证码发送间隔时间
            throw new UserException(CodeAndMessage.CODE_IS_SEND_TRY_AGAIN_LATER.getCode(), CodeAndMessage.CODE_IS_SEND_TRY_AGAIN_LATER.getMessage());
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        if (user == null) {
            throw new UserException(CodeAndMessage.USER_NOT_EXIST.getCode(), CodeAndMessage.USER_NOT_EXIST.getMessage());
        }
        if (user.getBanedTime() > 0) {
            //用户已封禁
            throw new UserException(CodeAndMessage.BANED_USER.getCode(), CodeAndMessage.BANED_USER.getMessage());
        }
        int code = RandomUtil.randomInt(1000, 999999);
        RedisUtil.set(UserConstant.REDIS_USER_VALIDATION_CODE_KEY_PRE + phone, code, UserConstant.USER_VALIDATION_CODE_EXPIRATION_TIME);
        //阿里云SMS发送手机验证码
        try {
            SendSmsResponse sendSmsResponse = sms.sendCode(phone, String.valueOf(code));
            if (!sendSmsResponse.getBody().getCode().equals(UserConstant.ALIYUN_CODE_SEND_SUCCESS)) {
                throw new UserException(CodeAndMessage.CODE_SEND_FAILED.getCode(), CodeAndMessage.CODE_SEND_FAILED.getMessage());
            }
        } catch (Exception e) {
            throw new UserException(1, e.getMessage());
        }
    }

    @Override
    public User register(User user) {
        User userInDb = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone()));

        if (userInDb != null) {
            throw new UserException(CodeAndMessage.USER_ALREADY_EXISTS.getCode(), CodeAndMessage.USER_ALREADY_EXISTS.getMessage());
        }
        if (user.getValidationCode() == null) {
            throw new UserException(CodeAndMessage.PLEASE_ENTER_VERIFICATION_CODE.getCode(), CodeAndMessage.PLEASE_ENTER_VERIFICATION_CODE.getMessage());
        }
        Object codeObj = RedisUtil.get(UserConstant.REDIS_USER_VALIDATION_CODE_KEY_PRE + user.getPhone());
        if (codeObj == null) {
            throw new UserException(CodeAndMessage.CODE_NOT_FOUND.getCode(), CodeAndMessage.CODE_NOT_FOUND.getMessage());
        }
        if (!user.getValidationCode().equals(codeObj.toString())) {
            throw new UserException(CodeAndMessage.CODE_IS_INCORRECT.getCode(), CodeAndMessage.CODE_IS_INCORRECT.getMessage());
        }
        user.setPassword(SaSecureUtil.sha1(user.getPassword()));
        userMapper.insert(user);
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User getUserByUid(Long uid) {
        User user = userMapper.selectById(uid);
        user.setPassword(null);
        return user;
    }

    @Override
    public User checkLogin() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        String uid = StpUtil.getLoginIdAsString();
        //从Redis取出User对象
        Object userObj = RedisUtil.get(UserConstant.REDIS_USER_LOGGED_KEY_PRE + uid);
        if (userObj == null) {
            User userInDb = getUserByUid(Long.valueOf(uid));
            //存放User对象到Redis
            RedisUtil.set(UserConstant.REDIS_USER_LOGGED_KEY_PRE + userInDb.getUid(), userInDb);
            RedisUtil.expire(UserConstant.REDIS_USER_LOGGED_KEY_PRE + userInDb.getUid(), UserConstant.USER_INFORMATION_CACHE_DURATION * 60);
            return userInDb;
        }
        return (User) userObj;
    }
}
