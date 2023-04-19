package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Message;
import com.lyu.entity.Complaint;
import com.lyu.entity.User;
import com.lyu.entity.Violation;
import com.lyu.exception.ComplaintException;
import com.lyu.exception.ViolationException;
import com.lyu.mapper.*;
import com.lyu.service.ComplaintService;
import com.lyu.service.UserMessageService;
import com.lyu.service.ViolationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/4/14 15:37
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Resource
    private ViolationMapper violationMapper;
    @Resource
    private ComplaintMapper complaintMapper;
    @Resource
    private ViolationService violationService;
    @Resource
    private UserMessageService userMessageService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private RequestMapper requestMapper;

    @Override
    public void complaint(Long vno, String reason) {
        Violation violation = violationMapper.selectById(vno);
        if (violation == null) {
            throw new ViolationException(CodeAndMessage.NO_SUCH_VIOLATION.getCode(), CodeAndMessage.NO_SUCH_VIOLATION.getMessage());
        }
        Complaint complaint = new Complaint();
        complaint.setVno(vno);
        complaint.setUid(StpUtil.getLoginIdAsLong());
        complaint.setReason(reason);
        complaintMapper.insert(complaint);
    }

    @Override
    public List<Complaint> getUnprocessedComplaints() {
        List<Complaint> complaints = complaintMapper.selectList(new QueryWrapper<Complaint>().isNull("withdrawal"));
        complaints.forEach(complaint -> {
            User user = userMapper.selectById(complaint.getUid());
            user.setPassword(null);
            complaint.setUser(user);
        });
        return complaints;
    }

    @Override
    public void complaintPass(Long cno) {
        Complaint complaint = complaintMapper.selectById(cno);
        if (complaint == null) {
            throw new ComplaintException(CodeAndMessage.NO_SUCH_COMPLAINT.getCode(), CodeAndMessage.NO_SUCH_COMPLAINT.getMessage());
        }
        violationService.withdraw(complaint.getVno());
        complaint.setReply(Message.COMPLAINT_PASS.getMessage());
        complaint.setWithdrawal(true);
        complaintMapper.updateById(complaint);
        //推送信息
        userMessageService.sendNotification(Message.COMPLAINT_PASS, null, complaint.getUid());
    }

    @Override
    public void complaintRejected(Long cno) {
        Complaint complaint = complaintMapper.selectById(cno);
        if (complaint == null) {
            throw new ComplaintException(CodeAndMessage.NO_SUCH_COMPLAINT.getCode(), CodeAndMessage.NO_SUCH_COMPLAINT.getMessage());
        }
        complaint.setReply(Message.COMPLAINT_FAILED.getMessage());
        complaint.setWithdrawal(false);
        complaintMapper.updateById(complaint);
        userMessageService.sendNotification(Message.COMPLAINT_PASS, null, complaint.getUid());
    }

    @Override
    public Complaint getComplaintByCno(Long cno) {
        Complaint complaint = complaintMapper.selectById(cno);
        if (complaint == null) {
            throw new ComplaintException(CodeAndMessage.NO_SUCH_COMPLAINT.getCode(), CodeAndMessage.NO_SUCH_COMPLAINT.getMessage());
        }
        Violation violation = violationMapper.selectById(complaint.getVno());
        User user = userMapper.selectById(violation.getUid());
        user.setPassword(null);
        violation.setUser(user);
        switch (violation.getTarget()) {
            case COMMODITY:
                violation.setCommodity(commodityMapper.selectById(violation.getTargetId()));
                break;
            case REQUEST:
                violation.setRequest(requestMapper.selectById(violation.getTargetId()));
                break;
            default:
                break;
        }
        complaint.setViolation(violation);
        return complaint;
    }
}
