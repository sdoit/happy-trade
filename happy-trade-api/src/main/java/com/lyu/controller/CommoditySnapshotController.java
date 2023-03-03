package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.dto.CommoditySnapshotDTO;
import com.lyu.service.CommoditySnapshotService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/3/1 15:06
 */
@Validated
@RestController
@RequestMapping("/api/commodity/snapshot")

@ApiOperation("商品交易快照")
public class CommoditySnapshotController {
    @Resource
    private CommoditySnapshotService commoditySnapshotService;

    @ApiOperation("根据cid获取指定商品")
    @GetMapping("/{ssid}")
    public CommonResult<CommoditySnapshotDTO> getCommodityById(@NotNull @PathVariable("ssid") Long ssid) {
        CommoditySnapshotDTO commoditySnapshotDTO = commoditySnapshotService.getCommoditySnapshotBySsid(ssid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, commoditySnapshotDTO);
    }

}
