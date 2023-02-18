package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.Tag;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/14 14:35
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 映射tag和commodity
     * @param tags
     * @param cid
     * @param time
     * @return
     */
    Integer mapCommodityAndTags(Collection<Tag> tags, Long cid, LocalDateTime time);

    /**
     * 根据cid获取目前所有的tags
     * @param cid
     * @return
     */
    List<Tag> getAllTagsByCid(Long cid);

    /**
     * 取消cid的所有包含在tags的tags
     * @param cid
     * @param tags
     * @return
     */
    Integer cancelAllTagsMap(Long cid, Collection<Tag> tags);
}
