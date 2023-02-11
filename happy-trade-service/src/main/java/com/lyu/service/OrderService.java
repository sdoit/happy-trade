package com.lyu.service;

import com.lyu.entity.Commodity;
import com.lyu.entity.Order;
import com.lyu.entity.UserAddress;
import com.lyu.entity.dto.OrderDTO;
import com.lyu.exception.CommodityException;
import com.lyu.exception.OrderException;
import com.lyu.exception.UserException;

import java.util.List;

/**
 * @author LEE
 * @time 2022/12/28 11:58
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param commodity
     * @param userAddress
     * @return
     * @throws UserException
     * @throws CommodityException
     */
    Order createOrder(Commodity commodity, UserAddress userAddress) throws UserException, CommodityException;


    /**
     * 根据订单号获取订单
     *
     * @param oid
     * @return
     * @throws OrderException
     */
    OrderDTO getOrderByOid(Long oid) throws OrderException;

    /**
     * 系统调用 不要求又登录用户
     * @param oid
     * @return
     * @throws OrderException
     */
    Order getOrderByOidSystem(Long oid) throws OrderException;

    /**
     * 获取指定买家的所有订单
     * @param uid
     * @return
     * @throws OrderException
     * @throws UserException
     */
    List<OrderDTO> getOrdersByBuyerUid(Long uid) throws OrderException, UserException;
    /**
     * 获取指定买家的未完成的订单
     * @param uid
     * @return
     * @throws OrderException
     * @throws UserException
     */
    List<Order> getOrdersUncompletedByBuyerUid(Long uid) throws OrderException, UserException;

    /**
     * 更新订单信息
     * @param order
     * @return
     */
    Integer updateOrder(Order order);
    /**
     * 取消订单
     *
     * @param order
     * @return
     */
    Integer cancelOrder(Order order);

    /**
     * 支付订单
     *
     * @param order
     * @return
     */
    Integer completePayOrder(Order order);

    /**
     * 检查订单是否存在
     * @param oid
     * @return
     */
    Boolean exist(Long oid);


}