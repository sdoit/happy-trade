package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @TableName t_order
 */
@TableName(value = "t_order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order implements Serializable {
    /**
     * 订单id
     */
    @TableId
    private Long oid;

    /**
     * 商品id
     */
    private Long cid;

    /**
     * 卖家id
     */
    private Long uidSeller;

    /**
     * 买家id
     */
    private Long uidBuyer;

    /**
     * 产品名
     */
    private String name;

    /**
     * 支付凭证号
     */
    private String tradeId;

    /**
     * 下单时间
     */

    private LocalDateTime orderTime;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    private LocalDateTime shipTime;

    /**
     * 交易金额
     */
    private BigDecimal totalAmount;

    /**
     * 买家支付宝id
     */
    private String buyerAlipayId;
    private LocalDateTime refundTime;
    /**
     * 订单取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 订单完成时间
     */
    private LocalDateTime completeTime;

    private String   expressId;
    private String shipId;

    /**
     * 收货地址id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long aid;

    /**
     * -2:已退款；-1已关闭（未支付）；0:可用；1:已完成
     */
    private Integer status;

    /**
     * 快照id
     */
    private Long ssid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUidSeller() {
        return uidSeller;
    }

    public void setUidSeller(Long uidSeller) {
        this.uidSeller = uidSeller;
    }

    public Long getUidBuyer() {
        return uidBuyer;
    }

    public void setUidBuyer(Long uidBuyer) {
        this.uidBuyer = uidBuyer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public LocalDateTime getShipTime() {
        return shipTime;
    }

    public void setShipTime(LocalDateTime shipTime) {
        this.shipTime = shipTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBuyerAlipayId() {
        return buyerAlipayId;
    }

    public void setBuyerAlipayId(String buyerAlipayId) {
        this.buyerAlipayId = buyerAlipayId;
    }

    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
    }

    public LocalDateTime getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(LocalDateTime cancelTime) {
        this.cancelTime = cancelTime;
    }

    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(LocalDateTime completeTime) {
        this.completeTime = completeTime;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSsid() {
        return ssid;
    }

    public void setSsid(Long ssid) {
        this.ssid = ssid;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Order other = (Order) that;
        return (this.getOid() == null ? other.getOid() == null : this.getOid().equals(other.getOid()))
                && (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
                && (this.getUidSeller() == null ? other.getUidSeller() == null : this.getUidSeller().equals(other.getUidSeller()))
                && (this.getUidBuyer() == null ? other.getUidBuyer() == null : this.getUidBuyer().equals(other.getUidBuyer()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getTradeId() == null ? other.getTradeId() == null : this.getTradeId().equals(other.getTradeId()))
                && (this.getOrderTime() == null ? other.getOrderTime() == null : this.getOrderTime().equals(other.getOrderTime()))
                && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
                && (this.getShipTime() == null ? other.getShipTime() == null : this.getShipTime().equals(other.getShipTime()))
                && (this.getTotalAmount() == null ? other.getTotalAmount() == null : this.getTotalAmount().equals(other.getTotalAmount()))
                && (this.getBuyerAlipayId() == null ? other.getBuyerAlipayId() == null : this.getBuyerAlipayId().equals(other.getBuyerAlipayId()))
                && (this.getCancelTime() == null ? other.getCancelTime() == null : this.getCancelTime().equals(other.getCancelTime()))
                && (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()))
                && (this.getAid() == null ? other.getAid() == null : this.getAid().equals(other.getAid()))
                && (this.getRefundTime() == null ? other.getRefundTime() == null : this.getRefundTime().equals(other.getRefundTime()))
                && (this.getShipId() == null ? other.getShipId() == null : this.getShipId().equals(other.getShipId()))
                && (this.getExpressId() == null ? other.getExpressId() == null : this.getExpressId().equals(other.getExpressId()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getSsid() == null ? other.getSsid() == null : this.getSsid().equals(other.getSsid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOid() == null) ? 0 : getOid().hashCode());
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getUidSeller() == null) ? 0 : getUidSeller().hashCode());
        result = prime * result + ((getUidBuyer() == null) ? 0 : getUidBuyer().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getTradeId() == null) ? 0 : getTradeId().hashCode());
        result = prime * result + ((getOrderTime() == null) ? 0 : getOrderTime().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getShipTime() == null) ? 0 : getShipTime().hashCode());
        result = prime * result + ((getTotalAmount() == null) ? 0 : getTotalAmount().hashCode());
        result = prime * result + ((getBuyerAlipayId() == null) ? 0 : getBuyerAlipayId().hashCode());
        result = prime * result + ((getCancelTime() == null) ? 0 : getCancelTime().hashCode());
        result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
        result = prime * result + ((getAid() == null) ? 0 : getAid().hashCode());
        result = prime * result + ((getRefundTime() == null) ? 0 : getRefundTime().hashCode());
        result = prime * result + ((getShipId() == null) ? 0 : getShipId().hashCode());
        result = prime * result + ((getExpressId() == null) ? 0 : getExpressId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSsid() == null) ? 0 : getSsid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oid=").append(oid);
        sb.append(", cid=").append(cid);
        sb.append(", uidSeller=").append(uidSeller);
        sb.append(", uidBuyer=").append(uidBuyer);
        sb.append(", name=").append(name);
        sb.append(", tradeId=").append(tradeId);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", shipTime=").append(shipTime);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", buyerAlipayId=").append(buyerAlipayId);
        sb.append(", cancelTime=").append(cancelTime);
        sb.append(", refundTime=").append(refundTime);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", aid=").append(aid);
        sb.append(", expressId=").append(expressId);
        sb.append(", shipId=").append(shipId);
        sb.append(", status=").append(status);
        sb.append(", ssid=").append(ssid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}