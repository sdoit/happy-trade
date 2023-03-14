package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.Commodity;
import com.lyu.entity.CommodityType;
import com.lyu.entity.dto.CommodityDTO;
import com.lyu.exception.CommodityException;
import com.lyu.exception.UserException;
import com.lyu.mapper.CommodityMapper;
import com.lyu.service.CommodityService;
import com.lyu.service.TagService;
import com.lyu.service.UserViewHistoryService;
import com.lyu.util.IDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        commodity.setTypeId(commodity.getType().getTid());
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
        //先保存tag
        if (commodity.getTags().size() == 1) {
            commodity.setTags(List.of(tagService.insertTagIfNotExist(commodity.getTags().get(0))));
        } else if (commodity.getTags().size() > 1) {
            commodity.setTags(tagService.insertTagsIfNotExist(commodity.getTags()));
        }
        //映射tag 并删除旧tags
        tagService.cancelTagsNotIncludedAndAddIncluded(commodity.getCid(), commodity.getTags());


        return commodityMapper.updateById(commodity);
    }

    @Override
    public void takeDownCommodity(Long cid) {
        Commodity commodity = commodityMapper.selectById(cid);
        if (commodity == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        if (!commodity.getUid().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        if (!BooleanUtil.isTrue(commodity.getLaunched())) {
            throw new CommodityException(CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getCode(), CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getMessage());
        }
        commodity.setLaunched(false);
        commodityMapper.updateById(commodity);
    }

    @Override
    public void uploadedCommodity(Long cid) {
        Commodity commodity = commodityMapper.selectById(cid);
        if (commodity == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        if (!commodity.getUid().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        if (BooleanUtil.isTrue(commodity.getLaunched())) {
            throw new CommodityException(CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getCode(), CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getMessage());
        }
        commodity.setLaunched(true);
        commodityMapper.updateById(commodity);
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
    public List<CommodityDTO> getCommodityForRequest(Long rid) {
        return commodityMapper.getCommodityForRequest(rid);

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

    @Override
    public List<CommodityDTO> getCommoditiesByType(IPage<CommodityDTO> page, Integer type) {
        if (type == null) {
            throw new CommodityException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
        }
        IPage<CommodityDTO> commoditiesByType = commodityMapper.getCommoditiesByType(page, type);
        return commoditiesByType.getRecords();
    }

    @Override
    public List<CommodityDTO> getCommoditiesRecommendations(IPage<CommodityDTO> page) {
//        long uidLogin = StpUtil.getLoginIdAsLong();
//        List<CommodityTypeViewCount> recentlyViewedCommodityType = commodityMapper.getRecentlyViewedCommodityType(uidLogin);
//        int count = 0;
//        for (CommodityTypeViewCount commodityTypeViewCount : recentlyViewedCommodityType) {
//            count += commodityTypeViewCount.getCount();
//        }
//        for (CommodityTypeViewCount commodityTypeViewCount : recentlyViewedCommodityType) {
//            commodityTypeViewCount.setRate((double) commodityTypeViewCount.getCount() / count);
//        }
        IPage<CommodityDTO> commodityRecommend = commodityMapper.getCommodityRecommend(page, StpUtil.getLoginIdAsLong());
        List<CommodityDTO> records = commodityRecommend.getRecords();
        if (records.size() < Constant.COMMODITY_PER_PAGE) {
            //如果过一页都不够，添加最新商品
            List<CommodityDTO> commoditiesLatest = getCommoditiesLatest(page);
            List<CommodityDTO> commodityListResult = new ArrayList<>(Constant.COMMODITY_PER_PAGE);
            commodityListResult.addAll(records);
            commodityListResult.addAll(commoditiesLatest.subList(0, Constant.COMMODITY_PER_PAGE - records.size()));
            return commodityListResult;
        }
        return records;
    }

    @Override
    public List<CommodityDTO> getCommoditiesLatest(IPage<CommodityDTO> page) {
        return commodityMapper.getCommoditiesLatest(page).getRecords();
    }

    @Override
    public List<CommodityType> getTypeRecommend() {
        if (StpUtil.isLogin()) {
            List<CommodityType> typeRecommend = commodityMapper.getTypeRecommend(StpUtil.getLoginIdAsLong(), LocalDateTime.now());
            if (typeRecommend.size() < Constant.HOME_RECOMMENDED_CATEGORY_COUNT) {
                //如果用户浏览过于单一，甚至5个品类也没有，就在后面追加上近期较热的商品分类
                List<CommodityType> typeHotRecent = commodityMapper.getTypeHotRecent(LocalDateTime.now());
                for (int i = 0; i < Constant.HOME_RECOMMENDED_CATEGORY_COUNT - typeRecommend.size(); i++) {
                    typeRecommend.add(typeHotRecent.get(i));
                }

            }
            return typeRecommend;
        }
        return commodityMapper.getTypeHotRecent(LocalDateTime.now());
    }

}
