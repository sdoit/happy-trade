package com.lyu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.CommonResultPage;
import com.lyu.common.Constant;
import com.lyu.entity.UserFavorite;
import com.lyu.entity.dto.UserFavoriteDTO;
import com.lyu.service.UserFavoritesService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/11 15:28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/favorites")
@ApiOperation("用户收藏夹操作接口")
public class UserFavoriteController {
    @Resource
    private UserFavoritesService userFavoritesService;

    @ApiOperation("添加收藏")
    @PostMapping
    public CommonResult<Object> saveFavorite(@RequestBody @NotNull UserFavorite userFavorite) {
        userFavoritesService.saveFavorite(userFavorite);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @ApiOperation("通过fid删除收藏")
    @DeleteMapping("/{fid}")
    public CommonResult<Object> deleteFavoriteByFid(@NotNull @PathVariable("fid") Long fid) {
        userFavoritesService.deleteFavoriteByFid(fid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);

    }

    @ApiOperation("通过uid和cid删除收藏")
    @DeleteMapping("/c/{cid}")
    public CommonResult<Object> deleteFavoriteByUidAndCid(@NotNull @PathVariable("cid") Long cid) {
        userFavoritesService.deleteFavoriteByUidAndCid(cid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @ApiOperation("[分页]获取用户的收藏夹")
    @GetMapping
    public CommonResultPage<List<UserFavoriteDTO>> getFavoritesByUid(@NotNull Integer page) {
        IPage<UserFavoriteDTO> pageDTO = new Page<>(page, Constant.FAVORITE_PER_PAGE);
        IPage<UserFavoriteDTO> favorites = userFavoritesService.getFavoritesByUid(pageDTO);
        return CommonResultPage.Result(CodeAndMessage.SUCCESS, favorites.getRecords(), favorites.getTotal());
    }
}
