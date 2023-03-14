package com.lyu.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.entity.dto.UserFavoriteDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/3/10 16:22
 */
@Mapper
public interface UserFavoritesRequestMapper extends UserFavoriteMapper{
    /**
     * 获取收藏的求购
     * @param page
     * @param uid
     * @return
     */
    @Override
    IPage<UserFavoriteDTO> getFavoritesByUid(IPage<UserFavoriteDTO> page, Long uid);
}
