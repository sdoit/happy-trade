package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.entity.CommodityType;
import com.lyu.entity.Request;
import com.lyu.entity.dto.RequestDTO;
import com.lyu.exception.CommodityException;
import com.lyu.exception.UserException;
import com.lyu.mapper.RequestMapper;
import com.lyu.service.RequestService;
import com.lyu.util.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LEE
 * @time 2023/3/8 16:58
 */
@Slf4j
@Service
public class RequestServiceImpl implements RequestService {
    @Resource
    private RequestMapper requestMapper;

    @Resource
    private IDUtil idUtil;

    @Override
    public Long addRequest(RequestDTO request) {
        long uid = StpUtil.getLoginIdAsLong();
        request.setRid(idUtil.getNextCommodityId());
        request.setUid(uid);
        request.setTypeId(request.getType().getTid());
        request.setLaunched(true);
        request.setTime(LocalDateTime.now());
        return request.getRid();
    }

    @Override
    public Integer updateRequest(RequestDTO request) {
        Long uid = request.getUid();
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid != uidLogin) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }


        return requestMapper.updateById(request);
    }

    @Override
    public void takeDownRequest(Long id) {
        Request request = requestMapper.selectById(id);
        if (request == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        if (!request.getUid().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        if (!BooleanUtil.isTrue(request.getLaunched())) {
            throw new CommodityException(CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getCode(), CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getMessage());
        }
        request.setLaunched(false);
        requestMapper.updateById(request);
    }

    @Override
    public void uploadedRequest(Long id) {
        Request request = requestMapper.selectById(id);
        if (request == null) {
            throw new CommodityException(CodeAndMessage.NO_SUCH_COMMODITY.getCode(), CodeAndMessage.NO_SUCH_COMMODITY.getMessage());
        }
        if (!request.getUid().equals(StpUtil.getLoginIdAsLong())) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        if (BooleanUtil.isTrue(request.getLaunched())) {
            throw new CommodityException(CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getCode(), CodeAndMessage.COMMODITY_IS_ALREADY_OUT_THE_SHELF.getMessage());
        }
        request.setLaunched(true);
        requestMapper.updateById(request);

    }

    @Override
    public Integer deleteRequestById(Long id) {
        Request request = requestMapper.selectById(id);
        Long uid = request.getUid();
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid != uidLogin) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }

        return requestMapper.deleteById(request.getRid());
    }

    @Override
    public RequestDTO getRequestById(Long id) {
        RequestDTO requestDTO= requestMapper.getRequestById(id);;
        //设置typePath
        CommodityType commodityType = requestDTO.getType();
        commodityType.setTypePath(new Integer[]{requestDTO.getType().getTidRoot(),
                requestDTO.getType().getTidMiddle(), requestDTO.getType().getTid()});
        requestDTO.setType(commodityType);
        return requestDTO;
    }

    @Override
    public List<RequestDTO> getRequestsFromUser(Page<RequestDTO> page, Long uid) {
        return requestMapper.getRequestsFromUser(page, uid).getRecords();

    }

    @Override
    public List<RequestDTO> getRequestsByKeyWordsPage(String words, IPage<RequestDTO> page) {
        String[] s = words.split(" ");
        IPage<RequestDTO> requestsByKeyWords = requestMapper.getRequestsByKeyWords(page, s);
        return requestsByKeyWords.getRecords();
    }

    @Override
    public List<RequestDTO> getRequestsByType(IPage<RequestDTO> page, Integer type) {
        if (type == null) {
            throw new CommodityException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
        }
        IPage<RequestDTO> requestDTOIPage = requestMapper.getRequestsByType(page, type);
        return requestDTOIPage.getRecords();
    }

    @Override
    public List<RequestDTO> getRequestsRecommendations(IPage<RequestDTO> page) {
        IPage<RequestDTO> requestRecommendation = requestMapper.getRequestRecommend(page, StpUtil.getLoginIdAsLong());
        List<RequestDTO> records = requestRecommendation.getRecords();
        if (records.size() < Constant.COMMODITY_PER_PAGE) {
            //如果过一页都不够，添加最新商品
            List<RequestDTO> commoditiesLatest = getRequestsLatest(page);
            List<RequestDTO> commodityListResult = new ArrayList<>(Constant.COMMODITY_PER_PAGE);
            commodityListResult.addAll(records);
            commodityListResult.addAll(commoditiesLatest.subList(0, Constant.COMMODITY_PER_PAGE - records.size()));
            return commodityListResult;
        }
        return records;
    }

    @Override
    public List<RequestDTO> getRequestsLatest(IPage<RequestDTO> page) {
        return requestMapper.getRequestsLatest(page).getRecords();

    }
}
