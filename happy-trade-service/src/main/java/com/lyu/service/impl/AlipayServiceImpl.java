package com.lyu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.easysdk.factory.Factory;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyu.common.AlipayConstant;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.common.Message;
import com.lyu.entity.CommodityBid;
import com.lyu.entity.Order;
import com.lyu.entity.User;
import com.lyu.entity.WithdrawalOrder;
import com.lyu.exception.AliPayException;
import com.lyu.mapper.CommodityBidMapper;
import com.lyu.mapper.WithdrawalOrderMapper;
import com.lyu.service.*;
import com.lyu.util.RedisUtil;
import com.lyu.util.aliyun.Sms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author LEE
 * @time 2023/2/3 13:26
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
    @Resource
    private CertAlipayRequest certAlipayRequest;
    @Resource
    @Lazy
    private OrderService orderService;
    @Resource
    private WithdrawalOrderMapper withdrawalOrderMapper;
    @Resource
    private CommodityBidMapper commodityBidMapper;
    @Resource
    @Lazy
    private CommodityBidService commodityBidService;
    @Resource
    private DateTimeFormatter dateTimeFormatterAliPay;
    @Resource
    private SseService sseService;
    @Resource
    private UserService userService;

    @Resource
    private Sms sms;
    @Resource
    private UserMessageService userMessageService;


    @Async("asyncPoolTaskExecutor")
    @Override
    public void withdraw(String alipayUid, BigDecimal amount, Long wid, String title, String remark, WithdrawalOrder withdrawalOrder) throws AliPayException {
        AlipayFundTransUniTransferResponse response;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
            AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
            AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
            model.setOutBizNo(String.valueOf(wid));
            model.setRemark(remark);
            model.setBusinessParams("{\"payer_show_name_use_alias\":\"true\"}");
            model.setBizScene("DIRECT_TRANSFER");
            Participant payeeInfo = new Participant();
            payeeInfo.setIdentity(alipayUid);
            payeeInfo.setIdentityType("ALIPAY_USER_ID");
            model.setPayeeInfo(payeeInfo);
            model.setTransAmount(amount.toString());
            model.setProductCode("TRANS_ACCOUNT_NO_PWD");
            model.setOrderTitle(title);
            request.setBizModel(model);
            response = alipayClient.certificateExecute(request);
            String status = response.getStatus();
            if (!AlipayConstant.ALIPAY_PAY_STATUS_SUCCESS.equals(status)) {
                throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
            }
        } catch (Exception e) {
            throw new AliPayException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), e.getMessage());
        }
        //提现成功
        log.info("提现成功，完成订单信息");
        withdrawalOrder.setStatus(response.getStatus());
        withdrawalOrder.setAlipayOrderId(response.getOrderId());
        withdrawalOrder.setAlipayFundOrderId(response.getPayFundOrderId());
