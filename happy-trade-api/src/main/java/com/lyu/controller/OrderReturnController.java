package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.dto.OrderReturnDTO;
import com.lyu.service.OrderReturnService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/4/22 20:47
 */
@Validated
@RestController
@RequestMapping("/api/return")
@ApiOperation("退货操作接口")
public class OrderReturnController {
    @Resource
    private OrderReturnService orderReturnService;

    @PostMapping
    public CommonResult<Object> createOrderReturn(@NotNull @RequestBody OrderReturnDTO orderReturn) {
        if (orderReturn.getReason() == null) {
            return CommonResult.Result(CodeAndMessage.WRONG_REQUEST_PARAMETER, null);
        }
        orderReturnService.createOrderReturn(orderReturn.getOid(), orderReturn.getReason());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @PostMapping("/agree")
    public CommonResult<Object> agreeReturnOrder(@NotNull @RequestBody OrderReturnDTO orderReturn) {
        if (orderReturn.getAid() == null) {
            return CommonResult.Result(CodeAndMessage.WRONG_REQUEST_PARAMETER, null);
        }
        orderReturnService.agreeReturnOrder(orderReturn.getOid(), orderReturn.getAid());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);

    }

    @PostMapping("/reject")
    public CommonResult<Object> rejectReturnOrder(@NotNull @RequestBody OrderReturnDTO orderReturn) {
        if (orderReturn.getRejectReason() == null) {
            return CommonResult.Result(CodeAndMessage.WRONG_REQUEST_PARAMETER, null);
        }
        orderReturnService.rejectReturnOrder(orderReturn.getOid(), orderReturn.getRejectReason());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
    @PostMapping("/ship")
    public CommonResult<Object> shipReturnOrder(@NotNull @RequestBody OrderReturnDTO orderReturn) {
        if (orderReturn.getShipId() == null) {
            return CommonResult.Result(CodeAndMessage.WRONG_REQUEST_PARAMETER, null);
        }
        orderReturnService.shipReturnOrder(orderReturn.getOid(), orderReturn.getShipId());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
    @PostMapping("/complete/{oid}")
    public CommonResult<Object> completeReturnOrder(@PathVariable("oid") @NotNull Long oid) {
        orderReturnService.completeReturnOrder(oid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);

    }
}
