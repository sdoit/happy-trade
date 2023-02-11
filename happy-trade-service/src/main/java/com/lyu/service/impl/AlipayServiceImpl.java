package com.lyu.service.impl;

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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.CommodityBid;
import com.lyu.entity.WithdrawalOrder;
import com.lyu.exception.AliPayException;
import com.lyu.mapper.CommodityBidMapper;
import com.lyu.mapper.WithdrawalOrderMapper;
import com.lyu.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private WithdrawalOrderMapper withdrawalOrderMapper;
    @Resource
    private CommodityBidMapper commodityBidMapper;

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
            if (!Constant.ALIPAY_PAY_STATUS_SUCCESS.equals(status)) {
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
                if (Constant.ALIPAY_REFUND_SUCCESSFUL_LOGO.equals(response.getFundChange())) {
                    //退款成功,修改数据库
                    log.info(alipayTradeId + "-￥" + amount + "退款成功");
                    if (Constant.ALIPAY_PAY_TYPE_BID.equals(type)) {
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
}
