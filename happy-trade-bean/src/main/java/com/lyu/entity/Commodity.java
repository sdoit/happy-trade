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
 * @TableName t_commodity
 */
@TableName(value = "t_commodity")
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
    private Long requestId;
    /**
     * 是否被强制删除
     */
    private Boolean forceDelete;
    /**
     * 是否被强制下架
     */
    private Boolean forceDown;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Double getQuality() {
        return quality;
    }

    public void setQuality(Double quality) {
        this.quality = quality;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFreightCollect() {
        return freightCollect;
    }

    public void setFreightCollect(Boolean freightCollect) {
        this.freightCollect = freightCollect;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Boolean getLaunched() {
        return launched;
    }

    public void setLaunched(Boolean launched) {
        this.launched = launched;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Boolean getForceDelete() {
        return forceDelete;
    }

    public void setForceDelete(Boolean forceDelete) {
        this.forceDelete = forceDelete;
    }

    public Boolean getForceDown() {
        return forceDown;
    }

    public void setForceDown(Boolean forceDown) {
        this.forceDown = forceDown;
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
                && (this.getRequestId() == null ? other.getRequestId() == null : this.getRequestId().equals(other.getRequestId()))
                && (this.getViewCount() == null ? other.getViewCount() == null : this.getViewCount().equals(other.getViewCount()))
                && (this.getForceDelete() == null ? other.getForceDelete() == null : this.getForceDelete().equals(other.getForceDelete()))
                && (this.getForceDown() == null ? other.getForceDown() == null : this.getForceDown().equals(other.getForceDown()));
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
        result = prime * result + ((getRequestId() == null) ? 0 : getRequestId().hashCode());
        result = prime * result + ((getViewCount() == null) ? 0 : getViewCount().hashCode());
        result = prime * result + ((getForceDelete() == null) ? 0 : getForceDelete().hashCode());
        result = prime * result + ((getForceDown() == null) ? 0 : getForceDown().hashCode());
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
        sb.append(", requestId=").append(requestId);
        sb.append(", forceDelete=").append(forceDelete);
        sb.append(", forceDown=").append(forceDown);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}