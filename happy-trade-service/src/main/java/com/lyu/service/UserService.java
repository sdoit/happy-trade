package com.lyu.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.lyu.entity.User;
import com.lyu.exception.UserException;

/**
 * @author LEE
 * @time 2022/12/26 10:42
 */
public interface UserService{
    /**
     * 登陆成功返回token，失败抛出异常
     * @param user
     * @return
     * @throws UserException
     */
    SaTokenInfo login(User user) throws UserException;

    /**
     * 用户登出
     */
    void logout();

    /**
     * 注册用户
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
     * @param uid
     * @return
     */
    User getUserByUid(Long uid);


}
