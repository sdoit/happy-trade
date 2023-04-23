package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.*;
import com.lyu.entity.*;
import com.lyu.entity.dto.OrderDTO;
import com.lyu.entity.dto.RequestDTO;
import com.lyu.exception.CommodityException;
import com.lyu.exception.OrderException;
import com.lyu.exception.UserException;
import com.lyu.mapper.*;
import com.lyu.service.*;
import com.lyu.util.IDUtil;
import com.lyu.util.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LEE
 * @time 2022/12/29 16:52
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private UserService userService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private CommodityBidService commodityBidService;
    @Resource
    private CommodityBidMapper commodityBidMapper;
    @Resource
    private AlipayService alipayService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private UserAmountLogService userAmountLogService;
    @Resource
    private UserAmountService userAmountService;
    @Resource
    private CommoditySnapshotService commoditySnapshotService;
    @Resource
    private OrderRateMapper orderRateMapper;
    @Resource
    private ExpressService expressService;
    @Resource
    private RequestService requestService;

    @Resource
    private UserMessageService userMessageService;
    @Resource
    private OrderReturnMapper orderReturnMapper;
    @Resource
    private IDUtil idUtil;


    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public Order createOrder(Commodity commodity, UserAddress userAddress) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        User user = userService.getUserByUid(uidLogin);
        //检查用户状态
        if (user.getBanedTime() > 0) {
            throw new UserException(CodeAndMessage.BANED_USER.getCode(), CodeAndMessage.BANED_USER.getMessage());
        }
        //检查库存
        commodity = commodityMapper.selectById(commodity.getCid());
        if (commodity.getSold()) {
            //已卖出
            throw new CommodityException(CodeAndMessage.ITEM_SOLD.getCode(), CodeAndMessage.ITEM_SOLD.getMessage());
        }
        if (uidLogin == commodity.getUid()) {
            throw new OrderException(CodeAndMessage.CANT_BUY_OWN_PRODUCT.getCode(), CodeAndMessage.CANT_BUY_OWN_PRODUCT.getMessage());
        }
        //检查是否为求购下的商品
        if (commodity.getRequestId() != null) {
            //如果时求购下的商品，只能时求购发起者购买
            RequestDTO request = requestService.getRequestById(commodity.getRequestId());
            if (uidLogin != request.getUid()) {
                throw new UserException(CodeAndMessage.COMMODITY_ONLY_FOR_REQUEST.getCode(), CodeAndMessage.COMMODITY_ONLY_FOR_REQUEST.getMessage());
            }
        }
        Boolean exist = commodityBidService.orderOrBidExist(commodity.getCid());
        if (BooleanUtil.isTrue(exist)) {
            throw new OrderException(CodeAndMessage.BID_ALREADY_EXISTS.getCode(), CodeAndMessage.BID_ALREADY_EXISTS.getMessage());
        }
        //判断收获地址是否正确
        UserAddress userAddressInDb = userAddressService.getUserAddressByAid(userAddress.getAid());
        if (userAddressInDb == null || userAddressInDb.getUid() != uidLogin) {
            throw new OrderException(CodeAndMessage.INCORRECT_SHIPPING_ADDRESS.getCode(), CodeAndMessage.INCORRECT_SHIPPING_ADDRESS.getMessage());
        }
        commodity.setSold(true);
        commodityMapper.updateById(commodity);
        Order order = new Order();
        order.setCid(commodity.getCid());
        order.setUidSeller(commodity.getUid());
        order.setUidBuyer(user.getUid());
        order.setOrderTime(LocalDateTime.now());
        order.setTotalAmount(commodity.getPrice());
        order.setName(commodity.getName());
        order.setAid(userAddressInDb.getAid());
        order.setOid(idUtil.getNextOrderId(order));
        //将待支付的订单记录到Redis ，支付成功后写入数据库，超时后自动删除
        RedisUtil.set(Constant.REDIS_ORDER_UNPAID_KEY_PRE + order.getOid(), order);
        RedisUtil.expire(Constant.REDIS_ORDER_UNPAID_KEY_PRE + order.getOid(),
                AlipayConstant.ALIPAY_TIME_EXPIRE * 60 + 60);

        RedisUtil.set(Constant.REDIS_ORDER_MAP_COMMODITY_KEY_PRE + order.getOid(), order.getCid());
        RedisUtil.expire(Constant.REDIS_ORDER_MAP_COMMODITY_KEY_PRE + order.getOid(),
                AlipayConstant.ALIPAY_TIME_EXPIRE * 60 + 120);
        return order;
    }

    @Override
    public OrderDTO getOrderByOid(Long oid) throws OrderException {
        OrderDTO orderDTO = orderMapper.getOrderByOid(oid, StpUtil.getLoginIdAsLong());
        if (orderDTO == null || orderDTO.getUidSeller() == null || orderDTO.getUidBuyer() == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        checkAccess(orderDTO.getUidSeller(), orderDTO.getUidBuyer());
        OrderReturn orderReturn = orderReturnMapper.selectById(oid);
        orderDTO.setOrderReturn(orderReturn);
        //如果交易对方已评价，但用户未评价。那么就隐藏对方评价
        if (orderDTO.getUidSeller().equals(StpUtil.getLoginIdAsLong())) {
            if (orderDTO.getOrderRatingToBuyer() == null || orderDTO.getOrderRatingToBuyer().getScore() == null) {
                orderDTO.setOrderRatingToSeller(null);
            }
        } else {
            if (orderDTO.getOrderRatingToSeller() == null || orderDTO.getOrderRatingToSeller().getScore() == null) {
                orderDTO.setOrderRatingToBuyer(null);
            }
        }
        return orderDTO;
    }

    @Override
    public Order getOrderByOidSystem(Long oid) throws OrderException {
        if (oid == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        Order order = orderMapper.selectById(oid);
        if (order == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        return order;
    }

    @Override
    public List<OrderDTO> getOrdersByBuyerUid(Long uid) throws OrderException, UserException {
        if (uid == null) {
            throw new OrderException(CodeAndMessage.USER_NOT_EXIST.getCode(), CodeAndMessage.USER_NOT_EXIST.getMessage());
        }
        checkAccess(uid);
        return orderMapper.getOrdersByUser(uid);
    }

    @Override
    public List<OrderDTO> getOrdersByBuyerUidAsSeller(Long uid) throws OrderException, UserException {
        if (uid == null) {
            throw new OrderException(CodeAndMessage.USER_NOT_EXIST.getCode(), CodeAndMessage.USER_NOT_EXIST.getMessage());
        }
        checkAccess(uid);
        return orderMapper.getOrdersByUserAsSeller(uid);
    }

    @Override
    public List<Order> getOrdersUncompletedByBuyerUid(Long uid) throws OrderException, UserException {

        if (uid == null) {
            throw new OrderException(CodeAndMessage.USER_NOT_EXIST.getCode(), CodeAndMessage.USER_NOT_EXIST.getMessage());
        }
        checkAccess(uid);
        User user = new User();
        user.setUid(uid);
        return orderMapper.selectList(new QueryWrapper<Order>()
                .eq("uid", user.getUid())
                .isNull("complete_time"));
    }

    @Override
    public OrderDTO getOrderByCid(Long cid) {
        OrderDTO order = orderMapper.getOrderByCid(cid);
        if (order == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        if (!order.getUidSeller().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        return order;
    }

    @Override
    public Integer updateOrder(Order order) {
        return orderMapper.updateById(order);

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void cancelOrder(Long oid) {
        Order order = orderMapper.selectById(oid);
        if (order == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INCONCLUSIVE_RESULT.getMessage());
        }
        if (!order.getUidSeller().equals(StpUtil.getLoginIdAsLong())) {
            throw new OrderException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        //检查订单状态
        if (order.getCompleteTime() != null && LocalDateTime.now().isAfter(order.getCompleteTime())) {
            //订单已完成不可取消
            throw new OrderException(CodeAndMessage.NON_CANCELLABLE_ORDER.getCode(), CodeAndMessage.NON_CANCELLABLE_ORDER.getMessage());
        }
        Long cid = order.getCid();
        Commodity commodity = commodityMapper.selectById(cid);
        commodity.setSold(false);
        commodityMapper.updateById(commodity);
        order.setStatus(AlipayConstant.ORDER_STATUS_CLOSED);
        order.setCancelTime(LocalDateTime.now());
        orderMapper.updateById(order);
        //如果已经支付，则发起退款
        if (order.getTradeId() != null) {
            alipayService.refund(order.getTradeId(), order.getTotalAmount(), String.valueOf(order.getOid()), AlipayConstant.SELLER_CLOSE_ORDER, AlipayConstant.ALIPAY_PAY_TYPE_ORDER);
        }
        //通知买家
        userMessageService.sendNotification(Message.ORDER_HAS_BEEN_CLOSED, "buyer/order/" + order.getOid(), order.getUidBuyer());
    }

    @Override
    public void completePayOrder(Order order) {
        //生成商品快照
        Long ssid = commoditySnapshotService.createCommoditySnapshot(order.getCid());
        //设置为已付款
        order.setStatus(0);
        order.setSsid(ssid);
        //获取本商品下的所有的出价
        List<CommodityBid> commodityBidRefundList = commodityBidMapper.selectList(new QueryWrapper<CommodityBid>().eq("cid", order.getCid()));
        //取消其他用户的所有出价并退款
        commodityBidService.cancelBidByCid(order.getCid());
        //发起其他用户的退款
        commodityBidRefundList.forEach((commodityBidRefund) -> alipayService.refund(commodityBidRefund.getTradeId(), commodityBidRefund.getPrice(),
                String.valueOf(commodityBidRefund.getBid()), AlipayConstant.REFUND_DUE_TO_DIRECT_PURCHASE, AlipayConstant.ALIPAY_PAY_TYPE_BID));
        //记录日志
        UserAmountLog userAmountLog = new UserAmountLog();
        userAmountLog.setAmount(order.getTotalAmount());
        userAmountLog.setPlus(true);
        userAmountLog.setUid(order.getUidSeller());
        userAmountLog.setEffective(false);
        userAmountLog.setSourceId(order.getOid());
        userAmountLog.setTime(order.getPayTime());
        userAmountLogService.logUserAmount(userAmountLog);
        //插入记录到数据库
        orderMapper.insert(order);
    }

    @Override
    public void completeAndRateOrder(Long oid, Integer rating, String comment) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        Order order = orderMapper.selectById(oid);
        if (order == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        if (order.getShipTime() == null) {
            throw new OrderException(CodeAndMessage.ORDER_IS_NOT_SHIP.getCode(), CodeAndMessage.ORDER_IS_NOT_SHIP.getMessage());
        }

        OrderRating orderRating = new OrderRating();
        orderRating.setScore(rating);
        if (rating >= 4) {
            orderRating.setLevel(2);
        } else if (rating > 2) {
            orderRating.setLevel(1);
        } else {
            orderRating.setLevel(0);
        }
        orderRating.setOid(oid);
        orderRating.setComment(comment);
        if (order.getUidBuyer().equals(uidLogin)) {
            order.setStatus(AlipayConstant.ORDER_STATUS_COMPLETED);
            order.setCompleteTime(LocalDateTime.now());
            updateOrder(order);
            orderRating.setTarget(order.getUidSeller());
            orderRating.setSeller(true);
            orderRateMapper.insert(orderRating);
            //发消息给卖家
            userMessageService.sendNotification(Message.BUYER_HAS_CONFIRMED_RECEIPT_OF_GOODS, "/seller/order/" + order.getOid(), order.getUidSeller());
            //解除冻结金额
            userAmountService.thawAmount(order.getUidSeller(), order.getTotalAmount(), order.getOid());
        } else if ((order.getUidSeller().equals(uidLogin))) {
            if (!order.getStatus().equals(AlipayConstant.ORDER_STATUS_COMPLETED)) {
                throw new OrderException(CodeAndMessage.ORDER_IS_NOT_COMPLETED.getCode(), CodeAndMessage.ORDER_IS_NOT_COMPLETED.getMessage());
            }
            orderRating.setTarget(order.getUidBuyer());
            orderRating.setSeller(false);
            orderRateMapper.insert(orderRating);
            //发消息给买家 卖家已完成对你的评分
            userMessageService.sendNotification(Message.SELLER_HAS_FINISHED_RATING_YOU, "/seller/order/" + order.getOid(), order.getUidBuyer());

        }
    }


    @Override
    public void expressOrder(Long oid, String expressId, String shipId) {
        Order order = orderMapper.selectById(oid);
        if (order == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        if (!order.getUidSeller().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
        }
        Express expressCompany = expressService.getExpressCompany(expressId);
        if (expressCompany == null) {
            throw new OrderException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
        }
        order.setShipId(shipId);
        order.setExpressId(expressId);
        order.setShipTime(LocalDateTime.now());
        updateOrder(order);
        userMessageService.sendNotification(Message.YOUR_ORDER_HAS_BEEN_SHIPPED, "/buyer/order/" + order.getOid(), order.getUidBuyer());

    }

    @Override
    public Boolean exist(Long oid) {
        return orderMapper.exist(oid);
    }


    /**
     * 检查权限
     */
    private void checkAccess(Long... ids) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        for (Long uid : ids) {
            if (uid == uidLogin) {
                return;
            }
        }
        //遍历完所有有权的用户没有匹配到当前用户就抛出异常
        throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
    }
}
