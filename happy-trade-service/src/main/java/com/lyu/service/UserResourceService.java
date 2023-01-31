package com.lyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyu.entity.UserResource;

/**
 * @author LEE
 * @time 2023/1/29 14:28
 */
public interface UserResourceService extends IService<UserResource> {
    /**
     * 文件上传到服务器后将文件信息保存到数据库
     * @param userResource
     * @return
     */
    Integer saveResource(UserResource userResource);

    /**
     *
     * @param userResource
     * @return
     */
    Integer deleteResource(UserResource userResource);



}
