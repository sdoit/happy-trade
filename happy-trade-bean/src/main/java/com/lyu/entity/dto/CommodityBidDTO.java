package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/1/31 13:34
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommodityBidDTO {
    @NotNull
    private Long bid;
    @NotNull
    private String message;
}
