package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LEE
 * @time 2023/2/25 15:20
 */
@RestController
public class ErrorControllerImpl implements ErrorController {
    @RequestMapping("/error")
    public CommonResult<String> error(HttpServletRequest request, HttpServletResponse response) {
        Integer httpCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        response.setStatus(httpCode);
        return CommonResult.Result(CodeAndMessage.WRONG_REQUEST_PARAMETER, request.getAttribute("javax.servlet.error.request_uri") + "->" + httpCode);
    }


}
