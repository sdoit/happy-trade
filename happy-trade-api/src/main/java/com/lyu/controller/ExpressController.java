package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.Express;
import com.lyu.service.ExpressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/3/1 20:46
 */
@RestController
@Validated
@RequestMapping("/api/express")

public class ExpressController {
    @Resource
    private ExpressService expressService;

    @ApiOperation("获取全部的快递公司")
    @GetMapping
    public CommonResult<List<Express>> getAllExpress(){
        List<Express> expressList = expressService.getExpressList();
        return CommonResult.Result(CodeAndMessage.SUCCESS, expressList);
    }

}
