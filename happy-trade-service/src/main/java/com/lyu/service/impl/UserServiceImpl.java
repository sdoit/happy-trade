package com.lyu.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.User;
import com.lyu.exception.*;
import com.lyu.mapper.UserMapper;
import com.lyu.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2022/12/28 14:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;


    @Override
    public SaTokenInfo login(User user) throws UserException {
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
        //登录成功
        return StpUtil.getTokenInfo();
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public User register(User user) {
        User userInDb = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone())
                .or()
                .eq("uid", user.getUid()));;
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
        return userMapper.selectById(uid);
    }
}
