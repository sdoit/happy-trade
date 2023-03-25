package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.MessageConstant;
import com.lyu.entity.UserMessage;
import com.lyu.entity.dto.UserMessageDTO;
import com.lyu.service.UserMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
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
@ApiOperation("消息操作接口")
public class UserMessageController {
    @Resource
    private UserMessageService userMessageService;

    @PostMapping
    public CommonResult<String> sendMessage(@NotNull @RequestBody UserMessage userMessage) {
        long uidSend = StpUtil.getLoginIdAsLong();
        String text = userMessageService.sendMessage(null, userMessage.getContent(), userMessage.getContentType(), String.valueOf(uidSend),
                null,
                uidSend, userMessage.getUidReceive());
        return CommonResult.Result(CodeAndMessage.SUCCESS, text);
    }

    @GetMapping
    public CommonResult<List<UserMessageDTO>> pullMessagesByUidReceiver() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.pullMessagesByUidReceiver(StpUtil.getLoginIdAsLong()));
    }

    @GetMapping("/unread")
    public CommonResult<List<UserMessageDTO>> pullUnreadMessagesByUidReceiver() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.pullUnreadMessagesByUidReceiver(StpUtil.getLoginIdAsLong()));
    }

    @GetMapping("/notification")
    public CommonResult<UserMessageDTO> pullNotificationsByUidReceiver(@Nullable Integer page) {
        if (page == null) {
            page = 1;
        }
        Page<UserMessageDTO> iPage = new Page<>(page, MessageConstant.MESSAGE_COUNT_PER_PAGE);
        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.pullNotificationsByUidReceiver(iPage, StpUtil.getLoginIdAsLong()));
    }

    @GetMapping("/{uid}")
    public CommonResult<UserMessageDTO> pullMessageBySenderAndMine(@NotNull @PathVariable("uid") Long uid, @NotNull Integer page) {

        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.pullMessageBySenderAndMine(
                new Page<>(page, MessageConstant.MESSAGE_COUNT_PER_PAGE), uid));
    }

    @ApiOperation("消息设置为已读")
    @PutMapping("/{uid}")
    public CommonResult<Object> setReadByTargetUid(@NotNull @PathVariable("uid") Long uid) {
        userMessageService.setReadByTargetUid(uid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
}
