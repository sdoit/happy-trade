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
    private UserWithRatingDTO user;
    private String cover;
    private Commodity commodity;
    private UserAddressDTO userAddress;
    private String statusName;

    private ExpressDTO express;

    /**
     * 买家对卖家的评价
     */
    private OrderRating orderRatingToSeller;
    /**
     *  卖家对买家的评价
     */
    private OrderRating orderRatingToBuyer;

}
