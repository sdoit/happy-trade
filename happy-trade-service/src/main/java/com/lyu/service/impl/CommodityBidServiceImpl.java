package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.*;
import com.lyu.entity.dto.CommodityBidUserDTO;
import com.lyu.exception.CommodityBidException;
import com.lyu.exception.CommodityException;
import com.lyu.exception.OrderException;
import com.lyu.exception.UserException;
import com.lyu.mapper.CommodityBidMapper;
import com.lyu.mapper.CommodityMapper;
import com.lyu.mapper.OrderMapper;
import com.lyu.service.AlipayService;
import com.lyu.service.CommodityBidService;
import com.lyu.service.UserService;
import com.lyu.util.IDUtil;
import com.lyu.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 12:58
 */
@Slf4j
@Service
public class CommodityBidServiceImpl implements CommodityBidService {
    @Resource
    private UserService userService;
    @Resource
    private CommodityBidMapper commodityBidMapper;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private AlipayService alipayService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private IDUtil idUtil;
    @Resource
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public CommodityBid createCommodityBid(CommodityBid commodityBid) {
        CommodityBid commodityBidUse = new CommodityBid();
        long uidLogin = StpUtil.getLoginIdAsLong();
        User user = userService.getUserByUid(uidLogin);
        //检查用户状态
        if (user.getBanedTime() > 0) {
            throw new UserException(CodeAndMessage.BANED_USER.getCode(), CodeAndMessage.BANED_USER.getMessage());
        }
        Commodity commodity = commodityMapper.selectById(commodityBid.getCid());
        if (commodity == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        if (commodity.getSold()) {
            //已卖出
            throw new CommodityException(CodeAndMessage.ITEM_SOLD.getCode(), CodeAndMessage.ITEM_SOLD.getMessage());
        }
        if (uidLogin == commodity.getUid()) {
            throw new OrderException(CodeAndMessage.CANT_BUY_OWN_PRODUCT.getCode(), CodeAndMessage.CANT_BUY_OWN_PRODUCT.getMessage());
        }
        //检查用户是否已对本商品报价
        Boolean exists = commodityBidMapper.orderOrBidExist(uidLogin, commodityBid.getCid());
        if (BooleanUtil.isTrue(exists)) {
            throw new CommodityBidException(CodeAndMessage.BID_ALREADY_EXISTS.getCode(), CodeAndMessage.BID_ALREADY_EXISTS.getMessage());
        }
        commodityBidUse.setCid(commodity.getCid());
        commodityBidUse.setName(commodity.getName());
        commodityBidUse.setPrice(commodityBid.getPrice());
        commodityBidUse.setUidBuyer(StpUtil.getLoginIdAsLong());
        commodityBidUse.setUidSeller(commodity.getUid());
        commodityBidUse.setMessageBuyer(commodityBid.getMessageBuyer());
        commodityBidUse.setTimeCreated(LocalDateTime.now());
        commodityBidUse.setAid(commodityBid.getAid());
        commodityBidUse.setBid(idUtil.getNextCommodityBidId(commodityBidUse));
        //放置到Redis缓存服务器中，30分钟超时，如果支付再写入数据库
        redisUtil.set(Constant.REDIS_BID_UNPAID_KEY_PRE + commodityBidUse.getBid(), commodityBidUse);
        //设置过期时间 比支付超时时间稍长
        redisUtil.expire(Constant.REDIS_BID_UNPAID_KEY_PRE + commodityBidUse.getBid(), 60L * (Constant.ALIPAY_TIME_EXPIRE << 1));
//        int result = commodityBidMapper.insert(commodityBidUse);
//        if (result != 1) {
//            throw new OrderException(CodeAndMessage.UNEXPECTED_ERROR.getCode(), CodeAndMessage.UNEXPECTED_ERROR.getMessage());
//        }
        return commodityBidUse;
    }

    @Override
    public Integer createOrderFromBid(CommodityBid commodityBid) throws UserException, CommodityException {
        long uid = commodityBid.getUidBuyer();
        User user = userService.getUserByUid(uid);
        //检查用户状态
        if (user.getBanedTime() > 0) {
            throw new UserException(CodeAndMessage.BANED_USER.getCode(), CodeAndMessage.BANED_USER.getMessage());
        }
        //检查库存
        Commodity commodity = commodityMapper.selectById(commodityBid.getCid());
        if (commodity.getSold()) {
            //已卖出 发起退款
            log.info("商品已售出，发起退款 cid:" + commodityBid.getCid());
            alipayService.refund(commodityBid.getTradeId(), commodityBid.getPrice(), Constant.REFUND_DUE_TO_DIRECT_PURCHASE, String.valueOf(commodityBid.getBid()), Constant.ALIPAY_PAY_TYPE_BID);
            throw new CommodityException(CodeAndMessage.ITEM_SOLD.getCode(), CodeAndMessage.ITEM_SOLD.getMessage());
        }
        commodity.setSold(true);
        commodityMapper.updateById(commodity);
        Order order = new Order();
        order.setCid(commodity.getCid());
        order.setUidSeller(commodity.getUid());
        order.setUidBuyer(user.getUid());
        order.setOrderTime(LocalDateTime.now());
        order.setTotalAmount(commodity.getPrice().doubleValue());
        order.setName(commodity.getName());
        order.setAid(commodityBid.getAid());
        order.setStatus(0);
        order.setBuyerAlipayId(commodityBid.getBuyerAlipayId());
        order.setTradeId(commodityBid.getTradeId());
        order.setPayTime(commodityBid.getPayTime());
        order.setOid(idUtil.getNextOrderId(order));
        Integer result = orderMapper.insert(order);
        //其他未回应的出价发起退款
        //获取本商品下的所有未回应的出价
        List<CommodityBid> commodityBidRefundList = commodityBidMapper.selectList(new QueryWrapper<CommodityBid>().eq("cid",
                order.getCid()).isNull("agree"));
        //拒绝其他报价
        commodityBidRefundList.forEach((commodityBidRefund) -> {
            this.rejectCommodityBid(commodityBidRefund.getBid(), Constant.REFUND_DUE_TO_SELLER_AGREES_TO_ANOTHER_BID);
        });
        return result;
    }

    @Override
    public Integer agreeCommodityBid(Long bid, String message) {
        long uid = StpUtil.getLoginIdAsLong();
        CommodityBid commodityBid = this.getCommodityBidByBid(bid);
        if (commodityBid == null || !commodityBid.getUidSeller().equals(uid)) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        commodityBid.setAgree(true);
        commodityBid.setReplySeller(message);
        commodityBid.setTimeReply(LocalDateTime.now());
        //生成正式订单
        this.createOrderFromBid(commodityBid);
        return commodityBidMapper.updateById(commodityBid);
    }

    @Override
    public Integer rejectCommodityBid(Long bid, String message) {
        long uid = StpUtil.getLoginIdAsLong();
        CommodityBid commodityBid = this.getCommodityBidByBid(bid);
        if (commodityBid == null || !commodityBid.getUidSeller().equals(uid)) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        commodityBid.setAgree(false);
        commodityBid.setReplySeller(message);
        commodityBid.setTimeReply(LocalDateTime.now());
        int result = commodityBidMapper.updateById(commodityBid);
        //发起退款
        alipayService.refund(commodityBid.getTradeId(), commodityBid.getPrice(), String.valueOf(commodityBid.getBid()),
                Constant.REFUND_DUE_TO_REJECTED, Constant.ALIPAY_PAY_TYPE_BID);
        return result;
    }

    @Override
    public List<CommodityBidUserDTO> getCommodityBidsPaidByCid(Long cid) {
        return commodityBidMapper.getCommodityBidsPaidByCid(cid);
    }

    @Override
    public CommodityBid getCommodityBidByBid(Long bid) {

        CommodityBid commodityBid = commodityBidMapper.selectById(bid);
        if (commodityBid == null) {
            throw new CommodityBidException(CodeAndMessage.INVALID_BID_ID.getCode(), CodeAndMessage.INVALID_BID_ID.getMessage());
        }
        return commodityBidMapper.selectById(bid);
    }

    @Override
    public List<CommodityBidUserDTO> getCommodityBidsByBuyerUid(IPage<CommodityBidUserDTO> page, Long uid) {
        return commodityBidMapper.getCommodityBidsByBuyerUid(page, uid).getRecords();

    }

    @Override
    public IPage<CommodityBidUserDTO> getCommodityBidsBySellerUid(Long uid, IPage<CommodityBidUserDTO> page, String type) {
        if (Constant.BID_GET_RESPONDED.equals(type)) {
            return commodityBidMapper.getCommodityBidsRespondedBySellerUid(page, uid);
        } else if (Constant.BID_GET_NO_RESPONSE.equals(type)) {
            return commodityBidMapper.getCommodityBidsNoResponseBySellerUid(page, uid);
        } else if (Constant.BID_GET_AGREED.equals(type)) {
            return commodityBidMapper.getCommodityBidsAgreedBySellerUid(page, uid);
        } else if (Constant.BID_GET_REJECTED.equals(type)) {
            return commodityBidMapper.getCommodityBidsRejectedBySellerUid(page, uid);
        }
        return commodityBidMapper.getCommodityBidsBySellerUid(page, uid);
    }

    @Override
    public Integer completePay(CommodityBid commodityBid) {
        if (commodityBid.getTradeId() == null || commodityBid.getPayTime() == null || commodityBid.getBuyerAlipayId() == null) {
            throw new CommodityBidException(CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getCode(), CodeAndMessage.ALIPAY_WRONG_PAYMENT_PARAMETER.getMessage());
        }
        //return commodityBidMapper.updateById(commodityBid);
        return commodityBidMapper.insert(commodityBid);
    }

    @Override
    public Integer cancelBidByCid(Long cid) {
        return commodityBidMapper.update(null, new UpdateWrapper<CommodityBid>().set("cancel", 1).eq("cid", cid));
    }

    @Override
    public Integer cancelBidNoResponseByCid(Long cid) {
        return commodityBidMapper.update(null, new UpdateWrapper<CommodityBid>().set("cancel", 1).eq("cid", cid).isNull("agree"));
    }


    @Override
    public Boolean orderOrBidExist(Long cid) {
        Long uidLogin = StpUtil.getLoginIdAsLong();
        return commodityBidMapper.orderOrBidExist(uidLogin, cid);
    }

    @Override
    public Integer setRefundTimeForRefundedBid(Long bid) {
        return commodityBidMapper.update(null, new UpdateWrapper<CommodityBid>().set("refund_time", LocalDateTime.now()));
    }
}
