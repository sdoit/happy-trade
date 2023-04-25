package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.CommodityType;
import com.lyu.service.CommodityTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/15 14:02
 */
@Validated
@RestController
@RequestMapping("/api/type")

@ApiOperation("type接口")
public class CommodityTypeController {

    @Resource
    private CommodityTypeService commodityTypeService;

    @GetMapping("/{pTid}")
    public CommonResult<List<CommodityType>> getCommodityTypes(@PathVariable("pTid") Integer pTid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityTypeService.getSubTypes(pTid));
    }

    @GetMapping
    public CommonResult<List<CommodityType>> getCommodityRootTypes() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityTypeService.getRootTypes());
    }

    @GetMapping("/all")
    public CommonResult<List<CommodityType>> getAllCommodityTypes() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityTypeService.getAllTypes());
    }

    @GetMapping("/t/{tid}")
    public CommonResult<CommodityType> getCommodityTypeByTid(@PathVariable("tid") Integer tid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityTypeService.getType(tid));
    }

}
