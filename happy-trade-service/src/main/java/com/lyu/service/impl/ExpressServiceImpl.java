package com.lyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.Express;
import com.lyu.exception.OrderException;
import com.lyu.mapper.ExpressMapper;
import com.lyu.service.ExpressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/3/1 20:43
 */
@Service
public class ExpressServiceImpl implements ExpressService {
    @Resource
    private ExpressMapper expressMapper;

    @Override
    public Express getExpressCompany(String id) {
        Express express = expressMapper.selectById(id);
        if(express==null){
            throw new OrderException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
        }
        return express;
    }

    @Override
    public List<Express> getExpressList() {
        return expressMapper.selectList(new QueryWrapper<>());
    }
}
