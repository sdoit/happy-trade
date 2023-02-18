package com.lyu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.entity.dto.CommodityDTO;

import java.util.List;

/**
 * @author LEE
 * @time 2022/12/28 10:10
 */
public interface CommodityService {
    /**
     * 用户添加商品
     *
     * @param commodity
     * @return 返回cid
     */
    Long addCommodity(CommodityDTO commodity);

    /**
     * 修改商品信息
     *
     * @param commodity
     * @return
     */
    Integer updateCommodity(CommodityDTO commodity);

    /**
     * 删除指定商品
     *
     * @param cid
     * @return
     */
    Integer deleteCommodityByCid(Long cid);

    /**
     * 根据商品id获取商品
     *
     * @param cid
     * @return
     */
    CommodityDTO getCommodityById(Long cid);



    /**
     * 获取指定用户在线商品
     *
     * @param uid
     * @return
     */
    List<CommodityDTO> getCommoditiesFromUser(Page<CommodityDTO> page, Long uid);

    /**
     * 根据关键词获取商品列表
     * @param words
     * @param page
     * @return
     */
    List<CommodityDTO> getCommoditiesByKeyWordsPage(String words, IPage<CommodityDTO> page);

}
