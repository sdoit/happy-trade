package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.Commodity;
import com.lyu.entity.Order;
import com.lyu.entity.User;
import com.lyu.entity.dto.CommoditySimpleDTO;
import com.lyu.service.CommodityService;
import com.lyu.service.OrderService;
import com.lyu.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2022/12/28 12:05
 */

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("订单操作接口")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;
    @Resource
    private CommodityService commodityService;
    @Value("${hostname}")
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
     * @param commoditySimpleDTO
     * @return
     */
    @ApiOperation("创建订单")
    @PostMapping("/create")
    public CommonResult<String> createOrder(CommoditySimpleDTO commoditySimpleDTO) {
        Long loginId = StpUtil.getLoginIdAsLong();
        User user = userService.getUserByUid(loginId);
        Commodity commodity = new Commodity();
        commodity.setCid(commoditySimpleDTO.getCid());
        Order order = orderService.createOrder(user, commodity);

        //创建支付链接
        String payUrl = StrUtil.format("{}://{}:{}/api/alipay/pay?traceNo={}&totalAmount={}&subject={}&returnUrl={}",
                protocol, hostname, port, order.getOid(), order.getTotalAmount(), order.getName(), returnUrl);
        return CommonResult.createCommonResult(CodeAndMessage.ORDER_NEED_PAY, payUrl);
    }

}
