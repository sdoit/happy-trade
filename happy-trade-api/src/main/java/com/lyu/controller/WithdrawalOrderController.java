package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.WithdrawalOrder;
import com.lyu.service.WithdrawalOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/2/3 16:55
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/withdrawal")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("用户提现操作接口")
public class WithdrawalOrderController {

    @Resource
    private WithdrawalOrderService withdrawalOrderService;
    @PostMapping
    public CommonResult<Object> createWithdrawalOrder(@RequestBody @NotNull WithdrawalOrder withdrawalOrder) {
        withdrawalOrderService.createWithdrawalOrder(withdrawalOrder);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
}
