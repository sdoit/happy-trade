package com.lyu.mapper;

import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.address.Area;
import com.lyu.entity.address.City;
import com.lyu.entity.address.Province;
import com.lyu.entity.address.Street;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 区别于 UserAddressMapper 本Mapper仅从数据库获取对应的地址列表
 *
 * @author LEE
 * @time 2023/2/10 15:15
 * @see UserAddressMapper
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
@Mapper
public interface AddressMapper {
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
