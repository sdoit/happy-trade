package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.OrderReturn;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/4/22 19:55
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
@Mapper
public interface OrderReturnMapper extends BaseMapper<OrderReturn> {
}
