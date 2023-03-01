package com.lyu.interceptor;

import cloud.tianai.captcha.generator.common.model.dto.ImageCaptchaInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.util.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LEE
 * @time 2023/2/23 12:00
 */
@Slf4j
public class CaptchaInterceptor implements HandlerInterceptor {
    /**
     * 验证码验证拦截，目前只拦截post请求
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (!request.getMethod().equals(RequestMethod.POST.name()) && !request.getMethod().equals(RequestMethod.PUT.name())) {
            return true;
        }
        if (StpUtil.isLogin()) {
            if (StrUtil.startWith(request.getRequestURI(), "/api/captcha")) {
                return true;
            }

            if (CaptchaUtil.needVerify()) {
                response.setContentType("application/json;charset=utf-8");
                //返回验证码
                ImageCaptchaInfo captcha = CaptchaUtil.getCaptcha();
                String resultJson = JSONObject.toJSONString(CommonResult.Result(CodeAndMessage.THIS_OPERATION_NEEDS_FURTHER_VERIFICATION, captcha));
                response.getWriter().print(resultJson);
                return false;
            }
        } else if (StrUtil.startWith(request.getRequestURI(), "/api/user/login")) {
            String passToken = request.getHeader("passToken");
            String uidToken = request.getHeader("uidToken");
            if (CaptchaUtil.needVerify(uidToken, passToken)) {
                response.setContentType("application/json;charset=utf-8");
                //登录操作 需要验证
                //返回验证码
                ImageCaptchaInfo captcha = CaptchaUtil.getCaptcha();
                String resultJson = JSONObject.toJSONString(CommonResult.Result(CodeAndMessage.THIS_OPERATION_NEEDS_FURTHER_VERIFICATION, captcha));
                response.getWriter().print(resultJson);
                return false;
            }
            return true;
        }
        return true;
    }
}
