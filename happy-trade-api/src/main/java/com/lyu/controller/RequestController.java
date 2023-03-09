package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
import com.lyu.entity.dto.RequestDTO;
import com.lyu.service.RequestService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/3/8 16:41
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/request")
@ApiOperation("用户求购接口")
public class RequestController {
    @Resource
    private RequestService requestService;

    @ApiOperation("发布新求购")
    @PostMapping
    public CommonResult<Long> saveRequest(@RequestBody @NotNull RequestDTO requestDTO) {
        Long id = requestService.addRequest(requestDTO);
        return CommonResult.Result(CodeAndMessage.SUCCESS, id);
    }

    @ApiOperation("编辑求购商品信息")
    @PutMapping
    public CommonResult<Object> editRequest(@RequestBody @NotNull RequestDTO requestDTO) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, requestService.updateRequest(requestDTO));
    }

    @ApiOperation("下架求购")
    @PutMapping("/down/{id}")
    public CommonResult<Object> takeDownRequest(@PathVariable("id") @NotNull Long id) {
        requestService.takeDownRequest(id);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @ApiOperation("上架求购")
    @PutMapping("/uploaded/{id}")
    public CommonResult<Object> uploadedRequest(@PathVariable("id") @NotNull Long id) {
        requestService.uploadedRequest(id);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }


    @ApiOperation("根据id获取指定求购商品")
    @GetMapping("/{id}")
    public CommonResult<RequestDTO> getRequestById(@NotNull @PathVariable("id") Long id) {
        RequestDTO request =requestService.getRequestById(id);
        return CommonResult.Result(CodeAndMessage.SUCCESS, request);
    }

    @ApiOperation("获取指定用户的商品")
    @GetMapping("/u")
    public CommonResult<List<RequestDTO>> getCommoditiesFromUser(@NotNull Integer page) {
        List<RequestDTO> requests = requestService.getRequestsFromUser(
                new Page<>(page, Constant.COMMODITY_PER_PAGE),
                StpUtil.getLoginIdAsLong());
        return CommonResult.Result(CodeAndMessage.SUCCESS, requests);
    }

    @ApiOperation("获取指定类型的商品")
    @GetMapping("/type/{typeId}")
    public CommonResult<List<RequestDTO>> getCommoditiesByType(@NotNull Integer page, @NotNull @PathVariable("typeId") Integer typeId) {
        List<RequestDTO> requests = requestService.getRequestsByType(
                new Page<>(page, Constant.COMMODITY_PER_PAGE), typeId);
        return CommonResult.Result(CodeAndMessage.SUCCESS, requests);
    }

    @ApiOperation("根据关键词获取商品（分页）/ 如果keyword为null，则依据用户浏览习惯返回推荐商品")
    @GetMapping
    public CommonResult<List<RequestDTO>> getCommoditiesByKeyWords(@Nullable String keyword, @NotNull Integer page) {
        Page<RequestDTO> requestDTOPage = new Page<>(page, Constant.COMMODITY_PER_PAGE);
        if (Strings.isBlank(keyword)) {
            if (StpUtil.isLogin()) {
                //如果用户已登录，根据用户浏览历史推荐
                return CommonResult.Result(CodeAndMessage.SUCCESS, requestService.getRequestsRecommendations(requestDTOPage));
            } else {
                //如果未登录，就随机最新商品
                return CommonResult.Result(CodeAndMessage.SUCCESS, requestService.getRequestsLatest(requestDTOPage));
            }
        }
        return CommonResult.Result(CodeAndMessage.SUCCESS, requestService.getRequestsByKeyWordsPage(keyword, requestDTOPage));
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public CommonResult<Object> deleteCommodityByCid(@PathVariable @NotNull Long id) {
        Integer result = requestService.deleteRequestById(id);
        return CommonResult.Result(CodeAndMessage.SUCCESS, result);
    }

}
