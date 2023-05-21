package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.Commodity;
import com.lyu.entity.Order;
import com.lyu.entity.OrderRating;
import com.lyu.entity.UserAddress;
import com.lyu.entity.dto.ExpressDTO;
import com.lyu.entity.dto.OrderDTO;
import com.lyu.entity.dto.OrderSimpleDTO;
import com.lyu.service.CommodityBidService;
import com.lyu.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2022/12/28 12:05
 */
@Validated
@RestController
@RequestMapping("/api/order")
@Slf4j
@ApiOperation("订单操作接口")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private CommodityBidService commodityBidService;
    @Value("${pay-server}")
    private String hostname;
    @Value("${protocol}")
    private String protocol;
    @Value("${server.port}")
    private String port;
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    /**
     * 创建订单，创建成功后返回支付链接
     *
     * @param orderSimpleDTO
     * @return
     */
    @ApiOperation("创建订单")
    @PostMapping
    public CommonResult<String> createOrder(@RequestBody @NotNull OrderSimpleDTO orderSimpleDTO) {
        Commodity commodity = new Commodity();
        UserAddress userAddress = new UserAddress();
        commodity.setCid(orderSimpleDTO.getCid());
        userAddress.setAid(orderSimpleDTO.getAid());
        Order order = orderService.createOrder(commodity, userAddress);
        log.debug(hostname);
        //创建支付链接
        String payUrl = StrUtil.format("{}://{}:{}/api/alipay/pay?traceNo={}&totalAmount={}&subject={}&returnUrl={}",
                protocol, hostname, port, order.getOid(), order.getTotalAmount(), order.getName(), returnUrl);
        return CommonResult.Result(CodeAndMessage.ORDER_NEED_PAY, payUrl);
    }

    @ApiOperation("订单是否存在,同时查询bid和order 两者任一存在返回true")
    @GetMapping("/{cid}")
    public CommonResult<Boolean> exist(@NotNull @PathVariable("cid") Long cid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, commodityBidService.orderOrBidExist(cid));
    }

    @ApiOperation("获取用户的作为买家所有订单")
    @GetMapping
    public CommonResult<List<OrderDTO>> getOrdersByLoginUser() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, orderService.getOrdersByBuyerUid(StpUtil.getLoginIdAsLong()));
    }

    @ApiOperation("获取用户的作为卖家所有订单")
    @GetMapping("/seller")
    public CommonResult<List<OrderDTO>> getOrdersByLoginUserAsSeller() {
        return CommonResult.Result(CodeAndMessage.SUCCESS, orderService.getOrdersByBuyerUidAsSeller(StpUtil.getLoginIdAsLong()));
    }

    @ApiOperation("根据订单号获取指定订单")
    @GetMapping("/oid/{oid}")
    public CommonResult<OrderDTO> getOrderByOid(@NotNull @PathVariable("oid") Long oid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, orderService.getOrderByOid(oid));
    }

    @ApiOperation("根据商品id获取指定订单")
    @GetMapping("/c/{cid}")
    public CommonResult<OrderDTO> getOrderByCid(@NotNull @PathVariable("cid") Long cid) {
        return CommonResult.Result(CodeAndMessage.SUCCESS, orderService.getOrderByCid(cid));
    }

    @ApiOperation("买家确定收货完成订单/卖家评价买家")
    @PutMapping
    public CommonResult<OrderDTO> completeAndRateOrder(@NotNull @RequestBody OrderRating orderRating) {
        orderService.completeAndRateOrder(orderRating.getOid(), orderRating.getScore(), orderRating.getComment());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }


    @ApiOperation("卖家发货")
    @PutMapping("/express")
    public CommonResult<Object> expressOrder(@NotNull @RequestBody ExpressDTO expressDTO) {
        orderService.expressOrder(expressDTO.getOid(), expressDTO.getExpressId(), expressDTO.getShipId());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }


    @ApiOperation("卖家关闭订单")
    @PostMapping("/close/{oid}")
    public CommonResult<Object> closeOrder(@PathVariable("oid") Long oid) {
        orderService.cancelOrder(oid);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }


}
