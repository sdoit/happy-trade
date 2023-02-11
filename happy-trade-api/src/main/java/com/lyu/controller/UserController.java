package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.User;
import com.lyu.entity.dto.UserDTO;
import com.lyu.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2022/12/26 10:57
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("用户操作接口")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult<User> login(@NotNull @RequestBody UserDTO userDTO) {
        User user = new User();
        try {
            user.setUid(Long.parseLong(userDTO.getCertificate()));
        } catch (NumberFormatException exception) {
            log.debug("凭证不是uid");
        }
        user.setPhone(userDTO.getCertificate());
        user.setUsername(userDTO.getCertificate());
        user.setPassword(userDTO.getPassword());
        User userWithToken = userService.login(user);
        if (userWithToken != null) {
            return CommonResult.Result(CodeAndMessage.SUCCESS, userWithToken);
        }
        return CommonResult.Result(CodeAndMessage.UNEXPECTED_ERROR, null);


    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public CommonResult<String> login() {
        userService.logout();
        return CommonResult.Result(CodeAndMessage.SUCCESS,null);
    }

    @PostMapping("/update")
    public CommonResult<Object> update(@NotNull User user) {
        userService.update(user);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @GetMapping("/{uid}")
    public CommonResult<User> getUserByUid(@NotNull @PathVariable("uid") Long uid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, userService.getUserByUid(uid));

    }

    @PostMapping("/check")
    public CommonResult<User> checkLogin() {
        User user = userService.checkLogin();
        if (user == null) {
            CommonResult.Result(CodeAndMessage.USER_NOT_LOGIN, null);
        }
        return CommonResult.Result(CodeAndMessage.SUCCESS, user);
    }

}
