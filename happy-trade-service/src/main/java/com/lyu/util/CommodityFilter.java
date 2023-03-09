package com.lyu.util;

import com.lyu.entity.Commodity;

import java.util.List;

/**
 * @author LEE
 */
public class CommodityFilter {


    /**
     * 筛选范围 下限
     * 品质 价格等
     */
    private Commodity commodityMin;
    /**
     * 上限
     */
    private Commodity commodityMax;

    /**
     * 关键词
     */
    private List<String> keywords;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Commodity getCommodityMin() {
        return commodityMin;
    }

    public void setCommodityMin(Commodity commodityMin) {
        this.commodityMin = commodityMin;
    }

    public Commodity getCommodityMax() {
        return commodityMax;
    }

    public void setCommodityMax(Commodity commodityMax) {
        this.commodityMax = commodityMax;
    }

    /**
     * 按照要求过滤商品列表
     *
     * @param commodities
     * @return 过滤结果
     */
    public List<Commodity> filter(List<Commodity> commodities) {


        return null;
    }
}
