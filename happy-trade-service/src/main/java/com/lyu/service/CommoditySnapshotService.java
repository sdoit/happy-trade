package com.lyu.service;

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
}
