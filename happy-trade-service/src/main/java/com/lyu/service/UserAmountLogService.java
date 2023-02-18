package com.lyu.service;

import com.lyu.entity.UserAmountLog;

import java.util.List;

/**
 * @author LEE
 * @time 2023/2/15 20:56
 */
public interface UserAmountLogService {
    /**
     * 记录用户金额增删详情
     *
     * @param userAmountLog
     * @return
     */
    Integer logUserAmount(UserAmountLog userAmountLog);

    /**
     * 获取指定用户的金额记录
     *
     * @param uid
     * @return
     */
    List<UserAmountLog> getUserAmountLogsByUid(Long uid);
}
