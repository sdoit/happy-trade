package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyu.common.reason.BanUserReason;
import com.lyu.entity.User;
import com.lyu.mapper.UserMapper;
import com.lyu.service.UserManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/4/14 14:40
 */
@Service
public class UserManageServiceImpl implements UserManageService {
    @Resource
    private UserMapper userMapper;
    @Override
    public void banUser(Long uid, Integer duration, BanUserReason reason) {
        //强制下线
        StpUtil.kickout(uid);
        userMapper.update(null, new UpdateWrapper<User>().set("baned_time", duration).set("ban_reason", reason.getText()).eq("uid", uid));
    }
    @Override
    public void unBanUser(Long uid) {
        userMapper.update(null, new UpdateWrapper<User>().set("baned_time", null).set("ban_reason", null).eq("uid", uid));
    }
}
