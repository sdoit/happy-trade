package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2022/12/4 16:31
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 保存订单到数据库
     *
     * @param order
     * @return
     */
//    Integer saveOrder(Order order);

    /**
     * 修改订单信息
     *
     * @param order
     * @return
     */
//    Integer updateOrder(Order order);

    /**
     * 根据订单号获取订单信息
     * @param oid
     * @return
     */
//    Order getOrderById(Long oid);

    /**
     * 获取指定用户的所有订单（作为卖家）
     * @param user
     * @return
     */
//    List<Order> getOrdersByUser(User user);

    /**
     * 获取购买的订单（作为买家）
     * @param user
     * @return
     */
//    List<Order> getBuyOrdersByUser(User user);

    /**
     * 获取指定用户已完成的订单
     * @param user
     * @return
     */
//    List<Order> getCompletedOrdersByUser(User user);
    /**
     * 获取指定用户未完成的订单
     * @param user
     * @return
     */
//    List<Order> getUncompletedOrdersByUser(User user);
}
