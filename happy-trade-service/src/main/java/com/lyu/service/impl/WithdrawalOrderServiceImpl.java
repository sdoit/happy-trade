package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.UserAmount;
import com.lyu.entity.UserAmountLog;
import com.lyu.entity.WithdrawalOrder;
import com.lyu.exception.UserAmountException;
import com.lyu.mapper.WithdrawalOrderMapper;
import com.lyu.service.AlipayService;
import com.lyu.service.UserAmountLogService;
import com.lyu.service.UserAmountService;
import com.lyu.service.WithdrawalOrderService;
import com.lyu.util.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author LEE
 * @time 2023/2/3 16:11
 */
@Slf4j
@Service
public class WithdrawalOrderServiceImpl implements WithdrawalOrderService {
    @Resource
    private WithdrawalOrderMapper withdrawalOrderMapper;

    @Resource
    private AlipayService alipayService;

    @Resource
    private UserAmountService userAmountService;
    @Resource
    private UserAmountLogService userAmountLogService;

    @Resource
    private IDUtil idUtil;

    @Resource
    private DateTimeFormatter dateTimeFormatterAliPay;
    @Value("${alipay.withdrawalOrder.title}")
    private String title;
    @Value("${alipay.withdrawalOrder.remark}")
    private String remark;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void createWithdrawalOrder(WithdrawalOrder withdrawalOrder) {
        long uidLogin = StpUtil.getLoginIdAsLong();

        //判断用户可用余额是否满足提现要求
        UserAmount userAmount = userAmountService.getUserAmountByUid(uidLogin);
        BigDecimal amountEffective = userAmount.getAmountEffective();
        if (amountEffective.compareTo(withdrawalOrder.getAmount()) < 0) {
            throw new UserAmountException(CodeAndMessage.INSUFFICIENT_AMOUNT_AVAILABLE.getCode(), CodeAndMessage.INSUFFICIENT_AMOUNT_AVAILABLE.getMessage());
        }
        //如果用户没有绑定支付宝，需要绑定后才能提现
        if (StrUtil.isBlank(userAmount.getAlipayUid())) {
            throw new UserAmountException(CodeAndMessage.UNBOUND_ALIPAY.getCode(), CodeAndMessage.UNBOUND_ALIPAY.getMessage());
        }
        withdrawalOrder.setUid(uidLogin);
        withdrawalOrder.setWid(idUtil.getNextWithdrawalOrderId(withdrawalOrder));
        withdrawalOrder.setTime(LocalDateTime.now());
        withdrawalOrderMapper.insert(withdrawalOrder);
        //扣款
        userAmount.setAmountEffective(userAmount.getAmountEffective().subtract(withdrawalOrder.getAmount()));
        userAmountService.updateUserAmount(userAmount);
        //记录日志
        UserAmountLog userAmountLog = new UserAmountLog();
        userAmountLog.setAmount(withdrawalOrder.getAmount());
        userAmountLog.setPlus(false);
        userAmountLog.setUid(uidLogin);
        userAmountLog.setEffective(true);
        userAmountLog.setSourceId(withdrawalOrder.getWid());
        userAmountLog.setTime(withdrawalOrder.getTime());
        userAmountLogService.logUserAmount(userAmountLog);
        //提现
        log.info("开始向支付宝发起提现申请");
        alipayService.withdraw(
                userAmount.getAlipayUid(),
                withdrawalOrder.getAmount(),
                withdrawalOrder.getWid(),
                title + ";uid：" + uidLogin + ":" + withdrawalOrder.getAmount().doubleValue(),
                remark + LocalDateTime.now().format(dateTimeFormatterAliPay), withdrawalOrder);
    }

    @Override
    public Integer completeWithdrawalOrder(WithdrawalOrder withdrawalOrder) {
        return withdrawalOrderMapper.updateById(withdrawalOrder);
    }
}
