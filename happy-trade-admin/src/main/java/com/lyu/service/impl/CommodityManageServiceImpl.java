package com.lyu.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.Commodity;
import com.lyu.exception.CommodityException;
import com.lyu.mapper.CommodityMapper;
import com.lyu.service.CommodityManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/4/10 17:04
 */
@Service
public class CommodityManageServiceImpl implements CommodityManageService {
    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public void getDownCommodityForce(Long cid, String reason) {
        Commodity commodity = commodityMapper.selectById(cid);
        if (commodity == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        if (BooleanUtil.isTrue(commodity.getForceDown())) {
            throw new CommodityException(CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getCode(), CodeAndMessage.COMMODITY_IS_ALREADY_ON_THE_SHELF.getMessage());
        }
        commodity.setLaunched(false);
        commodity.setForceDown(true);
        commodityMapper.updateById(commodity);
    }

    @Override
    public void deleteCommodityForce(Long cid, String reason) {
        Commodity commodity = commodityMapper.selectById(cid);
        if (commodity == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        if (BooleanUtil.isTrue(commodity.getForceDelete())) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        commodity.setLaunched(false);
        commodity.setForceDelete(true);
        commodityMapper.updateById(commodity);
    }

    @Override
    public void withdrawGetDownCommodityForce(Long cid) {
        Commodity commodity = commodityMapper.selectById(cid);
        if (commodity == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        commodity.setForceDelete(false);
        commodityMapper.updateById(commodity);
    }

    @Override
    public void withdrawDeleteCommodityForce(Long cid) {
        Commodity commodity = commodityMapper.selectById(cid);
        if (commodity == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        commodity.setForceDown(false);
        commodityMapper.updateById(commodity);
    }
}
