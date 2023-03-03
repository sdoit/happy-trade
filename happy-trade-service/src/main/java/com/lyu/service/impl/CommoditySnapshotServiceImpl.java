package com.lyu.service.impl;

import com.lyu.common.CodeAndMessage;
import com.lyu.entity.Commodity;
import com.lyu.entity.CommoditySnapshot;
import com.lyu.entity.dto.CommoditySnapshotDTO;
import com.lyu.exception.CommodityException;
import com.lyu.mapper.CommodityMapper;
import com.lyu.mapper.CommoditySnapshotMapper;
import com.lyu.service.CommoditySnapshotService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/2/26 10:09
 */
@Service
public class CommoditySnapshotServiceImpl implements CommoditySnapshotService {
    @Resource
    private CommoditySnapshotMapper commoditySnapshotMapper;
    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public Long createCommoditySnapshot(Long cid) {
        Commodity commodity = commodityMapper.selectById(cid);
        if (commodity == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        CommoditySnapshot commoditySnapshot = new CommoditySnapshot();
        commoditySnapshot.setCid(commodity.getCid());
        commoditySnapshot.setUid(commodity.getUid());
        commoditySnapshot.setName(commodity.getName());
        commoditySnapshot.setCover(commodity.getCover());
        commoditySnapshot.setQuality(commodity.getQuality());
        commoditySnapshot.setTypeId(commodity.getTypeId());
        commoditySnapshot.setPrice(commodity.getPrice());
        commoditySnapshot.setFare(commodity.getFare());
        commoditySnapshot.setFreightCollect(commodity.getFreightCollect());
        commoditySnapshot.setFreeShipping(commodity.getFreeShipping());
        commoditySnapshot.setDescription(commodity.getDescription());
        commoditySnapshot.setSold(commodity.getSold());
        commoditySnapshot.setLaunched(commodity.getLaunched());
        commoditySnapshot.setTime(commodity.getTime());
        commoditySnapshot.setViewCount(commodity.getViewCount());
        commoditySnapshotMapper.insert(commoditySnapshot);
        return commoditySnapshot.getSsid();
    }

    @Override
    public CommoditySnapshotDTO getCommoditySnapshotBySsid(Long ssid) {
        CommoditySnapshotDTO commoditySnapshotBySsid = commoditySnapshotMapper.getCommoditySnapshotBySsid(ssid);
        if (commoditySnapshotBySsid == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY_SNAPSHOT.getCode(), CodeAndMessage.WRONG_PASSWORD.getMessage());
        }
        return commoditySnapshotBySsid;
    }
}
