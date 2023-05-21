package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.Commodity;
import com.lyu.entity.CommodityType;
import com.lyu.entity.CommodityTypeViewCount;
import com.lyu.entity.dto.CommodityDTO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

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
     * 根据request id 获取商品
     * @param rid
     * @return
     */
    List<CommodityDTO> getCommodityForRequest(Long rid);
    /**
     * 根据关键词获取商品列表
     *
     * @param page  分页参数
     * @param words 关键词
     * @return
     */
    IPage<CommodityDTO> getCommoditiesByKeyWords(IPage<CommodityDTO> page, String[] words);
    /**
     * 根据商品类别获取商品
     *
     * @param page  分页参数
     * @param typeId 类别id
     * @return
     */
    IPage<CommodityDTO> getCommoditiesByType(IPage<CommodityDTO> page,Integer typeId);
    /**
     * 获取用户最近浏览的商品类型
     *
     * @param uid
     * @return
     */
    List<CommodityTypeViewCount> getRecentlyViewedCommodityType(long uid);



    /**
     * 获取推荐商品
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<CommodityDTO> getCommodityRecommend(IPage<CommodityDTO> page, long uid);

    /**
     * 返回最新商品
     *
     * @param page
     * @return
     */
    IPage<CommodityDTO> getCommoditiesLatest(IPage<CommodityDTO> page);
    /**
     * 返回用户近期浏览最多的商品分类
     *
     * @param uid
     * @param now
     * @return
     */
    List<CommodityType> getTypeRecommend(long uid,LocalDateTime now);
    /**
     * 返回近期最热的商品分类
     * @param now
     * @return
     */
    List<CommodityType> getTypeHotRecent(LocalDateTime now);


}

