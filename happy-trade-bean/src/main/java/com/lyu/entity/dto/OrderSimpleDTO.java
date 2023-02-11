package com.lyu.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/1/12 9:56
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class OrderSimpleDTO {
    @NotNull
    private Long cid;
    @NotNull
    private Long aid;


}
