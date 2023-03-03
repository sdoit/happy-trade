package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.UserAddress;
import com.lyu.entity.dto.UserAddressDTO;
import com.lyu.service.UserAddressService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/2 20:07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/userAddress")
@ApiOperation("用户收获地址操作接口")
public class UserAddressController {
    @Resource
    private UserAddressService userAddressService;

    @ApiOperation("根据aid获取收货地址")
    @GetMapping("/{aid}")
    public CommonResult<UserAddress> getUserAddressByAid(@NotNull @PathVariable("aid") Long aid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userAddressService.getUserAddressByAid(aid));
    }

    @ApiOperation("根据uid获取所有收货地址")
    @GetMapping("/u")
    public CommonResult<List<UserAddressDTO>> getUserAddressByUid() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userAddressService.getUserAddressByUid(StpUtil.getLoginIdAsLong()));

    }

    @ApiOperation("根据uid获取默认收货地址")
    @GetMapping("/u/default")
    public CommonResult<UserAddress> getUserDefaultAddressByUid() {

        return CommonResult.Result(CodeAndMessage.SUCCESS, userAddressService.getDefaultUserAddressByUid(StpUtil.getLoginIdAsLong()));

    }

    @ApiOperation("保存收货地址")
    @PostMapping
    public CommonResult<Object> saveUserAddress(@RequestBody @NotNull UserAddress userAddress) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userAddressService.saveUserAddress(userAddress));
    }

    @ApiOperation("修改收货地址")
    @PutMapping
    public CommonResult<Object> updateUserAddress(@RequestBody @NotNull UserAddress userAddress) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userAddressService.updateUserAddress(userAddress));
    }

    @ApiOperation("删除收货地址")
    @DeleteMapping("/{aid}")
    public CommonResult<Object> deleteUserAddress(@PathVariable("aid") @NotNull Long aid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userAddressService.deleteUserAddress(aid));
    }

}
