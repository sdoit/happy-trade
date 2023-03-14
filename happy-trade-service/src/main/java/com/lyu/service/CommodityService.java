package com.lyu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.entity.CommodityType;
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
     * 下架商品
     *
     * @param cid
     * @return
     */
    void takeDownCommodity(Long cid);

    /**
     * 上架商品
     *
     * @param cid
     * @return
     */
    void uploadedCommodity(Long cid);

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
     * 根据求购id获取商品
     *
     * @param rid
     * @return
     */
    List<CommodityDTO> getCommodityForRequest(Long rid);

    /**
     * 获取指定用户在线商品
     *
     * @param page
     * @param uid
     * @return
     */
    List<CommodityDTO> getCommoditiesFromUser(Page<CommodityDTO> page, Long uid);

    /**
     * 根据关键词获取商品列表
     *
     * @param words
     * @param page
     * @return
     */
    List<CommodityDTO> getCommoditiesByKeyWordsPage(String words, IPage<CommodityDTO> page);

    /**
     * 获取指定分类的商品
     *
     * @param page
     * @param type
     * @return
     */
    List<CommodityDTO> getCommoditiesByType(IPage<CommodityDTO> page, Integer type);


    /**
     * 根据用户浏览内容，推荐商品
     *
     * @param page
     * @return
     */
    List<CommodityDTO> getCommoditiesRecommendations(IPage<CommodityDTO> page);

    /**
     * 返回最新商品
     *
     * @param page
     * @return
     */
    List<CommodityDTO> getCommoditiesLatest(IPage<CommodityDTO> page);

    /**
     * 返回用户感兴趣的商品分类
     * 如果没有登陆，返回浏览量较高的商品分类
     *
     * @return 返回5个分类（二级分类）
     */
    List<CommodityType> getTypeRecommend();


}
