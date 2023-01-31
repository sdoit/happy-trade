package com.lyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyu.entity.UserResource;
import com.lyu.mapper.UserResourceMapper;
import com.lyu.service.UserResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/1/29 14:50
 */
@Slf4j
@Service
public class UserResourceServiceImpl extends ServiceImpl<UserResourceMapper,UserResource> implements UserResourceService {
    @Resource
    private UserResourceMapper userResourceMapper;

    @Override
    public Integer saveResource(UserResource userResource) {

        return userResourceMapper.insert(userResource);
    }

    @Override
    public Integer deleteResource(UserResource userResource) {
        return userResourceMapper.deleteById(userResource.getRid());
    }

//    @Override
//    public Integer saveResourceBatch(List<UserResource> userResource) {
//        return userResourceMapper.InsertBatchSomeColumn();
//    }
}
