package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @time 2023/2/26 10:07
 */
@TableName("t_commodity_snapshot")
@Data
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
}
