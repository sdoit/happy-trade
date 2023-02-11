package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.address.Area;
import com.lyu.entity.address.City;
import com.lyu.entity.address.Province;
import com.lyu.entity.address.Street;
import com.lyu.service.AddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 区别于UserAddressController 此Controller仅获取省市区列表
 *
 * @author LEE
 * @time 2023/2/10 15:30
 */
@Validated
@ApiOperation("省市区列表api")
@CrossOrigin(origins = "${vue.address}")
@RestController()
@RequestMapping("/api/address")
public class AddressController {
    @Resource
    private AddressService addressService;


    @GetMapping
    public CommonResult<List<Province>> getProvinceList() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, addressService.getProvinceList());
    }

    @GetMapping("/{pCode}")
    public CommonResult<List<City>> getCityListByProvince(@NotNull @PathVariable("pCode") String pCode) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, addressService.getCityListByProvince(pCode));
    }

    @GetMapping("/{pCode}/{cCode}")
    public CommonResult<List<Area>> getAreaListByCityAndProvince(@NotNull @PathVariable("pCode") String pCode, @NotNull @PathVariable("cCode") String cCode) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, addressService.getAreaListByCityAndProvince(pCode, cCode));
    }

    @GetMapping("/{pCode}/{cCode}/{aCode}")
    public CommonResult<List<Street>> getStreetListByAreaAndCityAndProvince(@NotNull @PathVariable("pCode") String pCode,
                                                                            @NotNull @PathVariable("cCode") String cCode,
                                                                            @NotNull @PathVariable("aCode") String aCode) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, addressService.getStreetListByAreaAndCityAndProvince(pCode, cCode, aCode));

    }
}
