package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.entity.UserFavorite;
import com.lyu.entity.dto.UserFavoriteDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/2/11 14:54
 */
@Mapper
public interface UserFavoriteMapper extends BaseMapper<UserFavorite> {

    /**
     * [分页] 获取用户的收藏
     * @param page
     * @param uid
     * @return
     */
    IPage<UserFavoriteDTO> getFavoritesByUid(IPage<UserFavoriteDTO> page, Long uid);
}
