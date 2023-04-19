package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.BooleanUtil;
import com.lyu.common.UserConstant;
import com.lyu.entity.User;
import com.lyu.mapper.UserMapper;
import com.lyu.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LEE
 * @time 2023/4/14 16:47
 */
@Service
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Object user = RedisUtil.get(UserConstant.REDIS_USER_LOGGED_KEY_PRE + loginId.toString());
        if (user == null) {
            user = userMapper.selectById((Long) loginId);
            if (user == null) {
                return null;
            }
        }
        ArrayList<String> roles = new ArrayList<>();
        roles.add("user");
        if (BooleanUtil.isTrue(((User) user).getAdmin())) {
            roles.add("admin");
        }
        return roles;
    }
}
