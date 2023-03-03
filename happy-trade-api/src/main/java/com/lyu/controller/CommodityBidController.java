package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.common.CommonResultPage;
import com.lyu.common.Constant;
import com.lyu.entity.CommodityBid;
import com.lyu.entity.dto.CommodityBidDTO;
import com.lyu.entity.dto.CommodityBidSimplyDTO;
import com.lyu.service.CommodityBidService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 11:49
 */
@Validated
@ApiOperation("用户商品出价api")
@RestController()
@RequestMapping("/api/bid")
public class CommodityBidController {


    @Value("${hostname}")
    private String hostname;
    @Value("${protocol}")
    private String protocol;
    @Value("${server.port}")
    private String port;
    @Value("${alipay.returnUrl}")
    private String returnUrl;


    @Resource
    private CommodityBidService commodityBidService;

    @ApiOperation("创建出价")
    @PostMapping
    public CommonResult<Object> createCommodityBid(@RequestBody @NotNull CommodityBid commodityBid) {
        CommodityBid commodityBidResult = commodityBidService.createCommodityBid(commodityBid);

        //创建支付链接
        String payUrl = StrUtil.format("{}://{}:{}/api/alipay/pay?traceNo={}&totalAmount={}&subject={}&type={}&returnUrl={}",
                protocol, hostname, port, "0" + commodityBidResult.getBid(), commodityBidResult.getPrice(), commodityBidResult.getName(), Constant.ALIPAY_PAY_TYPE_BID, returnUrl);
        return CommonResult.Result(CodeAndMessage.ORDER_NEED_PAY, payUrl);

    }

    @ApiOperation("卖家同意此报价")
    @PostMapping("/agree")
    public CommonResult<Object> agreeCommodityBid(@RequestBody @NotNull CommodityBidSimplyDTO commodityBidSimplyDTO) {
        Integer result = commodityBidService.agreeCommodityBid(commodityBidSimplyDTO.getBid(), commodityBidSimplyDTO.getMessage());
        if (result != null && result == 1) {
            return CommonResult.Result(CodeAndMessage.SUCCESS, null);

        } else {
            return CommonResult.Result(CodeAndMessage.UNEXPECTED_ERROR, null);
        }
    }

    @ApiOperation("卖家拒绝此报价")
    @PostMapping("/reject")
    public CommonResult<Object> rejectCommodityBid(@RequestBody @NotNull CommodityBidSimplyDTO commodityBidSimplyDTO) {
        Integer result = commodityBidService.rejectCommodityBid(commodityBidSimplyDTO.getBid(), commodityBidSimplyDTO.getMessage());
        if (result != null && result == 1) {
            return CommonResult.Result(CodeAndMessage.SUCCESS, null);

        } else {
            return CommonResult.Result(CodeAndMessage.UNEXPECTED_ERROR, null);
        }
    }

    @ApiOperation("获取某商品的全部报价")
    @GetMapping("/commodity/{cid}")
    public CommonResult<CommodityBidDTO> getCommodityBidsByCid(@NotNull @PathVariable("cid") Long cid) {
        CommodityBidDTO commodityBidDTO = commodityBidService.getCommodityBidsPaidByCid(cid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityBidDTO);
    }

    @ApiOperation("获取我(作为卖家)的商品的所有出价")
    @GetMapping("/seller")
    public CommonResultPage<List<CommodityBidDTO>> getCommodityBidsBySellerUid(Integer page, String type) {

        if (!Constant.BID_GET_ALL.equals(type) &&
                !Constant.BID_GET_REJECTED.equals(type) &&
                !Constant.BID_GET_NO_RESPONSE.equals(type) &&
                !Constant.BID_GET_RESPONDED.equals(type) &&
                !Constant.BID_GET_AGREED.equals(type)) {
            return CommonResultPage.Result(CodeAndMessage.WRONG_REQUEST_PARAMETER, null, 0L);

        }
        long uid = StpUtil.getLoginIdAsLong();
        if (page == null) {
            page = 1;
        }
        Page<CommodityBidDTO> dtoPage = new Page<>(page, Constant.BID_PER_PAGE);
        IPage<CommodityBidDTO> commodityBids = commodityBidService.getCommodityBidsBySellerUid(uid, dtoPage, type);
        return CommonResultPage.Result(CodeAndMessage.SUCCESS, commodityBids.getRecords(), commodityBids.getTotal());

    }

    @ApiOperation("根据id获取一个报价")
    @GetMapping("/b/{bid}")
    public CommonResult<CommodityBid> getCommodityBidByBid(@NotNull @PathVariable("bid") Long bid) {
        CommodityBid commodityBid = commodityBidService.getCommodityBidByBid(bid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityBid);
    }

    @ApiOperation("获取当前登录用户的所有出价")
    @GetMapping
    public CommonResult<List<CommodityBidDTO>> getCommodityBidsByBuyerUid(Integer page) {
        long uid = StpUtil.getLoginIdAsLong();
        if (page == null) {
            page = 1;
        }
        Page<CommodityBidDTO> dtoPage = new Page<>(page, Constant.BID_PER_PAGE);
        List<CommodityBidDTO> commodityBids = commodityBidService.getCommodityBidsByBuyerUid(dtoPage, uid).getRecords();
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityBids);

    }

    @ApiOperation("获取一个买家对本商品是否已出价")
    @GetMapping("/{cid}")
    public CommonResult<Boolean> getCommodityBidsExistByUidCid(@NotNull @PathVariable("cid") Long cid) {
        Boolean exist = commodityBidService.orderOrBidExist(cid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, exist);
    }

    @ApiOperation("买家撤销出价")
    @PostMapping("/revoke/{bid}")
    public CommonResult<Object> revokeCommodityBidsExistByBid(@NotNull @PathVariable("bid") Long bid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityBidService.revokeCommodityBidByBid(bid));
    }
}
