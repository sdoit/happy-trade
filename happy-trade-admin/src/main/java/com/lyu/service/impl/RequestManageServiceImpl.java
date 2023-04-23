package com.lyu.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.Request;
import com.lyu.exception.RequestException;
import com.lyu.mapper.RequestMapper;
import com.lyu.service.RequestManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/4/10 19:35
 */
@Service
public class RequestManageServiceImpl implements RequestManageService {
    @Resource
    private RequestMapper requestMapper;
    @Override
    public void getDownRequestForce(Long rid, String reason) {
        Request request = requestMapper.selectById(rid);
        if (request == null) {
            throw new RequestException(CodeAndMessage.NO_SUCH_REQUEST.getCode(), CodeAndMessage.NO_SUCH_REQUEST.getMessage());
        }
        if (BooleanUtil.isTrue(request.getForceDown())) {
            throw new RequestException(CodeAndMessage.REQUEST_IS_ALREADY_OUT_THE_SHELF.getCode(), CodeAndMessage.REQUEST_IS_ALREADY_OUT_THE_SHELF.getMessage());
        }
        request.setForceDown(true);
        requestMapper.updateById(request);

    }
    @Override
    public void deleteRequestForce(Long rid, String reason) {
        Request request = requestMapper.selectById(rid);
        if (request == null) {
            throw new RequestException(CodeAndMessage.NO_SUCH_REQUEST.getCode(), CodeAndMessage.NO_SUCH_REQUEST.getMessage());
        }
        if (BooleanUtil.isTrue(request.getForceDelete())) {
            throw new RequestException(CodeAndMessage.NO_SUCH_REQUEST.getCode(), CodeAndMessage.NO_SUCH_REQUEST.getMessage());
        }
        request.setForceDown(true);
        request.setForceDown(true);
        requestMapper.updateById(request);
    }

    @Override
    public void withdrawGetDownRequestForce(Long rid) {
        Request request = requestMapper.selectById(rid);
        if (request == null) {
            throw new RequestException(CodeAndMessage.NO_SUCH_REQUEST.getCode(), CodeAndMessage.NO_SUCH_REPORT.getMessage());
        }
        request.setForceDown(false);
        requestMapper.updateById(request);

    }

    @Override
    public void withdrawDeleteRequestForce(Long rid) {
        Request request = requestMapper.selectById(rid);
        if (request == null) {
            throw new RequestException(CodeAndMessage.NO_SUCH_REQUEST.getCode(), CodeAndMessage.NO_SUCH_REPORT.getMessage());
        }
        request.setForceDelete(false);
        requestMapper.updateById(request);
    }
}
