package com.lyu.service;

/**
 * @author LEE
 * @time 2023/4/22 19:56
 */

public interface OrderReturnService {
    /**
     * 发起退货申请
     *
     * @param oid    订单号
     * @param reason 退货原因
     */
    void createOrderReturn(Long oid, String reason);


    /**
     * 同意退货
     *
     * @param oid 要同意退货的订单号
     * @param aid 商品回寄地址
     */
    void agreeReturnOrder(Long oid,Long aid);

    /**
     * 拒绝退货
     *
     * @param oid    要拒退货申请的订单号
     * @param rejectReason 拒绝原因
     */
    void rejectReturnOrder(Long oid, String rejectReason);

    /**
     * 买家回寄商品
     * @param oid
     * @param shipId 运单号
     */
    void shipReturnOrder(Long oid, String shipId);
    /**
     * 完成退货，发起退款
     *
     * @param oid    买家已发货的退货订单号
     */
    void completeReturnOrder(Long oid);

}
