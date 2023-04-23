package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @TableName t_commodity_bid
 */
@TableName(value = "t_commodity_bid")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityBid implements Serializable {
    /**
     * 报价id
     */
    @TableId
    private Long bid;

    /**
     * 商品id
     */
    @NotNull
    private Long cid;


    /**
     * 商品名
     */
    private String name;
    /**
     * 出价人
     */
    private Long uidBuyer;

    /**
     * 卖家id
     */
    private Long uidSeller;

    /**
     * 价格
     */
    @NotNull
    private BigDecimal price;

    /**
     * 买家留言
     */
    @NotNull
    private String messageBuyer;

    /**
     * 卖家回复
     */
    private String replySeller;

    /**
     * 卖家是否同意
     */
    private Boolean agree;

    /**
     * 出价时间
     */

    private LocalDateTime timeCreated;

    /**
     * 卖家回复时间
     */
    private LocalDateTime timeReply;

    /**
     * 支付凭证号
     */
    private String tradeId;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 买家支付宝id
     */
    private String buyerAlipayId;
    /**
     * 收货地址id
     */
    private Long aid;

    /**
     * 已创建的正式订单号
     */
    private Long formalOrder;


    /**
     * 退款时间
     */
    private LocalDateTime refundTime;
    /**
     * 是否取消
     */
    private Boolean cancel;
    @TableField(exist = false)
    private User buyer;
    @TableField(exist = false)
    private User seller;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUidBuyer() {
        return uidBuyer;
    }

    public void setUidBuyer(Long uidBuyer) {
        this.uidBuyer = uidBuyer;
    }

    public Long getUidSeller() {
        return uidSeller;
    }

    public void setUidSeller(Long uidSeller) {
        this.uidSeller = uidSeller;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getMessageBuyer() {
        return messageBuyer;
    }

    public void setMessageBuyer(String messageBuyer) {
        this.messageBuyer = messageBuyer;
    }

    public String getReplySeller() {
        return replySeller;
    }

    public void setReplySeller(String replySeller) {
        this.replySeller = replySeller;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeReply() {
        return timeReply;
    }

    public void setTimeReply(LocalDateTime timeReply) {
        this.timeReply = timeReply;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public String getBuyerAlipayId() {
        return buyerAlipayId;
    }

    public void setBuyerAlipayId(String buyerAlipayId) {
        this.buyerAlipayId = buyerAlipayId;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getFormalOrder() {
        return formalOrder;
    }

    public void setFormalOrder(Long formalOrder) {
        this.formalOrder = formalOrder;
    }

    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
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
        CommodityBid other = (CommodityBid) that;
        return (this.getBid() == null ? other.getBid() == null : this.getBid().equals(other.getBid()))
                && (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
                && (this.getUidBuyer() == null ? other.getUidBuyer() == null : this.getUidBuyer().equals(other.getUidBuyer()))
                && (this.getUidSeller() == null ? other.getUidSeller() == null : this.getUidSeller().equals(other.getUidSeller()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getMessageBuyer() == null ? other.getMessageBuyer() == null : this.getMessageBuyer().equals(other.getMessageBuyer()))
                && (this.getReplySeller() == null ? other.getReplySeller() == null : this.getReplySeller().equals(other.getReplySeller()))
                && (this.getAgree() == null ? other.getAgree() == null : this.getAgree().equals(other.getAgree()))
                && (this.getTimeCreated() == null ? other.getTimeCreated() == null : this.getTimeCreated().equals(other.getTimeCreated()))
                && (this.getTimeReply() == null ? other.getTimeReply() == null : this.getTimeReply().equals(other.getTimeReply()))
                && (this.getTradeId() == null ? other.getTradeId() == null : this.getTradeId().equals(other.getTradeId()))
                && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
                && (this.getBuyerAlipayId() == null ? other.getBuyerAlipayId() == null : this.getBuyerAlipayId().equals(other.getBuyerAlipayId()))
                && (this.getRefundTime() == null ? other.getRefundTime() == null : this.getRefundTime().equals(other.getRefundTime()))
                && (this.getCancel() == null ? other.getCancel() == null : this.getCancel().equals(other.getCancel()))
                && (this.getFormalOrder() == null ? other.getFormalOrder() == null : this.getFormalOrder().equals(other.getFormalOrder()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBid() == null) ? 0 : getBid().hashCode());
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getUidBuyer() == null) ? 0 : getUidBuyer().hashCode());
        result = prime * result + ((getUidSeller() == null) ? 0 : getUidSeller().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getMessageBuyer() == null) ? 0 : getMessageBuyer().hashCode());
        result = prime * result + ((getReplySeller() == null) ? 0 : getReplySeller().hashCode());
        result = prime * result + ((getAgree() == null) ? 0 : getAgree().hashCode());
        result = prime * result + ((getTimeCreated() == null) ? 0 : getTimeCreated().hashCode());
        result = prime * result + ((getTimeReply() == null) ? 0 : getTimeReply().hashCode());
        result = prime * result + ((getTradeId() == null) ? 0 : getTradeId().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getBuyerAlipayId() == null) ? 0 : getBuyerAlipayId().hashCode());
        result = prime * result + ((getFormalOrder() == null) ? 0 : getFormalOrder().hashCode());
        result = prime * result + ((getRefundTime() == null) ? 0 : getRefundTime().hashCode());
        result = prime * result + ((getCancel() == null) ? 0 : getCancel().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bid=").append(bid);
        sb.append(", cid=").append(cid);
        sb.append(", uidBuyer=").append(uidBuyer);
        sb.append(", uidSeller=").append(uidSeller);
        sb.append(", price=").append(price);
        sb.append(", messageBuyer=").append(messageBuyer);
        sb.append(", replySeller=").append(replySeller);
        sb.append(", agree=").append(agree);
        sb.append(", timeCreated=").append(timeCreated);
        sb.append(", timeReply=").append(timeReply);
        sb.append(", tradeId=").append(tradeId);
        sb.append(", payTime=").append(payTime);
        sb.append(", buyerAlipayId=").append(buyerAlipayId);
        sb.append(", formalOrder=").append(formalOrder);
        sb.append(", refundTime=").append(refundTime);
        sb.append(", cancel=").append(cancel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}