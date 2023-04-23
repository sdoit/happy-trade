package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lyu.common.AlipayConstant;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Message;
import com.lyu.entity.*;
import com.lyu.exception.OrderException;
import com.lyu.exception.UserException;
import com.lyu.mapper.*;
import com.lyu.service.*;
import com.lyu.util.aliyun.Sms;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @time 2023/4/22 19:58
 */
@Service
public class OrderReturnServiceImpl implements OrderReturnService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderReturnMapper orderReturnMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AlipayService alipayService;
    @Resource
    private UserMessageService userMessageService;
    @Resource
    private UserAddressMapper userAddressMapper;
    @Resource
    private UserAmountService userAmountService;
    @Resource
    private UserAmountLogService userAmountLogService;

    @Resource
    private Sms sms;
    @Resource
    private OrderService orderService;

    @Override
    public void createOrderReturn(Long oid, String reason) {
        Order order = orderMapper.selectById(oid);
        if (order == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INCONCLUSIVE_RESULT.getMessage());
        }
        if (!order.getStatus().equals(AlipayConstant.ORDER_STATUS_NORMAL)) {
            throw new OrderException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
        }
        if (!order.getUidBuyer().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        OrderReturn orderReturn = new OrderReturn();
        orderReturn.setOid(oid);
        orderReturn.setReason(reason);
        orderReturn.setTime(LocalDateTime.now());
        orderReturnMapper.insert(orderReturn);
        order.setStatus(AlipayConstant.ORDER_STATUS_REFUNDING);
        orderService.updateOrder(order);
        //通知卖家有人要退货
        User user = userMapper.selectById(order.getUidSeller());
        userMessageService.sendNotification(Message.ORDER_RETURN, "/seller/order/" + oid, order.getUidSeller());
        sms.sendNotification(user.getPhone(), Sms.SmsNotifyType.ReturnNews);
    }


    @Override
    public void agreeReturnOrder(Long oid, Long aid) {
        Order order = orderMapper.selectById(oid);
        OrderReturn orderReturn = orderReturnMapper.selectById(oid);
        if (order == null || orderReturn == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        if (!order.getUidSeller().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        UserAddress userAddress = userAddressMapper.selectById(aid);
        if (userAddress == null) {
            throw new UserException(CodeAndMessage.INCORRECT_SHIPPING_ADDRESS.getCode(), CodeAndMessage.INCORRECT_SHIPPING_ADDRESS.getMessage());
        }
        orderReturn.setAgree(Boolean.TRUE);
        orderReturn.setAid(aid);
        orderReturnMapper.updateById(orderReturn);
        //通知买家
        User user = userMapper.selectById(order.getUidBuyer());
        userMessageService.sendNotification(Message.ORDER_RETURN_AGREED, "/buyer/order/" + oid, order.getUidBuyer());
        sms.sendNotification(user.getPhone(), Sms.SmsNotifyType.ReturnAgree);
    }

    @Override
    public void rejectReturnOrder(Long oid, String rejectReason) {
        Order order = orderMapper.selectById(oid);
        OrderReturn orderReturn = orderReturnMapper.selectById(oid);
        if (order == null || orderReturn == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        if (!order.getUidSeller().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        orderReturn.setAgree(Boolean.FALSE);
        orderReturn.setRejectReason(rejectReason);
        orderReturnMapper.updateById(orderReturn);
        order.setStatus(AlipayConstant.ORDER_STATUS_NORMAL);
        orderMapper.updateById(order);
        //通知买家
        User user = userMapper.selectById(order.getUidBuyer());
        userMessageService.sendNotification(Message.ORDER_RETURN_AGREED, "/buyer/order/" + oid, order.getUidBuyer());
        sms.sendNotification(user.getPhone(), Sms.SmsNotifyType.ReturnAgree);
    }

    @Override
    public void shipReturnOrder(Long oid, String shipId) {
        Order order = orderMapper.selectById(oid);
        OrderReturn orderReturn = orderReturnMapper.selectById(oid);
        if (order == null || orderReturn == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        if (!order.getUidBuyer().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        orderReturn.setShipId(shipId);
        orderReturnMapper.updateById(orderReturn);
        //通知卖家-买家已回寄
        User user = userMapper.selectById(order.getUidSeller());
        userMessageService.sendNotification(Message.ORDER_RETURN_SHIPPED, "/seller/order/" + oid, order.getUidSeller());
        sms.sendNotification(user.getPhone(), Sms.SmsNotifyType.ReturnShipped);
    }

    @Override
    public void completeReturnOrder(Long oid) {
        Order order = orderMapper.selectById(oid);
        OrderReturn orderReturn = orderReturnMapper.selectById(oid);
        if (order == null || orderReturn == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        if (!order.getUidSeller().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        order.setStatus(AlipayConstant.ORDER_STATUS_REFUNDED);
        order.setRefundTime(LocalDateTime.now());
        orderMapper.updateById(order);
        //发起退款
        alipayService.refund(order.getTradeId(), order.getTotalAmount(), String.valueOf(order.getOid()), AlipayConstant.REFUND_DUE_USER_RETURN_ORDER, AlipayConstant.ALIPAY_PAY_TYPE_ORDER);
        UserAmount userAmount = userAmountService.getUserAmountByUid(order.getUidSeller());
        userAmount.setAmountFrozen(userAmount.getAmountFrozen().subtract(order.getTotalAmount()));
        userAmountService.updateUserAmount(userAmount);
        UserAmountLog userAmountLog = new UserAmountLog();
        userAmountLog.setAmount(order.getTotalAmount());
        userAmountLog.setEffective(false);
        userAmountLog.setPlus(false);
        userAmountLog.setSourceId(order.getOid());
        userAmountLog.setUid(order.getUidSeller());
        userAmountLog.setTime(LocalDateTime.now());
        userAmountLogService.logUserAmount(userAmountLog);
        //通知买家
        User user = userMapper.selectById(order.getUidBuyer());
        userMessageService.sendNotification(Message.ORDER_RETURN_COMPLETED, "/buyer/order/" + oid, order.getUidBuyer());
        sms.sendNotification(user.getPhone(), Sms.SmsNotifyType.ReturnCompleted);
    }
}
