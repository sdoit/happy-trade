package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.*;
import com.lyu.entity.dto.OrderDTO;
import com.lyu.exception.CommodityException;
import com.lyu.exception.OrderException;
import com.lyu.exception.UserException;
import com.lyu.mapper.CommodityBidMapper;
import com.lyu.mapper.CommodityMapper;
import com.lyu.mapper.OrderMapper;
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
    private IDUtil idUtil;
    @Resource
    private RedisUtil redisUtil;

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
        redisUtil.set(Constant.REDIS_ORDER_UNPAID_KEY_PRE + order.getOid(), order);
        redisUtil.expire(Constant.REDIS_ORDER_UNPAID_KEY_PRE + order.getOid(),
                Constant.ALIPAY_TIME_EXPIRE * 60 + 60);

        redisUtil.set(Constant.REDIS_ORDER_MAP_COMMODITY_KEY_PRE + order.getOid(), order.getCid());
        redisUtil.expire(Constant.REDIS_ORDER_MAP_COMMODITY_KEY_PRE + order.getOid(),
                Constant.ALIPAY_TIME_EXPIRE * 60 + 120);
        return order;
    }

    @Override
    public OrderDTO getOrderByOid(Long oid) throws OrderException {
        OrderDTO orderDTO = orderMapper.getOrderByOid(oid, StpUtil.getLoginIdAsLong());
        checkAccess(orderDTO.getUidSeller(), orderDTO.getUidBuyer());
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

    @Override
    public Integer cancelOrder(Order order) {
        //检查订单状态
        if (LocalDateTime.now().isAfter(order.getCompleteTime())) {
            //订单已完成不可取消
            throw new OrderException(CodeAndMessage.NON_CANCELLABLE_ORDER.getCode(), CodeAndMessage.NON_CANCELLABLE_ORDER.getMessage());
        }
        Long cid = order.getCid();
        Commodity commodity = commodityMapper.selectById(cid);
        commodity.setSold(false);
        commodityMapper.updateById(commodity);
        order.setStatus(Constant.ORDER_STATUS_CLOSED);
        return orderMapper.updateById(order);
    }

    @Override
    public Integer completePayOrder(Order order) {
        //设置为已付款
        order.setStatus(0);
        //获取本商品下的所有的出价
        List<CommodityBid> commodityBidRefundList = commodityBidMapper.selectList(new QueryWrapper<CommodityBid>().eq("cid", order.getCid()));
        //取消其他用户的所有出价并退款
        commodityBidService.cancelBidByCid(order.getCid());
        //发起其他用户的退款
        commodityBidRefundList.forEach((commodityBidRefund) -> {
            alipayService.refund(commodityBidRefund.getTradeId(), commodityBidRefund.getPrice(),
                    String.valueOf(commodityBidRefund.getBid()), Constant.REFUND_DUE_TO_DIRECT_PURCHASE, Constant.ALIPAY_PAY_TYPE_BID);
        });
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
        return orderMapper.insert(order);
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
