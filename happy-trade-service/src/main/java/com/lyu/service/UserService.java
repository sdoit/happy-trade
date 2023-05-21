package com.lyu.service;

import com.lyu.entity.User;
import com.lyu.exception.UserException;

/**
 * @author LEE
 * @time 2022/12/26 10:42
 */
public interface UserService {
    /**
     * 登陆成功返回携带token的User，失败抛出异常
     *
     * @param user
     * @return
     * @throws UserException
     */
    User login(User user) throws UserException;

    /**
     * 用户登出
     */
    void logout();

    /**
     * 发送手机验证码
     * @param phone
     * @throws UserException
     */
    void sendCode(String phone,boolean forceUser) throws UserException;

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    User register(User user);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    User update(User user);

    /**
     * 通过uid查询用户
     *
     * @param uid
     * @return
     */
    User getUserByUid(Long uid);

    /**
     * 检查是否登录，如果登录返回登录的用户
     *
     * @return
     */
    User checkLogin();
}
