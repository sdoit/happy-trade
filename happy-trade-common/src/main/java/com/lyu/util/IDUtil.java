package com.lyu.util;

import cn.hutool.core.util.RandomUtil;
import com.lyu.common.Constant;
import com.lyu.entity.Order;
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
        long uidSuffix = order.getUid() % 100;
        long uid2Suffix = order.getUid2() % 100;
        Long nextIdWithMark = getNextIDWithMark();
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