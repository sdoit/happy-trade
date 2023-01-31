package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyu.entity.Commodity;
import com.lyu.entity.User;
import com.lyu.entity.dto.CommodityDTO;
import com.lyu.mapper.CommodityMapper;
import com.lyu.service.CommodityService;
import com.lyu.util.IDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2022/12/28 15:24
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private IDUtil idUtil;

    @Override
    public Integer addCommodity(Commodity commodity) {
        long uid = StpUtil.getLoginIdAsLong();
        commodity.setCid(idUtil.getNextCommodityId());
        commodity.setUid(uid);
        return commodityMapper.insert(commodity);
    }

    @Override
    public Integer updateCommodity(Commodity commodity) {
        return commodityMapper.updateById(commodity);
    }

    @Override
    public Integer deleteCommodity(Commodity commodity) {
        return commodityMapper.deleteById(commodity.getCid());
    }

    @Override
    public Commodity getCommodityById(Long cid) {
        return commodityMapper.getCommodityById(cid);
    }

    @Override
    public List<Commodity> getCommoditiesFromUser(User user) {
        return commodityMapper.selectList(new QueryWrapper<Commodity>()
                .eq("uid", user.getUid()));
    }

    @Override
    public List<CommodityDTO> getCommoditiesByKeyWordsPage(String words, IPage<CommodityDTO> page) {
        String[] s = words.split(" ");
//        QueryWrapper<Commodity> commodityKeyWordQueryWrapper = new QueryWrapper<>();
//        for (String keyword : s) {
//            commodityKeyWordQueryWrapper = commodityKeyWordQueryWrapper.like("name", keyword);
//        }
//        IPage<Commodity> pageResult = commodityMapper.selectPage(page, commodityKeyWordQueryWrapper);
        IPage<CommodityDTO> commoditiesByKeyWords = commodityMapper.getCommoditiesByKeyWords(page, s);
        return commoditiesByKeyWords.getRecords();
    }

}
