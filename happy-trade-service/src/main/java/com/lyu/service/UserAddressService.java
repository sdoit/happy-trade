package com.lyu.service;

import com.lyu.entity.UserAddress;
import com.lyu.entity.dto.UserAddressDTO;

import java.util.List;

/**
 * @author LEE
 * @time 2023/2/2 19:58
 */

public interface UserAddressService {
    /**
     * 根据用户id获取所有的地址
     *
     * @param uid
     * @return
     */
    List<UserAddressDTO> getUserAddressByUid(Long uid);

    /**
     * 根据地址id获取地址对象
     *
     * @param aid
     * @return
     */
    UserAddressDTO getUserAddressByAid(Long aid);

    /**
     * 获取用户的默认收货地址
     *
     * @param uid
     * @return
     */
    UserAddressDTO getDefaultUserAddressByUid(Long uid);


    /**
     * 保存UserAddress到数据库
     *
     * @param userAddress
     * @return
     */
    Integer saveUserAddress(UserAddress userAddress);
    /**
     * 保存UserAddress到数据库
     *
     * @param userAddress
     * @return
     */
    Integer updateUserAddress(UserAddress userAddress);

    /**
     * 删除
     *
     * @param aid
     * @return
     */
    Integer deleteUserAddress(Long aid);
}
