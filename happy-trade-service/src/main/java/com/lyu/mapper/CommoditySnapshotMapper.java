package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.CommoditySnapshot;
import com.lyu.entity.dto.CommoditySnapshotDTO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/2/26 10:08
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
@Mapper
public interface CommoditySnapshotMapper extends BaseMapper<CommoditySnapshot> {
    /**
     * 根据商品id获取商品
     *
     * @param ssid
     * @return
     */
    CommoditySnapshotDTO getCommoditySnapshotBySsid(Long ssid);

}
