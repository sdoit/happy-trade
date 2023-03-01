package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
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

        long uidSend = StpUtil.getLoginIdAsLong();
        userMessageService.sendMessage(null, userMessage.getContent(), String.valueOf(uidSend),
                false, null,
                uidSend, userMessage.getUidReceive());
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

    @GetMapping("/{uid}")
    public CommonResult<UserMessageDTO> pullMessageBySenderAndMine(@NotNull @PathVariable("uid") Long uid, @NotNull Integer page) {

        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.pullMessageBySenderAndMine(new Page<UserMessageDTO>(page, Constant.MESSAGE_COUNT_PER_PAGE), uid));
    }

    @ApiOperation("获取登录用户的聊天列表")
    @GetMapping("/list")
    public CommonResult<List<UserMessageDTO>> getChatUserList() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userMessageService.getChatUserList());
    }

    @ApiOperation("消息设置为已读")
    @PutMapping("/{uid}")
    public CommonResult<Object> setReadByTargetUid(@NotNull @PathVariable("uid") Long uid) {
        userMessageService.setReadByTargetUid(uid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
}
