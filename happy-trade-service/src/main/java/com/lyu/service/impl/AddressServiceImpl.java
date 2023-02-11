package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.address.Area;
import com.lyu.entity.address.City;
import com.lyu.entity.address.Province;
import com.lyu.entity.address.Street;
import com.lyu.exception.UserException;
import com.lyu.mapper.AddressMapper;
import com.lyu.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/10 15:29
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    @Override
    public List<Province> getProvinceList() {
        if (StpUtil.isLogin()) {
            return addressMapper.getProvinceList();
        }
        throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
    }

    @Override
    public List<City> getCityListByProvince(String pCode) {
        if (StpUtil.isLogin()) {
            return addressMapper.getCityListByProvince(pCode);
        }
        throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());

    }

    @Override
    public List<Area> getAreaListByCityAndProvince(String pCode, String cCode) {
        if (StpUtil.isLogin()) {
            return addressMapper.getAreaListByCityAndProvince(pCode, cCode);
        }
        throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());

    }

    @Override
    public List<Street> getStreetListByAreaAndCityAndProvince(String pCode, String cCode, String aCode) {
        if (StpUtil.isLogin()) {
            return addressMapper.getStreetListByAreaAndCityAndProvince(pCode, cCode, aCode);
        }
        throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());

    }


}
