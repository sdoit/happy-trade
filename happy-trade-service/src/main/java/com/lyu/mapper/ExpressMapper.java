package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.Express;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @author LEE
 * @time 2023/3/1 20:41
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface ExpressMapper extends BaseMapper<Express>{

}
