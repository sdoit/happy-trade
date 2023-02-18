package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @TableName t_commodity
 */
@TableName(value = "t_commodity")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Commodity implements Serializable {
    /**
     * 商品ID
     */
    @TableId
    private Long cid;

    /**
     * 所属人
     */
    private Long uid;

    /**
     * 商品名
     */
    @NotNull
    private String name;

    /**
     * 封面图片
     */
    @NotNull
    private String cover;

    /**
     * 商品品质 0-10
     */
    @NotNull
    private Double quality;

    /**
     * 商品类型
     */
    @NotNull
    private Integer typeId;

    /**
     * 价格
     */
    @NotNull
    private BigDecimal price;
    /**
     * 运费
     */

    private BigDecimal fare;


    /**
     * 商品说明/商品描述
     */
    @NotNull
    private String description;

    /**
     * 运费到付
     */
    private Boolean freightCollect;
    /**
     * 包邮
     */
    private Boolean freeShipping;
    /**
     * 已卖出
     */
    private Boolean sold;

    /**
     * 已上架
     */
    private Boolean launched;
    /**
     * 发布时间
     */
    private LocalDateTime time;

    /**
     * 浏览数
     */
    private Integer viewCount;
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
        Commodity other = (Commodity) that;
        return (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getCover() == null ? other.getCover() == null : this.getCover().equals(other.getCover()))
                && (this.getQuality() == null ? other.getQuality() == null : this.getQuality().equals(other.getQuality()))
                && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getFreeShipping() == null ? other.getFreeShipping() == null : this.getFreeShipping().equals(other.getFreeShipping()))
                && (this.getFare() == null ? other.getFare() == null : this.getFare().equals(other.getFare()))
                && (this.getFreightCollect() == null ? other.getFreightCollect() == null : this.getFreightCollect().equals(other.getFreightCollect()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
                && (this.getSold() == null ? other.getSold() == null : this.getSold().equals(other.getSold()))
                && (this.getLaunched() == null ? other.getLaunched() == null : this.getLaunched().equals(other.getLaunched()))
                && (this.getViewCount() == null ? other.getViewCount() == null : this.getViewCount().equals(other.getViewCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCover() == null) ? 0 : getCover().hashCode());
        result = prime * result + ((getQuality() == null) ? 0 : getQuality().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getFare() == null) ? 0 : getFare().hashCode());
        result = prime * result + ((getFreeShipping() == null) ? 0 : getFreeShipping().hashCode());
        result = prime * result + ((getFreightCollect() == null) ? 0 : getFreightCollect().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getSold() == null) ? 0 : getSold().hashCode());
        result = prime * result + ((getLaunched() == null) ? 0 : getLaunched().hashCode());
        result = prime * result + ((getViewCount() == null) ? 0 : getViewCount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cid=").append(cid);
        sb.append(", uid=").append(uid);
        sb.append(", name=").append(name);
        sb.append(", coverId=").append(cover);
        sb.append(", quality=").append(quality);
        sb.append(", typeId=").append(typeId);
        sb.append(", price=").append(price);
        sb.append(", freeShipping=").append(freeShipping);
        sb.append(", fare=").append(fare);
        sb.append(", freightCollect=").append(freightCollect);
        sb.append(", description=").append(description);
        sb.append(", sold=").append(sold);
        sb.append(", launched=").append(launched);
        sb.append(", viewCount=").append(viewCount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}