//        withdrawalOrder.setTransDate(LocalDateTime.parse(response.getTransDate(), dateTimeFormatterAliPay));
        //完成订单
        withdrawalOrderMapper.updateById(withdrawalOrder);
        log.info("完成");
    }

    @Async("asyncPoolTaskExecutor")
    @Override
    public void refund(String alipayTradeId, BigDecimal amount, String id, String reason, String type) throws AliPayException {
        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no", alipayTradeId);
        bizContent.put("refund_amount", amount.doubleValue());
        bizContent.put("out_request_no", id);
        bizContent.put("refund_reason", reason);
        AlipayClient alipayClient;
        AlipayTradeRefundRequest request;
        log.info("已发起退款" + alipayTradeId + "-￥" + amount);
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
            request = new AlipayTradeRefundRequest();
            request.setBizContent(bizContent.toString());
            AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);
            if (response.isSuccess()) {
                if (AlipayConstant.ALIPAY_REFUND_SUCCESSFUL_LOGO.equals(response.getFundChange())) {
                    //退款成功,修改数据库
                    log.info(alipayTradeId + "-￥" + amount + "退款成功");
                    if (AlipayConstant.ALIPAY_PAY_TYPE_BID.equals(type)) {
                        commodityBidMapper.update(null, new UpdateWrapper<CommodityBid>().set("refund_time", LocalDateTime.now()));
                    }
                } else {
                    log.info("退款申请已发出还未到账");
                }
            } else {
                log.error(response.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean alipayNotify(Map<String, String> alipayParamMap) throws Exception {

        // 支付宝验签
        if (Factory.Payment.Common().verifyNotify(alipayParamMap)) {
            // 验签通过
            log.debug("交易名称: " + alipayParamMap.get("subject"));
            log.debug("交易状态: " + alipayParamMap.get("trade_status"));
            log.debug("支付宝交易凭证号: " + alipayParamMap.get("trade_no"));
            log.debug("商户订单号: " + alipayParamMap.get("out_trade_no"));
            log.debug("交易金额: " + alipayParamMap.get("total_amount"));
            log.debug("买家在支付宝唯一id: " + alipayParamMap.get("buyer_id"));
            log.debug("买家付款时间: " + alipayParamMap.get("gmt_payment"));
            log.debug("买家付款金额: " + alipayParamMap.get("buyer_pay_amount"));
            Long uid;
            // 开始更新订单信息
            //判断是否是bidOrder
            if (StrUtil.startWith(alipayParamMap.get("out_trade_no"), AlipayConstant.BID_ORDER_PREFIX)) {
                //是bid订单
                log.debug("开始完成 bidOrder");
                //从Redis中取出bid订单信息
                Object bidObj = RedisUtil.getAndDelete(Constant.REDIS_BID_UNPAID_KEY_PRE + Long.parseLong(alipayParamMap.get("out_trade_no")));
                if (bidObj == null) {
                    return false;
                }
                CommodityBid bidOrder = (CommodityBid) bidObj;
                bidOrder.setPayTime(LocalDateTime.parse(alipayParamMap.get("gmt_payment"), dateTimeFormatterAliPay));
                bidOrder.setTradeId(alipayParamMap.get("trade_no"));
                bidOrder.setBuyerAlipayId(alipayParamMap.get("buyer_id"));
                commodityBidService.completePay(bidOrder);
                Long uidSeller = bidOrder.getUidSeller();
                //储存消息到数据库，待消费
                userMessageService.sendNotification(Message.YOU_HAVE_A_NEW_BID_ON_YOUR_ITEM,
                        "/seller/bid/" + bidOrder.getCid(),
                        uidSeller
                );
                //发送短信给卖家 通知有用户购买了他的订单
                User userSeller = userService.getUserByUid(bidOrder.getUidSeller());
                sms.sendPaySuccessNotification(userSeller.getPhone());
                uid = bidOrder.getUidBuyer();
                //即时消息，不储存； 通知支付成功
                sseService.sendMsgToClientByClientId(String.valueOf(uid), Message.SSE_BID_ALPAY_COMPLETED, "/buyer/bid/");
            } else {
                //是form 订单
                log.debug("开始完成 Order");
                Object orderObj = RedisUtil.getAndDelete(Constant.REDIS_ORDER_UNPAID_KEY_PRE + Long.parseLong(alipayParamMap.get("out_trade_no")));
                if (orderObj == null) {
                    return false;
                }
                RedisUtil.getAndDelete(Constant.REDIS_ORDER_MAP_COMMODITY_KEY_PRE + Long.parseLong(alipayParamMap.get("out_trade_no")));
                Order order = (Order) orderObj;
                order.setOid(Long.parseLong(alipayParamMap.get("out_trade_no")));
                order.setPayTime(LocalDateTime.parse(alipayParamMap.get("gmt_payment"), dateTimeFormatterAliPay));
                order.setTradeId(alipayParamMap.get("trade_no"));
                order.setBuyerAlipayId(alipayParamMap.get("buyer_id"));
                orderService.completePayOrder(order);
                uid = order.getUidBuyer();
                //发送通知
                userMessageService.sendNotification(Message.A_BUYER_BOUGHT_YOUR_PRODUCT_DIRECTLY,
                        "/seller/order/" + order.getOid(),
                        order.getUidSeller()
                );
                //发送短信给卖家 通知有用户购买了他的订单
                User userSeller = userService.getUserByUid(order.getUidSeller());
                sms.sendPaySuccessNotification(userSeller.getPhone());
                //即时消息，不储存
                sseService.sendMsgToClientByClientId(String.valueOf(uid), Message.SSE_ORDER_ALPAY_COMPLETED, "/buyer/order/" + order.getOid());

            }
            return true;
        }
        return false;
    }
}
