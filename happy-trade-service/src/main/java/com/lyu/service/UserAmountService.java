package com.lyu.service;

import com.lyu.entity.UserAmount;

import java.math.BigDecimal;

/**
 * @author LEE
 * @time 2023/2/3 15:45
 */
public interface UserAmountService {
    /**
     * 更新用户金额
     *
     * @param userAmount
     * @return
     */
    Integer updateUserAmount(UserAmount userAmount);

    /**
     * 根据uid获取金额
     *
     * @param uid
     * @return
     */
    UserAmount getUserAmountByUid(Long uid);

    /**
     * 冻结金额
     *
     * @param uid
     * @param amount 要冻结的金额数
     * @return
     */
    Integer frozenAmount(Long uid, BigDecimal amount);

    /**
     * 解冻金额
     *
     * @param uid
     * @param amount 要结冻的金额数
     * @return
     */
    Integer thawAmount(Long uid, BigDecimal amount);

}
