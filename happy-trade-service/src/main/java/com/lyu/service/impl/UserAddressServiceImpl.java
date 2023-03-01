package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.entity.UserAddress;
import com.lyu.entity.dto.UserAddressDTO;
import com.lyu.exception.GlobalException;
import com.lyu.exception.UserException;
import com.lyu.mapper.UserAddressMapper;
import com.lyu.service.UserAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/2 20:02
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddressDTO> getUserAddressByUid(Long uid) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uidLogin != uid) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }

        return userAddressMapper.getAddressListByUid(uid);
    }

    @Override
    public UserAddressDTO getUserAddressByAid(Long aid) {
        UserAddressDTO userAddress = userAddressMapper.getAddressByAid(aid);
        if (userAddress != null && userAddress.getUid() != StpUtil.getLoginIdAsLong()) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());

        }
        return userAddress;
    }

    @Override
    public UserAddressDTO getDefaultUserAddressByUid(Long uid) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (uid != uidLogin) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        return userAddressMapper.getAddressDefaultByUid(uid);
    }

    @Override
    public Integer saveUserAddress(UserAddress userAddress) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        userAddress.setUid(uidLogin);
        return userAddressMapper.insert(userAddress);
    }

    @Override
    public Integer updateUserAddress(UserAddress userAddress) {
        long uidLogin = StpUtil.getLoginIdAsLong();
        if (userAddress.getAid() == null) {
            throw new GlobalException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
        }
        userAddress.setUid(uidLogin);
        return userAddressMapper.updateById(userAddress);
    }

    @Override
    public Integer deleteUserAddress(Long aid) {
        UserAddress userAddress = userAddressMapper.selectById(aid);
        if (userAddress.getUid() != StpUtil.getLoginIdAsLong()) {
            throw new UserException(CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getCode(), CodeAndMessage.ACTIONS_WITHOUT_ACCESS.getMessage());
        }
        return userAddressMapper.update(null, new UpdateWrapper<UserAddress>().set("del", true).eq("aid",aid));
    }
}
