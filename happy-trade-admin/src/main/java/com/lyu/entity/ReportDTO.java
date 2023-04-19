package com.lyu.entity;

import com.lyu.common.PenaltyAction;
import com.lyu.common.reason.Reason;

/**
 * @author LEE
 * @time 2023/4/14 16:25
 */
public class ReportDTO {
    private Long rno;
    private PenaltyAction penaltyAction;
    private Integer duration;
    private Reason reason;
    private Integer complaintCount;

    public Long getRno() {
        return rno;
    }

    public void setRno(Long rno) {
        this.rno = rno;
    }

    public PenaltyAction getPenaltyAction() {
        return penaltyAction;
    }

    public void setPenaltyAction(PenaltyAction penaltyAction) {
        this.penaltyAction = penaltyAction;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Integer getComplaintCount() {
        return complaintCount;
    }

    public void setComplaintCount(Integer complaintCount) {
        this.complaintCount = complaintCount;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "rno='" + rno + '\'' +
                ", penaltyAction=" + penaltyAction +
                ", duration=" + duration +
                ", reason=" + reason +
                ", complaintCount=" + complaintCount +
                '}';
    }
}
