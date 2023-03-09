package com.lyu.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.entity.UserFavorite;
import com.lyu.entity.dto.UserFavoriteDTO;

/**
 * @author LEE
 * @time 2023/2/11 14:54
 */
public interface UserFavoritesService {
    /**
     * 添加收藏到收藏夹/移除收藏
     *
     * @param userFavorite
     */
    boolean saveOrDeleteFavorite(UserFavorite userFavorite);



    /**
     * [分页]获取用户的收藏
     *
     * @param page
     * @return
     */
    IPage<UserFavoriteDTO> getFavoritesByUid(IPage<UserFavoriteDTO> page);
}
