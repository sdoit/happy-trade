package com.lyu.service.impl;

import com.lyu.common.CodeAndMessage;
import com.lyu.entity.UserAmount;
import com.lyu.exception.UserAmountException;
import com.lyu.mapper.UserAmountMapper;
import com.lyu.service.UserAmountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author LEE
 * @time 2023/2/3 17:04
 */
@Service
public class UserAmountServiceImpl implements UserAmountService {
    @Resource
    private UserAmountMapper userAmountMapper;

    @Override
    public Integer updateUserAmount(UserAmount userAmount) {

        return userAmountMapper.updateById(userAmount);
    }

    @Override
    public UserAmount getUserAmountByUid(Long uid) {
        return userAmountMapper.selectById(uid);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Integer frozenAmount(Long uid, BigDecimal amount) {
        UserAmount userAmount = getUserAmountByUid(uid);
        if (userAmount.getAmountEffective().compareTo(amount) < 0) {
            throw new UserAmountException(CodeAndMessage.INSUFFICIENT_AMOUNT_AVAILABLE.getCode(), CodeAndMessage.INSUFFICIENT_AMOUNT_AVAILABLE.getMessage());
        }
        userAmount.setAmountFrozen(userAmount.getAmountFrozen().add(amount));
        userAmount.setAmountEffective(userAmount.getAmountEffective().subtract(amount));
        return updateUserAmount(userAmount);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Integer thawAmount(Long uid, BigDecimal amount) {
        UserAmount userAmount = getUserAmountByUid(uid);
        if (userAmount.getAmountFrozen().compareTo(amount) < 0) {
            throw new UserAmountException(CodeAndMessage.INSUFFICIENT_AMOUNT_AVAILABLE.getCode(), CodeAndMessage.INSUFFICIENT_AMOUNT_AVAILABLE.getMessage());
        }
        userAmount.setAmountFrozen(userAmount.getAmountFrozen().subtract(amount));
        userAmount.setAmountEffective(userAmount.getAmountEffective().add(amount));
        return updateUserAmount(userAmount);
    }
}
