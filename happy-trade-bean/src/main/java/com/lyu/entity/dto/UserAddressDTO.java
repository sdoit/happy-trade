package com.lyu.entity.dto;

import com.lyu.entity.UserAddress;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/10 13:33
 */
@Data
public class UserAddressDTO extends UserAddress {
    private String province;
    private String city;
    private String area;
    private String street;
}
