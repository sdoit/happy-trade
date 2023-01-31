package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author LEE
 * @TableName t_commodity
 */
@TableName(value ="t_commodity")
@Data
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
    private String name;

    /**
     * 封面图片
     */
    private Long coverId;

    /**
     * 商品品质 0-10
     */
    private Double quality;

    /**
     * 商品类型
     */
    private Integer type;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商品说明/商品描述
     */
    private String description;

    /**
     * 已卖出
     */
    private Boolean sold;

    /**
     * 已上架
     */
    private Integer launched;


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
            && (this.getCoverId() == null ? other.getCoverId() == null : this.getCoverId().equals(other.getCoverId()))
            && (this.getQuality() == null ? other.getQuality() == null : this.getQuality().equals(other.getQuality()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
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
        result = prime * result + ((getCoverId() == null) ? 0 : getCoverId().hashCode());
        result = prime * result + ((getQuality() == null) ? 0 : getQuality().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
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
        sb.append(", coverId=").append(coverId);
        sb.append(", quality=").append(quality);
        sb.append(", type=").append(type);
        sb.append(", price=").append(price);
        sb.append(", description=").append(description);
        sb.append(", sold=").append(sold);
        sb.append(", launched=").append(launched);
        sb.append(", viewCount=").append(viewCount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}