package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.CommodityType;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/2/15 13:57
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
@Mapper
public interface CommodityTypeMapper extends BaseMapper<CommodityType> {

}
