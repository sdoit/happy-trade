package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
import com.lyu.entity.dto.UserViewHistoryDTO;
import com.lyu.service.UserViewHistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 20:03
 */
@Validated
@ApiOperation("浏览历史api")
@RestController
@RequestMapping("/api/history")
public class UserViewHistoryController {

    @Resource
    private UserViewHistoryService userViewHistoryService;

    @ApiOperation("[分页]获取用户的浏览历史")
    @GetMapping
    public CommonResult<List<UserViewHistoryDTO>> getAllViewHistoryByUid(@NotNull Integer page) {
        Page<UserViewHistoryDTO> pageUserViewHistoryDTO = new Page<>(page, Constant.COMMODITY_PER_PAGE);
        List<UserViewHistoryDTO> allViewHistory = userViewHistoryService.getAllViewHistoryByUid(pageUserViewHistoryDTO, StpUtil.getLoginIdAsLong());
        return CommonResult.Result(CodeAndMessage.SUCCESS, allViewHistory);
    }
}
