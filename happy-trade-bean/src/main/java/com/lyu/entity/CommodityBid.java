package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * @TableName t_commodity_bid
 */
@TableName(value ="t_commodity_bid")
@Data
public class CommodityBid implements Serializable {
    /**
     * 出价id
     */
    @TableId
    private Long bid;

    /**
     * 商品id
     */
    private Long cid;

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
    private BigDecimal price;

    /**
     * 买家留言
     */
    private String messageBuyer;

    /**
     * 卖家回复
     */
    private String replySeller;

    /**
     * 卖家是否同意
     */
    private Integer agree;

    /**
     * 出价时间
     */
    private LocalDateTime time;

    /**
     * 卖家回复时间
     */
    private LocalDateTime timeReply;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getTimeReply() == null ? other.getTimeReply() == null : this.getTimeReply().equals(other.getTimeReply()));
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
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getTimeReply() == null) ? 0 : getTimeReply().hashCode());
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
        sb.append(", time=").append(time);
        sb.append(", timeReply=").append(timeReply);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}