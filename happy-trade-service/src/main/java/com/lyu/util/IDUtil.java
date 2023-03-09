package com.lyu.util;

import cn.hutool.core.util.RandomUtil;
import com.lyu.common.Constant;
import com.lyu.entity.CommodityBid;
import com.lyu.entity.Order;
import com.lyu.entity.WithdrawalOrder;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LEE
 * @time 2022/12/29 10:11
 */
@Component
public class IDUtil {


    private final AtomicLong mark = new AtomicLong(RandomUtil.randomLong(0, 99));

    /**
     * 为没有id的order生成一个唯一的、递增的id
     *
     * @param order
     * @return 返回的order为添加cid后的order
     */
    public Long getNextOrderId(Order order) {
        long uidSuffix = order.getUidBuyer() % 100;
        long uid2Suffix = order.getUidSeller() % 100;
        Long nextIdWithMark = getNextIDWithMark();
        return nextIdWithMark * 10000 + uid2Suffix * 100 + uidSuffix;
    }

    public Long getNextWithdrawalOrderId(WithdrawalOrder withdrawalOrder) {
        long mark1 = withdrawalOrder.getAmount().intValue() % 100;
        long mark2 = withdrawalOrder.getUid() % 100;
        Long nextIdWithMark = getNextIDWithMark();
        return nextIdWithMark * 10000 + mark1 * 100 + mark2;
    }

    public Long getNextCommodityBidId(CommodityBid commodityBid) {
        long nowTimeStamp = System.currentTimeMillis();
        long nextIdWithMark = (nowTimeStamp + Constant.ID_OFFSET * 2) * 100 + mark.incrementAndGet() % 100;


        long uidSuffix = commodityBid.getUidBuyer() % 100;
        long uid2Suffix = commodityBid.getUidSeller() % 100;
        return nextIdWithMark * 10000 + uid2Suffix * 100 + uidSuffix;
    }


    public Long getNextCommodityId() {
        return getNextIDWithMark();
    }

    private Long getNextIDWithMark() {
        long nowTimeStamp = System.currentTimeMillis();
        return (nowTimeStamp + Constant.ID_OFFSET) * 100 + mark.incrementAndGet() % 100;
    }



}