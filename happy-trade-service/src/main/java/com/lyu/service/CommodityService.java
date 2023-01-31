package com.lyu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.entity.Commodity;
import com.lyu.entity.User;
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
     * @param user
     * @param commodity
     * @return
     */
    Integer addCommodity(Commodity commodity);

    /**
     * 修改商品信息
     *
     * @param commodity
     * @return
     */
    Integer updateCommodity(Commodity commodity);

    /**
     * 删除指定商品
     *
     * @param commodity
     * @return
     */
    Integer deleteCommodity(Commodity commodity);

    /**
     * 根据商品id获取商品
     *
     * @param cid
     * @return
     */
    Commodity getCommodityById(Long cid);

    /**
     * 获取指定用户在线商品
     *
     * @param user
     * @return
     */
    List<Commodity> getCommoditiesFromUser(User user);

    /**
     * 根据关键词获取商品列表
     * @param words
     * @param page
     * @return
     */
    List<CommodityDTO> getCommoditiesByKeyWordsPage(String words, IPage<CommodityDTO> page);

}
