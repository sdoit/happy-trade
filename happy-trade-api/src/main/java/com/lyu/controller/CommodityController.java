package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
import com.lyu.entity.dto.CommodityDTO;
import com.lyu.service.CommodityService;
import com.lyu.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private UserService userService;

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
    public CommonResult<List<CommodityDTO>> getCommoditiesFromUser(Integer page) {

        List<CommodityDTO> commodities = commodityService.getCommoditiesFromUser(
                new Page<>(page, Constant.COMMODITY_PER_PAGE),
                StpUtil.getLoginIdAsLong());
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodities);
    }

    @ApiOperation("根据关键词获取商品（分页）")
    @GetMapping
    public CommonResult<List<CommodityDTO>> getCommoditiesByKeyWords(@NotNull String keyword, @NotNull Integer page) {
        if (Strings.isBlank(keyword) || page == null || page < 0) {
            return null;
        }
        Page<CommodityDTO> commodityPage = new Page<>(page, Constant.COMMODITY_PER_PAGE);
        List<CommodityDTO> commodities = commodityService.getCommoditiesByKeyWordsPage(keyword, commodityPage);
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodities);
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{cid}")
    public CommonResult<Object> deleteCommodityByCid(@PathVariable @NotNull Long cid) {
        Integer result = commodityService.deleteCommodityByCid(cid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, result);
    }

}
