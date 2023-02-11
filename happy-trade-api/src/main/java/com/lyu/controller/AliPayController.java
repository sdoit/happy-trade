package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.common.Message;
import com.lyu.entity.AliPay;
import com.lyu.entity.CommodityBid;
import com.lyu.entity.Order;
import com.lyu.exception.AliPayException;
import com.lyu.service.CommodityBidService;
import com.lyu.service.OrderService;
import com.lyu.service.SSEService;
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
@CrossOrigin(origins = "${vue.address}")
public class AliPayController {

    @Resource
    private OrderService orderService;
    @Resource
    private CommodityBidService commodityBidService;
    @Resource
    private DateTimeFormatter dateTimeFormatterAliPay;
    @Resource
    private SSEService sseService;
    @Resource
    private RedisUtil redisUtil;

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
        if (aliPay.getType() != null && aliPay.getType().equals(Constant.ALIPAY_PAY_TYPE_BID)) {

            Object bidObj = redisUtil.get(Constant.REDIS_BID_UNPAID_KEY_PRE + aliPay.getTraceNo().substring(1));
            if (bidObj == null) {
                throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
            }
            CommodityBid commodityBid = (CommodityBid) bidObj;
            if (commodityBid.getUidBuyer() != uid) {
                //登录用户与订单用户不一致
                throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
            }
        } else {
            Object orderObj = redisUtil.get(Constant.REDIS_ORDER_UNPAID_KEY_PRE + aliPay.getTraceNo());
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
        LocalDateTime timeExpire = LocalDateTime.now().plusMinutes(Constant.ALIPAY_TIME_EXPIRE);
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
        if (request.getParameter(Constant.ALIPAY_PAY_STATUS_KEY).equals(Constant.ALIPAY_PAY_SUCCESS_VALUE)) {
            Map<String, String> params = new HashMap<>(75);
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                log.debug("交易名称: " + params.get("subject"));
                log.debug("交易状态: " + params.get("trade_status"));
                log.debug("支付宝交易凭证号: " + params.get("trade_no"));
                log.debug("商户订单号: " + params.get("out_trade_no"));
                log.debug("交易金额: " + params.get("total_amount"));
                log.debug("买家在支付宝唯一id: " + params.get("buyer_id"));
                log.debug("买家付款时间: " + params.get("gmt_payment"));
                log.debug("买家付款金额: " + params.get("buyer_pay_amount"));
                Long uid;
                // 开始更新订单信息
                //判断是否是bidOrder
                if (StrUtil.startWith(params.get("out_trade_no"), Constant.BID_ORDER_PREFIX)) {
                    //是bid订单
                    log.debug("开始完成 bidOrder");
                    //从Redis中取出bid订单信息
                    Object bidObj = redisUtil.getAndDelete(Constant.REDIS_BID_UNPAID_KEY_PRE + Long.parseLong(params.get("out_trade_no")));
                    if (bidObj == null) {
                        return "error";
                    }
                    CommodityBid bidOrder = (CommodityBid) bidObj;
                    bidOrder.setPayTime(LocalDateTime.parse(params.get("gmt_payment"), dateTimeFormatterAliPay));
                    bidOrder.setTradeId(params.get("trade_no"));
                    bidOrder.setBuyerAlipayId(params.get("buyer_id"));
                    commodityBidService.completePay(bidOrder);
                    //发送消息到卖家
                    Long uidSeller=bidOrder.getUidSeller();
                    sseService.sendMessage(String.valueOf(uidSeller),Message.ITEM_HAS_A_NEW_BID,"/seller/bid");
                    uid = bidOrder.getUidBuyer();
                    sseService.sendMessage(String.valueOf(uid), Message.SSE_ALPAY_COMPLETED,"/buyer/bid/");

                } else {
                    //是form 订单
                    log.debug("开始完成 Order");
                    Object orderObj = redisUtil.getAndDelete(Constant.REDIS_ORDER_UNPAID_KEY_PRE + Long.parseLong(params.get("out_trade_no")));
                    if (orderObj == null) {
                        return "error";
                    }
                    redisUtil.getAndDelete(Constant.REDIS_ORDER_MAP_COMMODITY_KEY_PRE + Long.parseLong(params.get("out_trade_no")));
                    Order order = (Order) orderObj;
                    order.setOid(Long.parseLong(params.get("out_trade_no")));
                    order.setPayTime(LocalDateTime.parse(params.get("gmt_payment"), dateTimeFormatterAliPay));
                    order.setTradeId(params.get("trade_no"));
                    order.setBuyerAlipayId(params.get("buyer_id"));
                    orderService.completePayOrder(order);
                    uid = order.getUidBuyer();
                    sseService.sendMessage(String.valueOf(uid), Message.SSE_ALPAY_COMPLETED,"/buyer/order/"+order.getOid());
                }

            }
        }
        return "success";
    }


    @GetMapping("/result")
    public String payResult(HttpServletRequest request) throws Exception {
        Thread.sleep(600);
        return "ok";
    }
}