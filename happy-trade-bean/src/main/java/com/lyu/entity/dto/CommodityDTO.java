package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyu.entity.Commodity;
import com.lyu.entity.CommodityType;
import com.lyu.entity.Tag;
import com.lyu.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author LEE
 * @time 2023/1/29 15:35
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityDTO extends Commodity {
    private CommodityType type;

    /**
     * 卖家信息
     */
    private User user;


    /**
     * 商品标签
     */
    private List<Tag> tags;

}
