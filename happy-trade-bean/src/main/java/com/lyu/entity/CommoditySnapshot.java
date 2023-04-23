package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @time 2023/2/26 10:07
 */
@TableName("t_commodity_snapshot")
public class CommoditySnapshot implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long ssid;
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

    public Long getSsid() {
        return ssid;
    }

    public void setSsid(Long ssid) {
        this.ssid = ssid;
    }

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
}
