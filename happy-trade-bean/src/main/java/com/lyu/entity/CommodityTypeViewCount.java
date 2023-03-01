package com.lyu.entity;

import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/24 14:12
 */
@Data
public class CommodityTypeViewCount {
    /**
     * 属性id，二级属性id
     */
    private Integer tid;
    /**
     * 浏览次数
     */
    private Integer count;


    /**
     * 本属性占所有浏览数的比例
     */
    private Double rate;

}
