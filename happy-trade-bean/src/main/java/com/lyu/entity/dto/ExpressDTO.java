package com.lyu.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/3/1 20:52
 */
@Data
public class ExpressDTO {
    @NotNull
    private Long oid;
    /**
     * 快递公司id
     */
    @NotNull
    private String expressId;
    private String expressName;
    /**
     * 运单号码
     */
    @NotNull
    private String shipId;

}
