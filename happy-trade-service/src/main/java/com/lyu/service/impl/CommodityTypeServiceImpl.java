package com.lyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.entity.CommodityType;
import com.lyu.mapper.CommodityMapper;
import com.lyu.mapper.CommodityTypeMapper;
import com.lyu.service.CommodityTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public List<CommodityType> getSubTypes(Integer tid) {
        return commodityTypeMapper.selectList(new QueryWrapper<CommodityType>().eq("p_tid", tid));
    }

    @Override
    public CommodityType getType(Integer tid) {
        return commodityTypeMapper.selectById(tid);
    }

    @Override
    public List<CommodityType> getRootTypes() {
        return commodityTypeMapper.selectList(new QueryWrapper<CommodityType>().eq("p_tid", 0));
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<CommodityType> getAllTypes() {
        List<CommodityType> rootTypes = this.getRootTypes();
        rootTypes.forEach(rootType -> {
            List<CommodityType> subTypes = this.getSubTypes(rootType.getTid());
            subTypes.forEach(subType -> {
                List<CommodityType> leafTypes = this.getSubTypes(subType.getTid());
                //填充图片
//                leafTypes.forEach(type -> {
//                    Commodity commodityForType;
//                        commodityForType = commodityMapper.selectOne(new QueryWrapper<Commodity>().eq("type_id", type.getTid()).last("limit 1"));
//                        if (commodityForType != null && commodityForType.getCover() != null) {
//                            type.setImg(commodityForType.getCover());
//                        }
//                });
                subType.setCommodityTypes(leafTypes);
            });
            rootType.setCommodityTypes(subTypes);
        });
        return rootTypes;
    }
}
