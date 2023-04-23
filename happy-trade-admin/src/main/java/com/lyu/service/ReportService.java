package com.lyu.service;

import com.lyu.common.PenaltyAction;
import com.lyu.common.TargetType;
import com.lyu.entity.Report;

import java.util.List;

/**
 * @author LEE
 * @time 2023/4/10 15:23
 */
public interface ReportService {
    /**
     * 发起举报
     *
     * @param targetType 举报对象类型
     * @param id         举报对象id
     * @param uid       被举报主体人uid
     * @param reason     举报理由
     */
    void report(TargetType targetType, Long id, Long uid, String reason);

    /**
     * 举报成立，进行处罚
     *
     * @param rno
     * @param penaltyAction  处罚方式
     * @param duration       封禁时间【当且仅当处罚对象为账号时此参数才有效】
     * @param reason         处罚原因
     * @param complaintCount 可申诉次数
     */
    void reportPass(Long rno, PenaltyAction penaltyAction, Integer duration, String reason, Integer complaintCount) ;

    /**
     * 举报不通过
     *
     * @param rno
     * @param reply
     * @return 处罚记录
     */
    void reportReject(Long rno,String reply);


    /**
     * 获取未处理的举报
     *
     * @return
     */
    List<Report> getUnprocessedReport();

    /**
     * 根据举报编号获取report
     *
     * @param rno
     * @return
     */
    Report getReportByRno(Long rno);
}
