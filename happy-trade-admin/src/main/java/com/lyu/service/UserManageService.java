package com.lyu.service;

/**
 * @author LEE
 * @time 2023/4/9 19:20
 */
public interface UserManageService {
    /**
     * 封禁用户
     *
     * @param uid     被封禁用户
     * @param duration 封禁时长 （h）
     * @param reason   封禁原因
     */
    void banUser(Long uid, Integer duration, String reason);

    /**
     * 提前解除封禁
     *
     * @param uid
     */
    void unBanUser(Long uid);


}
