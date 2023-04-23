package com.lyu.entity;

import com.lyu.common.PenaltyAction;

/**
 * @author LEE
 * @time 2023/4/14 16:25
 */
public class ReportDTO {
    private Long rno;
    private PenaltyAction penaltyAction;
    private Integer duration;
    private Integer durationLevel;
    private String reason;
    private String reply;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getComplaintCount() {
        return complaintCount;
    }

    public void setComplaintCount(Integer complaintCount) {
        this.complaintCount = complaintCount;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getDurationLevel() {

        return durationLevel;
    }

    public void setDurationLevel(Integer durationLevel) {
        this.durationLevel = durationLevel;
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
