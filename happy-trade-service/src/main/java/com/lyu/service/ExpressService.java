package com.lyu.service;

import com.lyu.entity.Express;

import java.util.List;

/**
 * @author LEE
 * @time 2023/3/1 20:41
 */
public interface ExpressService {
    /**
     * 获取物流公司
     *
     * @param id
     * @return
     */
    Express getExpressCompany(String id);

    /**
     * 获取所有快递公司
     *
     * @return
     */
    List<Express> getExpressList();
}
