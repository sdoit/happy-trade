package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Message;
import com.lyu.common.PenaltyAction;
import com.lyu.common.TargetType;
import com.lyu.entity.*;
import com.lyu.exception.ReportException;
import com.lyu.mapper.CommodityMapper;
import com.lyu.mapper.ReportMapper;
import com.lyu.mapper.RequestMapper;
import com.lyu.mapper.UserMapper;
import com.lyu.service.ReportService;
import com.lyu.service.UserMessageService;
import com.lyu.service.ViolationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/4/10 21:32
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    private ReportMapper reportMapper;
    @Resource
    private ViolationService violationService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserMessageService userMessageService;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private RequestMapper requestMapper;

    @Override
    public void report(TargetType targetType, Long id, Long uid, String reason) {
        Report report = new Report();
        report.setUid(StpUtil.getLoginIdAsLong());
        report.setTarget(targetType);
        report.setTargetId(id);
        report.setUidTarget(uid);
        report.setReason(reason);
        reportMapper.insert(report);

    }

    @Override
    public void reportPass(Long rno, PenaltyAction penaltyAction, Integer durationLevel, String reason, Integer complaintCount) {
        Report report = reportMapper.selectById(rno);
        User user = userMapper.selectById(report.getUidTarget());
        int duration = 0;
        if (durationLevel != null) {
            switch (durationLevel) {
                case 0:
                    duration = 24;
                    break;
                case 1:
                    duration = 24 * 7;
                    break;
                case 2:
                    duration = 24 * 7 * 2;
                    break;
                case 3:
                    duration = 24 * 30;
                    break;
                case 4:
                    duration = 24 * 30 * 3;
                    break;
                case 5:
                    duration = 24 * 30 * 6;
                    break;
                case 6:
                    duration = 24 * 365;
                    break;
                case 7:
                    duration = -1;
                    break;
                default:
                    break;
            }
        }
        report.setValid(true);
        reportMapper.updateById(report);
        Violation violation = violationService.dispose(report.getTarget(), report.getTargetId(), user.getUid(), penaltyAction, duration, reason, complaintCount);
        //通知举报人举报成功
        userMessageService.sendNotification(Message.REPORT_PASS, null, user.getUid());

    }

    @Override
    public void reportReject(Long rno, String reply) {
        Report report = reportMapper.selectById(rno);
        User user = userMapper.selectById(report.getUid());
        report.setValid(false);
        report.setReply(reply);
        reportMapper.updateById(report);
        //通知举报人举报失败
        userMessageService.sendNotification(Message.REPORT_FAILED, null, user.getUid());
    }

    @Override
    public List<Report> getUnprocessedReport() {
        List<Report> reports = reportMapper.selectList(new QueryWrapper<Report>().isNull("valid"));
        reports.forEach(report -> {
            switch (report.getTarget()) {
                case COMMODITY:
                    Commodity commodity = commodityMapper.selectById(report.getTargetId());
                    report.setCommodity(commodity);
                    break;
                case REQUEST:
                    Request request = requestMapper.selectById(report.getTargetId());
                    report.setRequest(request);
                    break;
                default:
                    break;
            }
            report.setUser(userMapper.selectById(report.getUid()));
            report.setUserTarget(userMapper.selectById(report.getUidTarget()));
        });
        return reports;
    }

    @Override
    public Report getReportByRno(Long rno) {
        Report report = reportMapper.selectById(rno);
        if (report == null) {
            throw new ReportException(CodeAndMessage.NO_SUCH_REPORT.getCode(), CodeAndMessage.NO_SUCH_REPORT.getMessage());
        }
        switch (report.getTarget()) {
            case COMMODITY:
                Commodity commodity = commodityMapper.selectById(report.getTargetId());
                report.setCommodity(commodity);
                break;
            case REQUEST:
                Request request = requestMapper.selectById(report.getTargetId());
                report.setRequest(request);
                break;
            default:
                break;
        }
        report.setUser(userMapper.selectById(report.getUid()));
        report.setUserTarget(userMapper.selectById(report.getUidTarget()));
        return report;
    }
}
