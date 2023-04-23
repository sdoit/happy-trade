package com.lyu.entity;

/**
 * @author LEE
 * @time 2023/2/24 14:12
 */
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

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CommodityTypeViewCount{" +
                "tid=" + tid +
                ", count=" + count +
                ", rate=" + rate +
                '}';
    }
}
