package com.lyu.service;

import com.lyu.entity.WithdrawalOrder;

/**
 * @author LEE
 * @time 2023/2/3 16:08
 */
public interface WithdrawalOrderService {
    /**
     * 用户发起提现申请，创建提现订单,并发起支付
     *
     * @param withdrawalOrder
     * @return
     */
    void createWithdrawalOrder(WithdrawalOrder withdrawalOrder);

    /**
     * 系统打款完成后调用，更新订单状态为完成到数据库
     *
     * @param withdrawalOrder
     * @return
     */
    Integer completeWithdrawalOrder(WithdrawalOrder withdrawalOrder);


}
