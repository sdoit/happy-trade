package com.lyu.service;

import com.lyu.entity.dto.CommoditySnapshotDTO;

/**
 * @author LEE
 * @time 2023/2/26 10:08
 */
public interface CommoditySnapshotService {
    /**
     * 创建交易快照
     * @param cid
     * @return 返回ssid
     */
    Long createCommoditySnapshot(Long cid);

    /**
     * 根据商品id获取商品
     *
     * @param cid
     * @return
     */
    CommoditySnapshotDTO getCommoditySnapshotBySsid(Long ssid);

}
