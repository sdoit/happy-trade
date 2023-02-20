package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyu.entity.Commodity;
import com.lyu.entity.CommodityBid;
import lombok.Data;

import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 16:13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityBidDTO extends Commodity {
    private List<CommodityBid> bids;

}
