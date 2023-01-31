package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.AliPay;
import com.lyu.entity.Order;
import com.lyu.exception.AliPayException;
import com.lyu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LEE
 * @time 2023/1/12 20:38
 */
@Slf4j
@RestController
@RequestMapping("/api/alipay")
@CrossOrigin(origins = "${vue.address}")
public class AliPayController {

    @Resource
    private OrderService orderService;
    @Resource
    private DateTimeFormatter dateTimeFormatterAliPay;

    /**
     * 订单支付接口
     *
     * @param aliPay 支付参数
     * @return
     */
    @GetMapping("/pay")
    public String pay(AliPay aliPay) {
        if (aliPay == null) {
            throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
        }
        long uid = StpUtil.getLoginIdAsLong();
        Order order = orderService.getOrderByOid(Long.parseLong(aliPay.getTraceNo()));
        if (order.getUid2() != uid) {
            //登录用户与订单用户不一致
            throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
        }
        AlipayTradePagePayResponse response;
        try {
            response = Payment.Page().pay(aliPay.getSubject(), aliPay.getTraceNo(), String.valueOf(aliPay.getTotalAmount()), aliPay.getReturnUrl());
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
        if (request.getParameter(Constant.ALIPAY_PAY_STATUS_KEY).equals(Constant.ALIPAY_PAY_SUCCESS_VALUE)) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>(75);
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                log.info("交易名称: " + params.get("subject"));
                log.info("交易状态: " + params.get("trade_status"));
                log.info("支付宝交易凭证号: " + params.get("trade_no"));
                log.info("商户订单号: " + params.get("out_trade_no"));
                log.info("交易金额: " + params.get("total_amount"));
                log.info("买家在支付宝唯一id: " + params.get("buyer_id"));
                log.info("买家付款时间: " + params.get("gmt_payment"));
                log.info("买家付款金额: " + params.get("buyer_pay_amount"));
                // 更新订单信息
                Order order = orderService.getOrderByOid(Long.parseLong(params.get("out_trade_no")));
                order.setPayTime(LocalDateTime.parse("2017-09-28 17:07:05", dateTimeFormatterAliPay));
                order.setTradeId(params.get("trade_no"));
                order.setBuyerAlipayId(params.get("buyer_id"));
                orderService.updateOrder(order);
            }
        }
        return "success";
    }

    @GetMapping("/result")
    public String payResult(HttpServletRequest request) throws Exception {
        Thread.sleep(60000);
        return "ok";
    }
}