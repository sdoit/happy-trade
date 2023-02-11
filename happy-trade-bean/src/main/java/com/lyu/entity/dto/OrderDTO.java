package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyu.entity.*;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/2/6 19:54
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO extends Order {
    private User user;
    private UserResource cover;
    private Commodity commodity;
    private UserAddress userAddress;
    private String statusName;

}
