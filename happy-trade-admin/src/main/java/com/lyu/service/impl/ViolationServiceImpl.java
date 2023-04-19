package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Message;
import com.lyu.common.PenaltyAction;
import com.lyu.common.TargetType;
import com.lyu.common.reason.*;
import com.lyu.entity.User;
import com.lyu.entity.Violation;
import com.lyu.exception.ViolationException;
import com.lyu.mapper.UserMapper;
import com.lyu.mapper.ViolationMapper;
import com.lyu.service.*;
import com.lyu.util.aliyun.Sms;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LEE
 * @time 2023/4/10 19:53
 */
@Service
public class ViolationServiceImpl implements ViolationService {
    @Resource
    private CommodityManageService commodityManageService;
    @Resource
    private RequestManageService requestManageService;
    @Resource
    private UserManageService userManageService;
    @Resource
    private ViolationMapper violationMapper;
    @Resource
    private UserMessageService userMessageService;
    @Resource
    private Sms sms;
    @Resource
    private UserMapper userMapper;

    @Override
    public Violation dispose(TargetType targetType, Long id, Long uid, PenaltyAction penaltyAction, Integer banDuration, String reason, Integer complaintCount) throws ViolationException {
        StpUtil.hasRole("admin");
        Message message;
        //违规对象引导url
        String url = "";
        switch (targetType) {
            case COMMODITY:
                if (penaltyAction == PenaltyAction.DOWN) {
                    commodityManageService.getDownCommodityForce(id, GetDownCommodityReason.valueOf(reason));
                    message = Message.COMMODITY_ARE_FORCED_DOWN;
                } else if (penaltyAction == PenaltyAction.DELETE) {
                    commodityManageService.deleteCommodityForce(id, DeleteCommodityReason.valueOf(reason));
                    message = Message.COMMODITY_ARE_FORCED_DELETE;
                } else {
                    //异常
                    throw new ViolationException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
                }
                url = "";
                break;
            case REQUEST:
                if (penaltyAction == PenaltyAction.DOWN) {
                    requestManageService.getDownRequestForce(id, GetDownRequestReason.valueOf(reason));
                    message = Message.REQUEST_ARE_FORCED_DOWN;
                } else if (penaltyAction == PenaltyAction.DELETE) {
                    requestManageService.deleteRequestForce(id, DeleteRequestReason.valueOf(reason));
                    message = Message.REQUEST_ARE_FORCED_DELETE;
                } else {
                    //异常
                    throw new ViolationException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
                }
                break;
            case USER:
                if (penaltyAction == PenaltyAction.BAN) {
                    userManageService.banUser(uid, banDuration, BanUserReason.valueOf(reason));
                } else {
                    throw new ViolationException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
                }
            default:
                throw new ViolationException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
        }
        User user = userMapper.selectById(uid);
        //保存触发记录
        Violation violation = new Violation();
        violation.setAction(penaltyAction);
        violation.setTarget(targetType);
        violation.setReason(reason);
        violation.setUid(uid);
        violation.setComplaintCount(complaintCount);
        violation.setTargetId(id);
        violation.setTime(LocalDateTime.now());
        violationMapper.insert(violation);
        //推送消息
        userMessageService.sendNotification(message, url, uid);
        sms.sendNotification(user.getPhone(), Sms.SmsNotifyType.newViolation);
        return violation;
    }

    @Override
    public void withdraw(Long vno) {
        Violation violation = violationMapper.selectById(vno);
        if (violation == null) {
            throw new ViolationException(CodeAndMessage.NO_SUCH_VIOLATION.getCode(), CodeAndMessage.NO_SUCH_VIOLATION.getMessage());
        }
        TargetType target = violation.getTarget();
        switch (target) {
            case USER:
                //解封用户
                userManageService.unBanUser(violation.getUid());
                break;
            case COMMODITY:
                if (PenaltyAction.DOWN == violation.getAction()) {
                    //重新上架
                    commodityManageService.withdrawGetDownCommodityForce(violation.getTargetId());
                } else if (PenaltyAction.DELETE == violation.getAction()) {
                    commodityManageService.withdrawDeleteCommodityForce(violation.getTargetId());
                } else {
                    throw new ViolationException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
                }

            case REQUEST:
                if (PenaltyAction.DOWN == violation.getAction()) {
                    //重新上架
                    requestManageService.withdrawGetDownRequestForce(violation.getTargetId());
                } else if (PenaltyAction.DELETE == violation.getAction()) {
                    requestManageService.withdrawGetDownRequestForce(violation.getTargetId());
                } else {
                    throw new ViolationException(CodeAndMessage.WRONG_REQUEST_PARAMETER.getCode(), CodeAndMessage.WRONG_REQUEST_PARAMETER.getMessage());
                }
            default:
                break;
        }
        User user = userMapper.selectById(violation.getUid());
        userMessageService.sendNotification(Message.PENALTY_WAS_REVOKED, null, user.getUid());
        sms.sendNotification(user.getPhone(), Sms.SmsNotifyType.newViolation);
    }

    @Override
    public List<Violation> getAllViolation() {
        List<Violation> violations = violationMapper.selectList(new QueryWrapper<Violation>().last("where 1=1"));
        return violations;
    }
}
