package com.lyu.entity.dto;

import javax.validation.constraints.NotNull;

/**
 * @author LEE
 * @time 2023/4/22 20:51
 */
public class OrderReturnDTO  {
    @NotNull
    private Long oid;
    private String reason;
    private String rejectReason;
    private String agree;
    private Long aid;
    private String shipId;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    @Override
    public String toString() {
        return "OrderReturnDTO{" +
                "oid=" + oid +
                ", reason='" + reason + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", agree='" + agree + '\'' +
                ", aid=" + aid +
                ", shipId='" + shipId + '\'' +
                '}';
    }
}
