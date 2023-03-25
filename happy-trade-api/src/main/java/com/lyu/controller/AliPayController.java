package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.lyu.common.AlipayConstant;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.AliPay;
import com.lyu.entity.CommodityBid;
import com.lyu.entity.Order;
import com.lyu.exception.AliPayException;
import com.lyu.service.*;
import com.lyu.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LEE
 * @time 2023/1/12 20:38
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/api/alipay")
public class AliPayController {

    @Resource
    private DateTimeFormatter dateTimeFormatterAliPay;

    @Resource
    private AlipayService alipayService;

    /**
     * 订单支付接口
     *
     * @param aliPay 支付参数
     * @return
     */
    @GetMapping("/pay")
    public String pay(@NotNull AliPay aliPay) {
        if (aliPay == null) {
            throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
        }
        long uid = StpUtil.getLoginIdAsLong();
        if (aliPay.getType() != null && aliPay.getType().equals(AlipayConstant.ALIPAY_PAY_TYPE_BID)) {

            Object bidObj = RedisUtil.get(Constant.REDIS_BID_UNPAID_KEY_PRE + aliPay.getTraceNo().substring(1));
            if (bidObj == null) {
                throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
            }
            CommodityBid commodityBid = (CommodityBid) bidObj;
            if (commodityBid.getUidBuyer() != uid) {
                //登录用户与订单用户不一致
                throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
            }
        } else {
            Object orderObj = RedisUtil.get(Constant.REDIS_ORDER_UNPAID_KEY_PRE + aliPay.getTraceNo());
            if (orderObj == null) {
                throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
            }
            Order order = (Order) orderObj;
            if (order.getUidBuyer() != uid) {
                //登录用户与订单用户不一致
                throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
            }
        }
        AlipayTradePagePayResponse response;
        LocalDateTime timeExpire = LocalDateTime.now().plusMinutes(AlipayConstant.ALIPAY_TIME_EXPIRE);
//        String format = timeExpire.format(dateTimeFormatterAliPay);
        try {
//            response = Payment.Page().pay(aliPay.getSubject(), aliPay.getTraceNo(), String.valueOf(aliPay.getTotalAmount()), aliPay.getReturnUrl());
            response = Payment.Page().optional("time_expire", timeExpire.format(dateTimeFormatterAliPay)).pay(aliPay.getSubject(), aliPay.getTraceNo(), String.valueOf(aliPay.getTotalAmount()), aliPay.getReturnUrl());
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return response.getBody();
    }

    /**
     * 支付宝异步回调通知接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/notify")
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter(AlipayConstant.ALIPAY_PAY_STATUS_KEY).equals(AlipayConstant.ALIPAY_PAY_SUCCESS_VALUE)) {
            Map<String, String> alipayParamMap = new HashMap<>(75);
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                alipayParamMap.put(name, request.getParameter(name));
            }
            return alipayService.alipayNotify(alipayParamMap) ? "success" : "error";
        }
        return "error";
    }


    @GetMapping("/result")
    public String payResult(HttpServletRequest request) throws Exception {
        Thread.sleep(600);
        return "ok";
    }
}