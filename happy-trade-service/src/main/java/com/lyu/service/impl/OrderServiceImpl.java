package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.Commodity;
import com.lyu.entity.Order;
import com.lyu.entity.User;
import com.lyu.exception.CommodityException;
import com.lyu.exception.OrderException;
import com.lyu.exception.UserException;
import com.lyu.mapper.CommodityMapper;
import com.lyu.mapper.OrderMapper;
import com.lyu.service.OrderService;
import com.lyu.util.IDUtil;
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
    private OrderMapper orderMapper;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private IDUtil idUtil;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public Order createOrder(User user, Commodity commodity) {
        //检查库存
        commodity = commodityMapper.selectById(commodity.getCid());
        if (commodity.getSold()) {
            //已卖出
            throw new CommodityException(CodeAndMessage.ITEM_SOLD.getCode(), CodeAndMessage.ITEM_SOLD.getMessage());
        }

        //检查用户状态
        if (user.getBanedTime() > 0) {
            throw new UserException(CodeAndMessage.BANED_USER.getCode(), CodeAndMessage.BANED_USER.getMessage());
        }
        commodity.setSold(true);
        commodityMapper.updateById(commodity);
        Order order = new Order();
        order.setCid(commodity.getCid());
        order.setUid(commodity.getUid());
        order.setUid2(user.getUid());
        order.setOrderTime(LocalDateTime.now());
        order.setTotalAmount(commodity.getPrice().doubleValue());
        order.setName(commodity.getName());
        order.setOid(idUtil.getNextOrderId(order));
        orderMapper.insert(order);


        return order;
    }

    @Override
    public Order getOrderByOid(Long oid) throws OrderException {
        if (oid == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        Order order = orderMapper.selectById(oid);
        if (order == null) {
            throw new OrderException(CodeAndMessage.INVALID_ORDER_ID.getCode(), CodeAndMessage.INVALID_ORDER_ID.getMessage());
        }
        checkAccess(order.getUid(), order.getUid2());
        return order;
    }

    @Override
    public List<Order> getOrdersByBuyerUid(Long uid) throws OrderException, UserException {
        if (uid == null) {
            throw new OrderException(CodeAndMessage.USER_NOT_EXIST.getCode(), CodeAndMessage.USER_NOT_EXIST.getMessage());
        }
        checkAccess(uid);
        User user = new User();
        user.setUid(uid);


        return orderMapper.selectList(new QueryWrapper<Order>()
                .eq("uid", user.getUid()));
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
    public Order payOrder(Order order) {
        return null;
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
