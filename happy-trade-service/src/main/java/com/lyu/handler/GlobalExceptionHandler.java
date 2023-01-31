package com.lyu.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常控制
 * @author LEE
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 自定义全局异常
     */
    @ExceptionHandler({UserException.class, CommodityException.class, OrderException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Object> globalExceptionHandler(GlobalException e) {
        return new CommonResult<>(e.getCode(), false, e.getMessage(), null);
    }
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Object> saTokenNotLoginExceptionHandler(NotLoginException e) {
        return new CommonResult<>(CodeAndMessage.USER_NOT_LOGIN.getCode(), false, e.getMessage(), null);
    }

    @ExceptionHandler(UpdateFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Object> updateFileExceptionHandler(GlobalException e) {
        return new CommonResult<>(CodeAndMessage.UNEXPECTED_ERROR.getCode(), false, e.getMessage(), null);
    }

}