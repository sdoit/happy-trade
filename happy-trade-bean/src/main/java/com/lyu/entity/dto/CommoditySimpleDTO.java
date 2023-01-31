package com.lyu.entity.dto;

import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/1/12 9:56
 */
public class CommoditySimpleDTO {
    @NotNull
    private Long cid;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "CommodityDTO{" +
                "cid=" + cid +
                '}';
    }
}
