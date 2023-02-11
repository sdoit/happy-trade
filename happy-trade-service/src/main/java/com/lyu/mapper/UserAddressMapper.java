package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.entity.UserAddress;
import com.lyu.entity.dto.UserAddressDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LEE
 * @time 2023/2/2 19:57
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    /**
     * 获取用户的所有收货地址
     *
     * @param uid
     * @return
     */
    List<UserAddressDTO> getAddressListByUid(Long uid);

    /**
     * 根据aid获取addressDTO
     * @param aid
     * @return
     */
    UserAddressDTO getAddressByAid(Long aid);

    /**
     * 根据uid获取用户默认收货地址
     * @param uid
     * @return
     */
    UserAddressDTO getAddressDefaultByUid(Long uid);
}
