package com.lyu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.entity.CommodityBid;
import com.lyu.entity.dto.CommodityBidUserDTO;
import com.lyu.exception.CommodityException;
import com.lyu.exception.UserException;

import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 11:38
 */
public interface CommodityBidService {
    /**
     * 创建商品出价
     *
     * @param commodityBid
     * @return
     */
    CommodityBid createCommodityBid(CommodityBid commodityBid);

    /**
     * 创建订单
     *
     * @param commodityBid
     * @return
     * @throws UserException
     * @throws CommodityException
     */
    Integer createOrderFromBid(CommodityBid commodityBid) throws UserException, CommodityException;

    /**
     * 卖家同意此报价
     *
     * @param bid     报价id
     * @param message 卖家留言
     * @return
     */
    Integer agreeCommodityBid(Long bid, String message);

    /**
     * 卖家拒绝此报价
     *
     * @param bid     报价id
     * @param message 卖家留言
     * @return
     */
    Integer rejectCommodityBid(Long bid, String message);

    /**
     * 获取一个商品的所有用户已支付的出价
     *
     * @param cid
     * @return
     */
    List<CommodityBidUserDTO> getCommodityBidsPaidByCid(Long cid);

    /**
     * 通过报价id获取报价
     *
     * @param bid
     * @return
     */
    CommodityBid getCommodityBidByBid(Long bid);

    /**
     * 获取一个买家的所有的出价
     *
     * @param page
     * @param uid
     * @return
     */
    List<CommodityBidUserDTO> getCommodityBidsByBuyerUid(IPage<CommodityBidUserDTO> page, Long uid);

    /**
     * 获取一个卖家的商品的所有出价
     *
     * @param page
     * @param uid
     * @param type
     * @return
     */
    IPage<CommodityBidUserDTO> getCommodityBidsBySellerUid(Long uid, IPage<CommodityBidUserDTO> page, String type);


    /**
     * 用户完成支付
     *
     * @param commodityBid
     * @return
     */
    Integer completePay(CommodityBid commodityBid);


    /**
     * 取消一个商品的所有报价
     *
     * @param cid
     * @return
     */
    Integer cancelBidByCid(Long cid);

    /**
     * 取消一个商品的所有[未回应]的报价
     *
     * @param cid
     * @return
     */
    Integer cancelBidNoResponseByCid(Long cid);

    /**
     * 判断报价是否存在
     *
     * @param cid
     * @return
     */
    Boolean orderOrBidExist(Long cid);


    /**
     * 为已退款的bid设置退款时间
     *
     * @param bid
     * @return
     */
    Integer setRefundTimeForRefundedBid(Long bid);
}
