package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.entity.Commodity;
import com.lyu.entity.dto.CommodityDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
    /**
     * 保存商品信息到数据库
     *
     * @param commodity
     * @return
     */
//    Integer saveCommodity(Commodity commodity);

    /**
     * 更新商品信息
     *
     * @param commodity
     * @return
     */
//    Integer updateCommodity(Commodity commodity);

    /**
     * 删除商品，不可恢复
     *
     * @param commodity
     * @return
     */
//    Integer deleteCommodity(Commodity commodity);



    /**
     * 获取所有的商品
     *
     * @return
     */
//    List<Commodity> getCommodities();

    /**
     * 获取指定用户发布的商品
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<CommodityDTO> getCommoditiesFromUser(Page<CommodityDTO> page, Long uid);


    /**
     * 获取指定id的商品
     *
     * @param cid 商品id
     * @return
     */
    CommodityDTO getCommodityById(Long cid);

    /**
     * 根据关键词获取商品列表
     * @param page 分页参数
     * @param words 关键词
     * @return
     */
    IPage<CommodityDTO> getCommoditiesByKeyWords(IPage<CommodityDTO> page, String[] words);
}

