package com.lyu.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.Complaint;
import com.lyu.entity.Report;
import com.lyu.service.ComplaintService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/4/14 16:31
 */
@Validated
@RestController
@RequestMapping("/admin/complaint")
@ApiOperation("申诉操作接口")
public class ComplaintController {

    @Resource
    private ComplaintService complaintService;

    @PostMapping
    public CommonResult<Object> complaint(@NotNull @RequestBody Report report) {
        complaintService.complaint(report.getVno(), report.getReason());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
    @SaCheckRole("admin")
    @GetMapping("/unprocessed")
    public CommonResult<List<Complaint>> getUnprocessedComplaints() {
        List<Complaint> unprocessedComplaints = complaintService.getUnprocessedComplaints();
        return CommonResult.Result(CodeAndMessage.SUCCESS, unprocessedComplaints);

    }
    @SaCheckRole("admin")
    @PostMapping("/pass/{cno}")
    public CommonResult<Object> complaintPass(@PathVariable("cno") @NotNull Long cno) {
        complaintService.complaintPass(cno);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);

    }
    @SaCheckRole("admin")
    @PostMapping("/rejected/{cno}")
    public CommonResult<Object> complaintRejected(@PathVariable("cno") @NotNull Long cno) {
        complaintService.complaintRejected(cno);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
    @SaCheckRole("admin")
    @GetMapping("/{cno}")
    public CommonResult<Complaint> getComplaint(@PathVariable("cno") Long cno) {
        Complaint complaint = complaintService.getComplaintByCno(cno);
        return CommonResult.Result(CodeAndMessage.SUCCESS, complaint);

    }
}
