package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Constant;
import com.lyu.common.Message;
import com.lyu.entity.CommodityType;
import com.lyu.entity.Request;
import com.lyu.entity.dto.CommodityDTO;
import com.lyu.entity.dto.RequestDTO;
import com.lyu.exception.CommodityException;
import com.lyu.exception.RequestException;
import com.lyu.exception.UserException;
import com.lyu.mapper.RequestMapper;
import com.lyu.service.CommodityService;
import com.lyu.service.RequestService;
import com.lyu.service.UserMessageService;
import com.lyu.util.IDUtil;
import com.lyu.util.aliyun.Sms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CommodityService commodityService;
    @Resource
    private Sms sms;
    @Resource
    private UserMessageService userMessageService;
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
        requestMapper.insert(request);
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
        //转换商品为普通商品
        commodityService.transferToGeneral(id);
        //通知此求购下的所有商品发布者
        List<CommodityDTO> commodities = commodityService.getCommodityForRequest(id);
        commodities.forEach(commodity -> {
            //发送站内通知
            userMessageService.sendNotification(Message.REQUEST_DELETED, "/commodity/" + commodity.getCid(), commodity.getUid());
            //发送短信通知
            sms.sendNotification(commodity.getUser().getPhone(), Sms.SmsNotifyType.RequestCancel);
        });
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
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteRequestById(Long id) {
        Request request = requestMapper.selectById(id);
        Long uid = request.getUid();
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid != uidLogin) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        //将求购下的所有商品转为普通商品
        commodityService.transferToGeneral(id);
        requestMapper.deleteById(request.getRid());
        //通知此求购下的所有商品发布者
        List<CommodityDTO> commodities = commodityService.getCommodityForRequest(id);
        commodities.forEach(commodity -> {
            //发送站内通知
            userMessageService.sendNotification(Message.REQUEST_DELETED, "/commodity/" + commodity.getCid(), commodity.getUid());
            //发送短信通知
            sms.sendNotification(commodity.getUser().getPhone(), Sms.SmsNotifyType.RequestCancel);
        });
        //发送短信通知
        return 1;
    }

    @Override
    public RequestDTO getRequestById(Long id) {
        RequestDTO requestDTO = requestMapper.getRequestById(id);
        if (requestDTO == null || requestDTO.getRid() == null) {
            throw new RequestException(CodeAndMessage.NO_SUCH_REQUEST.getCode(), CodeAndMessage.NO_SUCH_REQUEST.getMessage());
        }
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
        IPage<RequestDTO> requestPage = requestMapper.getRequestsByType(page, type);
        return requestPage.getRecords();
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
