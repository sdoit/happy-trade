package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.entity.CommodityBid;
import com.lyu.entity.dto.CommodityBidUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 11:37
 */
@Mapper
public interface CommodityBidMapper extends BaseMapper<CommodityBid> {
    /**
     * 根据cid获取一件商品的所有出价 携带买家的头像和昵称 ,已支付的
     *
     * @param cid
     * @return
     */
    List<CommodityBidUserDTO> getCommodityBidsPaidByCid(Long cid);

    /**
     * 根据uid获取买家的所有出价 携带卖家的头像和昵称 和 commodity 对象
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<CommodityBidUserDTO> getCommodityBidsByBuyerUid(IPage<CommodityBidUserDTO> page, Long uid);

    /**
     * 根据uid获取卖家的[所有]出价 携带卖家的头像和昵称 和 commodity 对象
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<CommodityBidUserDTO> getCommodityBidsBySellerUid(IPage<CommodityBidUserDTO> page, Long uid);

    /**
     * 根据uid获取卖家的[已回应]的出价 携带买家的头像和昵称 和 commodity 对象
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<CommodityBidUserDTO> getCommodityBidsRespondedBySellerUid(IPage<CommodityBidUserDTO> page, Long uid);

    /**
     * 根据uid获取卖家的[未回应]出价 携带买家的头像和昵称 和 commodity 对象
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<CommodityBidUserDTO> getCommodityBidsNoResponseBySellerUid(IPage<CommodityBidUserDTO> page, Long uid);

    /**
     * 根据uid获取卖家的[接受]出价 携带买家的头像和昵称 和 commodity 对象
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<CommodityBidUserDTO> getCommodityBidsAgreedBySellerUid(IPage<CommodityBidUserDTO> page, Long uid);

    /**
     * 根据uid获取卖家的[拒绝]出价 携带买家的头像和昵称 和 commodity 对象
     *
     * @param page
     * @param uid
     * @return
     */
    IPage<CommodityBidUserDTO> getCommodityBidsRejectedBySellerUid(IPage<CommodityBidUserDTO> page, Long uid);


    /**
     * 判断用户是否已对某商品出价
     *
     * @param uid
     * @param cid
     * @return
     */
    Boolean orderOrBidExist(Long uid, Long cid);

}
