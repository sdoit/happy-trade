package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.Request;
import com.lyu.entity.dto.RequestDTO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/3/8 16:14
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
@Mapper
public interface RequestMapper extends BaseMapper<Request> {
    /**
     * 获取指定id的求购
     *
     * @param id 商品id
     * @return
     */
    RequestDTO getRequestById(Long id);
    /**
     * 获取指定用户发布的求购
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<RequestDTO> getRequestsFromUser(Page<RequestDTO> page, Long uid);

    /**
     * 根据关键词获取求购商品列表
     *
     * @param page  分页参数
     * @param words 关键词
     * @return
     */
    IPage<RequestDTO> getRequestsByKeyWords(IPage<RequestDTO> page, String[] words);


    /**
     * 根据类别获取求购列表
     *
     * @param page  分页参数
     * @param typeId 类别id
     * @return
     */
    IPage<RequestDTO> getRequestsByType(IPage<RequestDTO> page, Integer typeId);



    /**
     * 获取推荐求购
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<RequestDTO> getRequestRecommend(IPage<RequestDTO> page, long uid);

    /**
     * 返回最新求购
     *
     * @param page
     * @return
     */
    IPage<RequestDTO> getRequestsLatest(IPage<RequestDTO> page);


}
