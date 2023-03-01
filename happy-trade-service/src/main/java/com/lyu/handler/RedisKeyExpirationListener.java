package com.lyu.handler;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyu.common.Constant;
import com.lyu.entity.Commodity;
import com.lyu.mapper.CommodityMapper;
import com.lyu.service.OrderService;
import com.lyu.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/2/7 14:57
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    @Resource
    private OrderService orderService;
    @Resource
    private CommodityMapper commodityMapper;
    

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        if (StrUtil.startWith(expiredKey, Constant.REDIS_ORDER_UNPAID_KEY_PRE)) {
            Long oid = Long.parseLong(StrUtil.subAfter(expiredKey, ":", true));
            Boolean exist = orderService.exist(oid);
            if (BooleanUtil.isTrue(exist)) {
                return;
            }
            Object cidObj = RedisUtil.getAndDelete(Constant.REDIS_ORDER_MAP_COMMODITY_KEY_PRE + oid);
            if (cidObj == null) {
                log.info("未获取到cid->" + expiredKey);
                return;
            }
            Long cid = (Long) cidObj;
            commodityMapper.update(null, new UpdateWrapper<Commodity>().set("sold", false).eq("cid", cid));
            log.info("oid:cid->" + oid + ":" + cid + "未支付，已取消订单");
        } else {
            log.debug(expiredKey);
        }
    }
}

