package com.lyu.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.User;
import com.lyu.entity.dto.UserDTO;
import com.lyu.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2022/12/26 10:57
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("用户操作接口")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult<SaTokenInfo> login(UserDTO userDTO) {
        User user = new User();
        try {
            user.setUid(Long.parseLong(userDTO.getCertificate()));
        } catch (NumberFormatException exception) {
            log.debug("凭证不是uid");
        }
        user.setPhone(userDTO.getCertificate());
        user.setUsername(userDTO.getCertificate());
        user.setPassword(userDTO.getPassword());
        SaTokenInfo token = userService.login(user);
        if (token != null) {
            return CommonResult.createCommonResult(CodeAndMessage.SUCCESS, token);
        }
        return CommonResult.createCommonResult(CodeAndMessage.UNEXPECTED_ERROR, null);
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public CommonResult<String> login() {
        userService.logout();
        return null;
    }

    @PostMapping("/update")
    public CommonResult<Object> update(User user) {
        userService.update(user);
        return CommonResult.createCommonResult(CodeAndMessage.SUCCESS, null);
    }

}
