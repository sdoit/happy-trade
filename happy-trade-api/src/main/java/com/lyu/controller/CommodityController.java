package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
import com.lyu.entity.CommodityType;
import com.lyu.entity.dto.CommodityDTO;
import com.lyu.service.CommodityService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2022/12/28 10:09
 */
@Validated
@RestController
@RequestMapping("/api/commodity")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("商品操作接口")
public class CommodityController {
    @Resource
    private CommodityService commodityService;


    @ApiOperation("发布新商品")
    @PostMapping
    public CommonResult<Long> saveCommodity(@RequestBody @NotNull CommodityDTO commodityDTO) {
        Long cid = commodityService.addCommodity(commodityDTO);
        return CommonResult.Result(CodeAndMessage.SUCCESS, cid);
    }

    @ApiOperation("编辑商品信息")
    @PutMapping
    public CommonResult<Object> editCommodity(@RequestBody @NotNull CommodityDTO commodityDTO) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityService.updateCommodity(commodityDTO));
    }

    @ApiOperation("下架商品")
    @PutMapping("/down/{cid}")
    public CommonResult<Object> takeDownCommodity(@PathVariable("cid") @NotNull Long cid) {
        commodityService.takeDownCommodity(cid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @ApiOperation("上架架商品")
    @PutMapping("/uploaded/{cid}")
    public CommonResult<Object> uploadedCommodity(@PathVariable("cid") @NotNull Long cid) {
        commodityService.uploadedCommodity(cid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }


    @ApiOperation("根据cid获取指定商品")
    @GetMapping("/{cid}")
    public CommonResult<CommodityDTO> getCommodityById(@NotNull @PathVariable("cid") Long cid) {
        if (cid == null) {
            return null;
        }
        CommodityDTO commodity = commodityService.getCommodityById(cid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodity);
    }

    @ApiOperation("获取指定用户的商品")
    @GetMapping("/u")
    public CommonResult<List<CommodityDTO>> getCommoditiesFromUser(@NotNull Integer page) {
        List<CommodityDTO> commodities = commodityService.getCommoditiesFromUser(
                new Page<>(page, Constant.COMMODITY_PER_PAGE),
                StpUtil.getLoginIdAsLong());
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodities);
    }

    @ApiOperation("获取指定类型的商品")
    @GetMapping("/type/{typeId}")
    public CommonResult<List<CommodityDTO>> getCommoditiesByType(@NotNull Integer page, @NotNull @PathVariable("typeId") Integer typeId) {
        List<CommodityDTO> commodities = commodityService.getCommoditiesByType(
                new Page<>(page, Constant.COMMODITY_PER_PAGE), typeId);
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodities);
    }

    @ApiOperation("根据关键词获取商品（分页）/ 如果keyword为null，则依据用户浏览习惯返回推荐商品")
    @GetMapping
    public CommonResult<List<CommodityDTO>> getCommoditiesByKeyWords(@Nullable String keyword, @NotNull Integer page) {
        Page<CommodityDTO> commodityPage = new Page<>(page, Constant.COMMODITY_PER_PAGE);
        if (Strings.isBlank(keyword)) {
            if (StpUtil.isLogin()) {
                //如果用户已登录，根据用户浏览历史推荐
                return CommonResult.Result(CodeAndMessage.SUCCESS, commodityService.getCommoditiesRecommendations(commodityPage));
            } else {
                //如果未登录，就随机最新商品
                return CommonResult.Result(CodeAndMessage.SUCCESS, commodityService.getCommoditiesLatest(commodityPage));
            }
        }
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityService.getCommoditiesByKeyWordsPage(keyword, commodityPage));
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{cid}")
    public CommonResult<Object> deleteCommodityByCid(@PathVariable @NotNull Long cid) {
        Integer result = commodityService.deleteCommodityByCid(cid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, result);
    }

    @ApiOperation("获取大众感兴趣的分类或者用户感兴趣的商品类别")
    @GetMapping("/type")
    public CommonResult<List<CommodityType>> getTypeRecommend() {
        List<CommodityType> typeRecommend = commodityService.getTypeRecommend();
        return CommonResult.Result(CodeAndMessage.SUCCESS, typeRecommend);
    }


}
