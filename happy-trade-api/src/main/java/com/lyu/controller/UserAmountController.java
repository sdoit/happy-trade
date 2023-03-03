package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.UserAmount;
import com.lyu.entity.UserAmountLog;
import com.lyu.service.UserAmountLogService;
import com.lyu.service.UserAmountService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/16 14:04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/amount")
@ApiOperation("用户账户金额接口")
public class UserAmountController {
    @Resource
    private UserAmountService userAmountService;
    @Resource
    private UserAmountLogService userAmountLogService;


    @GetMapping
    public CommonResult<UserAmount> getUserAmount() {
        UserAmount useramount = userAmountService.getUserAmountByUid(StpUtil.getLoginIdAsLong());
        return CommonResult.Result(CodeAndMessage.SUCCESS, useramount);
    }

    @GetMapping("/log")
    public CommonResult<List<UserAmountLog>> getUserAmountLog() {
        List<UserAmountLog> userAmountLogs = userAmountLogService.getUserAmountLogsByUid(StpUtil.getLoginIdAsLong());
        return CommonResult.Result(CodeAndMessage.SUCCESS, userAmountLogs);
    }
}
