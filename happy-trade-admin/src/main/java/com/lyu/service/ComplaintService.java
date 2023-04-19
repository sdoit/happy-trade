package com.lyu.service;

import com.lyu.entity.Complaint;

import java.util.List;

/**
 * @author LEE
 * @time 2023/4/10 15:37
 */
public interface ComplaintService {
    /**
     * 提交申诉
     *
     * @param vno    违规编号
     * @param reason 申诉理由
     */
    void complaint(Long vno, String reason);

    /**
     * 获取还未处理的申诉
     *
     * @return
     */
    List<Complaint> getUnprocessedComplaints();

    /**
     * 申诉通过，进行处理
     *
     * @param cno
     */
    void complaintPass(Long cno);

    /**
     * 申诉不通过
     *
     * @param cno
     */
    void complaintRejected(Long cno);

    /**
     * 获取指定申诉
     *
     * @param cno
     * @return
     */
    Complaint getComplaintByCno(Long cno);
}
