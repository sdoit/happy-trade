package com.lyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.entity.CommodityType;
import com.lyu.mapper.CommodityTypeMapper;
import com.lyu.service.CommodityTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/15 14:00
 */
@Slf4j
@Service
public class CommodityTypeServiceImpl implements CommodityTypeService {
    @Resource
    private CommodityTypeMapper commodityTypeMapper;

    @Override
    public List<CommodityType> getSubTypes(Integer tid) {
        return commodityTypeMapper.selectList(new QueryWrapper<CommodityType>().eq("p_tid", tid));
    }

    @Override
    public List<CommodityType> getRootTypes() {
        return commodityTypeMapper.selectList(new QueryWrapper<CommodityType>().eq("p_tid", 0));
    }
}
