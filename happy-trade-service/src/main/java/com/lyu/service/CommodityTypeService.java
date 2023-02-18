package com.lyu.service;

import com.lyu.entity.CommodityType;

import java.util.List;

/**
 * @author LEE
 * @time 2023/2/15 13:58
 */
public interface CommodityTypeService {
    /**
     * 获取type下的子节点
     *
     * @param tid
     * @return
     */
    List<CommodityType> getSubTypes(Integer tid);

    /**
     * 获取根type节点
     *
     * @return
     */
    List<CommodityType> getRootTypes();
}
