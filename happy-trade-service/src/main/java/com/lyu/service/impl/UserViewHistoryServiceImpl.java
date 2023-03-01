package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.UserViewHistory;
import com.lyu.entity.dto.CommodityDTO;
import com.lyu.entity.dto.UserViewHistoryDTO;
import com.lyu.exception.CommodityException;
import com.lyu.exception.UserException;
import com.lyu.mapper.CommodityMapper;
import com.lyu.mapper.UserViewHistoryMapper;
import com.lyu.service.UserViewHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 19:34
 */

@Service
public class UserViewHistoryServiceImpl implements UserViewHistoryService {
    @Resource
    private UserViewHistoryMapper userViewHistoryMapper;
    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public CommodityDTO saveViewHistory(Long cid) {
        long uid = StpUtil.getLoginIdAsLong();
        CommodityDTO commodity = commodityMapper.getCommodityById(cid);
        if (commodity == null || commodity.getCid()==null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        UserViewHistory userViewHistory = new UserViewHistory();
        userViewHistory.setUid(uid);
        userViewHistory.setCid(cid);
        userViewHistory.setTime(LocalDateTime.now());
        Boolean exist = this.existViewHistory(userViewHistory);
        if (!BooleanUtil.isTrue(exist)) {
            commodity.setViewCount(commodity.getViewCount() + 1);
            commodityMapper.updateById(commodity);
        }
        userViewHistoryMapper.insertOrUpdate(userViewHistory);
        return commodity;
    }

    @Override
    public List<UserViewHistoryDTO> getAllViewHistoryByUid(Page<UserViewHistoryDTO> page, Long uid) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid != uidLogin) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        IPage<UserViewHistoryDTO> allViewHistory = userViewHistoryMapper.getAllViewHistoryByUid(page, uid);
        return allViewHistory.getRecords();
    }

    @Override
    public Boolean existViewHistory(UserViewHistory userViewHistory) {
        Long uid = userViewHistory.getUid();
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid != uidLogin) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        return userViewHistoryMapper.exists(userViewHistory);
    }
}
