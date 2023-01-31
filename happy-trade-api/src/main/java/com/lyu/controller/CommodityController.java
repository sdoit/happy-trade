package com.lyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.Constant;
import com.lyu.entity.Commodity;
import com.lyu.entity.User;
import com.lyu.entity.dto.CommodityDTO;
import com.lyu.service.CommodityService;
import com.lyu.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2022/12/28 10:09
 */
@RestController
@RequestMapping("/api/commodity")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("商品操作接口")
public class CommodityController {
    @Resource
    private CommodityService commodityService;
    @Resource
    private UserService userService;

    @ApiOperation("根据cid获取指定商品")
    @GetMapping("/{cid}")
    public CommonResult<Commodity> getCommodityById(@PathVariable("cid") Long cid) {
        if (cid == null) {
            return null;
        }
        Commodity commodity = commodityService.getCommodityById(cid);
        return CommonResult.createCommonResult(CodeAndMessage.SUCCESS, commodity);
    }

    @ApiOperation("获取指定用户的商品")
    @GetMapping("/u/{uid}")
    public CommonResult<List<Commodity>> getCommoditiesFromUser(@PathVariable("uid") Long uid) {
        if (uid == null) {
            return null;
        }
        User user = userService.getUserByUid(uid);
        if (user == null) {
            return null;
        }


        List<Commodity> commodities = commodityService.getCommoditiesFromUser(user);
        return CommonResult.createCommonResult(CodeAndMessage.SUCCESS, commodities);
    }

    @ApiOperation("根据关键词获取商品（分页）")
    @GetMapping()
    public CommonResult<List<CommodityDTO>> getCommoditiesByKeyWords(String keyword, Integer page) {
        if (Strings.isBlank(keyword) || page == null || page < 0) {
            return null;
        }
        Page<CommodityDTO> commodityPage = new Page<>(page, Constant.COMMODITY_PER_PAGE);
        List<CommodityDTO> commodities = commodityService.getCommoditiesByKeyWordsPage(keyword, commodityPage);
        return CommonResult.createCommonResult(CodeAndMessage.SUCCESS, commodities);
    }

}
