package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.service.UserChatService;
import com.lyu.service.impl.UserChatDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/3/3 12:22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/chatList")
@ApiOperation("用户聊天列表接口")
public class UserChatController {
    @Resource
    private UserChatService userChatService;

    @ApiOperation("获取用户聊天列表")
    @GetMapping
    public CommonResult<List<UserChatDTO>> getUserChatList() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userChatService.getAllUserChat());
    }

    @ApiOperation("从列表中删除")
    @DeleteMapping("/{uidTarget}")
    public CommonResult<Object> deleteUserFromChatList(@NotNull @PathVariable("uidTarget") Long uidTarget) {
        userChatService.deleteUserFromList(uidTarget);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
}
