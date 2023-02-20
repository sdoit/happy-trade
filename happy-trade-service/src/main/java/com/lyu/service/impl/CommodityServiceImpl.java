package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.Commodity;
import com.lyu.entity.CommodityType;
import com.lyu.entity.dto.CommodityDTO;
import com.lyu.exception.UserException;
import com.lyu.mapper.CommodityMapper;
import com.lyu.service.CommodityService;
import com.lyu.service.TagService;
import com.lyu.service.UserViewHistoryService;
import com.lyu.util.IDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    private UserViewHistoryService userViewHistoryService;
    @Resource
    private TagService tagService;

    @Resource
    private IDUtil idUtil;

    @Override
    public Long addCommodity(CommodityDTO commodity) {
        long uid = StpUtil.getLoginIdAsLong();
        commodity.setCid(idUtil.getNextCommodityId());
        commodity.setUid(uid);
        commodity.setSold(false);
        commodity.setLaunched(true);
        commodity.setTime(LocalDateTime.now());
        //先保存tag
        if (commodity.getTags().size() == 1) {
            commodity.setTags(List.of(tagService.insertTagIfNotExist(commodity.getTags().get(0))));
        } else if (commodity.getTags().size() > 1) {
            commodity.setTags(tagService.insertTagsIfNotExist(commodity.getTags()));
        }
        //映射
        tagService.mapCommodityAndTags(commodity.getTags(), commodity.getCid());
        commodityMapper.insert(commodity);
        return commodity.getCid();
    }

    @Override
    public Integer updateCommodity(CommodityDTO commodity) {
        Long uid = commodity.getUid();
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid != uidLogin) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        //删除tag
        tagService.cancelTagsNotIncludedAndAddIncluded(commodity.getCid(), commodity.getTags());


        return commodityMapper.updateById(commodity);
    }

    @Override
    public Integer deleteCommodityByCid(Long cid) {
        Commodity commodity = commodityMapper.selectById(cid);
        Long uid = commodity.getUid();
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid != uidLogin) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }

        return commodityMapper.deleteById(commodity.getCid());
    }

    @Override
    public CommodityDTO getCommodityById(Long cid) {
        CommodityDTO commodityDTO;
        //如果登录，保存浏览记录
        if (StpUtil.isLogin()) {
            commodityDTO = userViewHistoryService.saveViewHistory(cid);
        } else {
            commodityDTO = commodityMapper.getCommodityById(cid);
        }
        //设置typePath
        CommodityType commodityType = commodityDTO.getType();
        commodityType.setTypePath(new Integer[]{commodityDTO.getType().getTidRoot(),
                commodityDTO.getType().getTidMiddle(), commodityDTO.getType().getTid()});
        commodityDTO.setType(commodityType);
        return commodityDTO;
    }


    @Override
    public List<CommodityDTO> getCommoditiesFromUser(Page<CommodityDTO> page, Long uid) {
        return commodityMapper.getCommoditiesFromUser(page, uid).getRecords();
    }

    @Override
    public List<CommodityDTO> getCommoditiesByKeyWordsPage(String words, IPage<CommodityDTO> page) {
        String[] s = words.split(" ");
        IPage<CommodityDTO> commoditiesByKeyWords = commodityMapper.getCommoditiesByKeyWords(page, s);
        return commoditiesByKeyWords.getRecords();
    }

}
