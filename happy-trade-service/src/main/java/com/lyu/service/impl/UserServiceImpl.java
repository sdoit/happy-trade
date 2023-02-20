package com.lyu.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.User;
import com.lyu.exception.UserException;
import com.lyu.mapper.UserMapper;
import com.lyu.service.UserService;
import com.lyu.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2022/12/28 14:22
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public User login(User user) throws UserException {
        //从数据库获取用户 匹配 uid，username，phone
        User userInDb = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone())
                .or()
                .eq("uid", user.getUid()));
        if (userInDb == null) {
            //用户不存在
            throw new UserException(CodeAndMessage.WRONG_PASSWORD.getCode(), CodeAndMessage.WRONG_PASSWORD.getMessage());

        }
        //对比密码
        String s = SaSecureUtil.sha1(user.getPassword());
        if (!userInDb.getPassword().equals(s)) {
            //密码错误
            throw new UserException(CodeAndMessage.WRONG_PASSWORD.getCode(), CodeAndMessage.WRONG_PASSWORD.getMessage());
        }
        //判断账号是否封禁
        if (userInDb.getBanedTime() == null || userInDb.getBanedTime() != 0) {
            throw new UserException(CodeAndMessage.BANED_USER.getCode(), CodeAndMessage.BANED_USER.getMessage());
        }
        StpUtil.login(userInDb.getUid());
        userInDb.setPassword(null);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        userInDb.setTokenName(tokenInfo.getTokenName());
        userInDb.setTokenValue(tokenInfo.getTokenValue());
        //存放User对象到Redis
        redisUtil.set(Constant.REDIS_USER_LOGGED_KEY_PRE + userInDb.getUid(), userInDb);
        //登录成功
        return userInDb;
    }

    @Override
    public void logout() {
        String uid = StpUtil.getLoginIdAsString();
        //删除Redis中的User
        String key = Constant.REDIS_USER_LOGGED_KEY_PRE + uid;
        redisUtil.getAndDelete(key);
        StpUtil.logout();
    }

    @Override
    public User register(User user) {
        User userInDb = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone())
                .or()
                .eq("uid", user.getUid()));

        if (userInDb != null) {
            throw new UserException(CodeAndMessage.USER_ALREADY_EXISTS.getCode(), CodeAndMessage.USER_ALREADY_EXISTS.getMessage());
        }
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
        String uid = StpUtil.getLoginIdAsString();
        //从Redis取出User对象
        return (User) redisUtil.get(Constant.REDIS_USER_LOGGED_KEY_PRE + uid);
    }
}
