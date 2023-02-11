package com.lyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
import com.lyu.entity.UserViewHistory;
import com.lyu.entity.dto.UserViewHistoryDTO;
import com.lyu.service.UserViewHistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 20:03
 */
@Validated
@ApiOperation("浏览历史api")
@CrossOrigin(origins = "${vue.address}")
@RestController
@RequestMapping("/api/history")
public class UserViewHistoryController {

    @Resource
    private UserViewHistoryService userViewHistoryService;

    /**
     * ？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？是否应该存在 待定
     * @param userViewHistory
     * @return
     */
    @PostMapping()
    public CommonResult<Object> saveViewHistory(@NotNull UserViewHistory userViewHistory) {
        userViewHistoryService.saveViewHistory(userViewHistory.getCid());
        return CommonResult.Result(CodeAndMessage.INCONCLUSIVE_RESULT, null);
    }

    @GetMapping("/{uid}")
    public CommonResult<List<UserViewHistoryDTO>> getAllViewHistoryByUid(@NotNull() Integer page, @NotNull @PathVariable("uid") Long uid) {
        Page<UserViewHistoryDTO> pageUserViewHistoryDTO = new Page<>(page, Constant.COMMODITY_PER_PAGE);
        List<UserViewHistoryDTO> allViewHistory = userViewHistoryService.getAllViewHistoryByUid(pageUserViewHistoryDTO, uid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, allViewHistory);
    }
}
