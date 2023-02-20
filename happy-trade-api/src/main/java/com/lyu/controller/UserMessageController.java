package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.UserMessage;
import com.lyu.entity.dto.UserMessageDTO;
import com.lyu.service.UserMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/19 20:11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("消息操作接口")
public class UserMessageController {
    @Resource
    private UserMessageService userMessageService;

    @PostMapping
    public CommonResult<Object> sendMessage(@NotNull @RequestBody UserMessage userMessage) {
        userMessageService.sendMessage(null, userMessage.getContent(), null, false, null, StpUtil.getLoginIdAsLong(), userMessage.getUidReceive());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @GetMapping
    public CommonResult<List<UserMessageDTO>> pullMessagesByUidReceiver() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.pullMessagesByUidReceiver(StpUtil.getLoginIdAsLong()));
    }

    @GetMapping("/unread")
    public CommonResult<List<UserMessageDTO>> pullUnreadMessagesByUidReceiver() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.pullUnreadMessagesByUidReceiver(StpUtil.getLoginIdAsLong()));
    }

    @GetMapping("/{uidSender}")
    public CommonResult<List<UserMessageDTO>> pullMessageBySenderAndMine(@NotNull @PathVariable Long uidSender) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.pullMessageBySenderAndMine(uidSender));
    }

}
