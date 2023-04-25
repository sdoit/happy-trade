package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.UserResource;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/1/29 14:20
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
@Mapper
public interface UserResourceMapper extends BaseMapper<UserResource> {
}
