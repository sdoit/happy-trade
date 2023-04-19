package com.lyu.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.Report;
import com.lyu.entity.ReportDTO;
import com.lyu.service.ReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/4/14 16:15
 */
@Validated
@RestController
@RequestMapping("/admin/report")
@ApiOperation("举报记录操作接口")
public class ReportController {
    @Resource
    private ReportService reportService;

    @PostMapping
    public CommonResult<Object> report(@RequestBody @NotNull Report report) {
        reportService.report(report.getTarget(), report.getTargetId(), report.getUid(), report.getReason());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @SaCheckRole("admin")
    @PostMapping("/pass")
    public CommonResult<Object> reportPass(@RequestBody ReportDTO report) {
        reportService.reportPass(report.getRno(), report.getPenaltyAction(), report.getDuration(), report.getReason(), report.getComplaintCount());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @SaCheckRole("admin")
    @PostMapping("/reject/{rno}")
    public CommonResult<Object> reportReject(@PathVariable("rno") @NotNull Long rno) {
        reportService.reportReject(rno);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }
    @SaCheckRole("admin")
    @GetMapping
    public CommonResult<List<Report>> getUnprocessedReport() {
        List<Report> unprocessedReport = reportService.getUnprocessedReport();
        return CommonResult.Result(CodeAndMessage.SUCCESS, unprocessedReport);
    }
    @SaCheckRole("admin")
    @GetMapping("/{rno}")
    public CommonResult<Report> getReportByRno(@PathVariable("rno") Long rno) {
        Report report = reportService.getReportByRno(rno);
        return CommonResult.Result(CodeAndMessage.SUCCESS, report);

    }

}
