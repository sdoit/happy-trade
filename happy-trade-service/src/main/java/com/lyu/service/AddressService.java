package com.lyu.service;

import com.lyu.entity.address.Area;
import com.lyu.entity.address.City;
import com.lyu.entity.address.Province;
import com.lyu.entity.address.Street;

import java.util.List;

/**
 * 区别于 UserAddressService 此Service仅获取省市区列表
 * @author LEE
 * @time 2023/2/10 15:28
 * @see UserAddressService
 */
public interface AddressService {
    /**
     * 获取省列表
     *
     * @return
     */
    List<Province> getProvinceList();

    /**
     * 从省获取市列表
     *
     * @param pCode 省代码
     * @return
     */
    List<City> getCityListByProvince(String pCode);

    /**
     * 从市获取区列表
     *
     * @param pCode 省代码
     * @param cCode 市代码
     * @return
     */

    List<Area> getAreaListByCityAndProvince(String pCode, String cCode);

    /**
     * 从区获取街道列表
     *
     * @param pCode 省代码
     * @param cCode 市代码
     * @param aCode 区代码
     * @return
     */
    List<Street> getStreetListByAreaAndCityAndProvince(String pCode, String cCode, String aCode);
}
