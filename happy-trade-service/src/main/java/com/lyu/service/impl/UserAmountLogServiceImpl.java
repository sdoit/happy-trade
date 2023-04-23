package com.lyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.entity.UserAmountLog;
import com.lyu.mapper.UserAmountLogMapper;
import com.lyu.service.UserAmountLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/16 13:54
 */
@Service
public class UserAmountLogServiceImpl implements UserAmountLogService {
    @Resource
    private UserAmountLogMapper userAmountLogMapper;

    @Override
    public Integer logUserAmount(UserAmountLog userAmountLog) {
        userAmountLog.setTime(LocalDateTime.now());
        return userAmountLogMapper.insert(userAmountLog);
    }

    @Override
    public List<UserAmountLog> getUserAmountLogsByUid(Long uid) {
        return userAmountLogMapper.selectList(new QueryWrapper<UserAmountLog>().eq("uid", uid).orderByDesc("time"));
    }
}
