package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyu.entity.Commodity;
import com.lyu.entity.CommodityBid;
import lombok.Data;

/**
 * @author LEE
 * @time 2023/1/31 16:13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityBidUserDTO extends CommodityBid {

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    private String username;

    private Commodity commodity;

}
