package com.lyu.service;

import com.lyu.entity.WithdrawalOrder;
import com.lyu.exception.AliPayException;

import java.math.BigDecimal;

/**
 * @author LEE
 * @time 2023/2/3 13:12
 */
public interface AlipayService {
    /**
     * 提现指定金额到指定支付宝uid中
     *
     * @param alipayUid       支付宝uid
     * @param amount          提现金额
     * @param wid             本次提现id
     * @param title           提现订单标题
     * @param remark          备注
     * @param withdrawalOrder 提现订单
     * @throws AliPayException
     */
    void withdraw(String alipayUid, BigDecimal amount, Long wid, String title, String remark, WithdrawalOrder withdrawalOrder) throws AliPayException;


    /**
     * 退款到原账户
     *
     * @param alipayTradeId 支付宝支付订单id
     * @param amount        退款金额
     * @param id            订单id （bid or oid）
     * @param reason        退款原因
     * @param type          订单类型：ORDER/BID
     * @throws AliPayException
     */
    void refund(String alipayTradeId, BigDecimal amount, String id, String reason, String type) throws AliPayException;
}
