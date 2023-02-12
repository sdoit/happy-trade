package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.UserFavorite;
import com.lyu.entity.dto.UserFavoriteDTO;
import com.lyu.exception.UserException;
import com.lyu.exception.UserFavoriteException;
import com.lyu.mapper.UserFavoriteMapper;
import com.lyu.service.UserFavoritesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author LEE
 * @time 2023/2/11 14:59
 */
@Slf4j
@Service
public class UserFavoritesServiceImpl implements UserFavoritesService {
    @Resource
    private UserFavoriteMapper userFavoriteMapper;

    @Override
    public void saveFavorite(UserFavorite userFavorite) {
        userFavorite.setUid(StpUtil.getLoginIdAsLong());
        userFavorite.setTime(LocalDateTime.now());
        userFavoriteMapper.insert(userFavorite);
    }

    @Override
    public void deleteFavoriteByFid(Long fid) {
        UserFavorite userFavorite = userFavoriteMapper.selectById(fid);
        if (userFavorite == null) {
            throw new UserFavoriteException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
        }
        if (!userFavorite.getUid().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        userFavoriteMapper.deleteById(fid);
    }

    @Override
    public void deleteFavoriteByUidAndCid(Long cid) {
        Long uid = StpUtil.getLoginIdAsLong();
        userFavoriteMapper.delete(new QueryWrapper<UserFavorite>().eq("uid", uid).eq("cid", cid));
    }

    @Override
    public IPage<UserFavoriteDTO> getFavoritesByUid(IPage<UserFavoriteDTO> page) {
        Long uid = StpUtil.getLoginIdAsLong();
        return userFavoriteMapper.getFavoritesByUid(page, uid);
    }
}
