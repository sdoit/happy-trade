package com.lyu.service;

import com.lyu.common.PenaltyAction;
import com.lyu.common.TargetType;
import com.lyu.entity.Violation;
import com.lyu.exception.ViolationException;

import java.util.List;

/**
 * @author LEE
 * @time 2023/4/10 10:23
 */
public interface ViolationService {
    /**
     * 处理违规并添加到违规记录
     *
     * @param targetType     处理对象
     * @param id             处理对象id
     * @param uid           主体人
     * @param penaltyAction  处理方式
     * @param banDuration    封号时长(h)
     * @param reason         处理原因
     * @param complaintCount 可用申诉次数
     * @throws ViolationException
     * @return 处罚信息
     */
    Violation dispose(TargetType targetType, Long id, Long uid, PenaltyAction penaltyAction, Integer banDuration,
                      String reason, Integer complaintCount) throws ViolationException;

    /**
     * 撤销处理
     *
     * @param vno 违规编号
     */
    void withdraw(Long vno);

    /**
     * 获取全部违规记录
     * @return
     */
    List<Violation> getAllViolation();

    /**
     * 获取指定违规记录
     * @return
     */
    Violation getViolationByVno(Long vno);
}
