package com.lyu.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.Violation;
import com.lyu.service.ViolationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/4/14 15:57
 */
@Validated
@RestController
@RequestMapping("/admin/violation")
@ApiOperation("违规记录操作接口")
public class ViolationController {
    @Resource
    private ViolationService violationService;

    @SaCheckRole("admin")
    @PostMapping("/dispose")
    public CommonResult<Object> dispose(@RequestBody @NotNull Violation violation) {
        violationService.dispose(
                violation.getTarget(),
                violation.getTargetId(),
                violation.getUid(),
                violation.getAction(),
                violation.getDuration(),
                violation.getReason(),
                violation.getComplaintCount());
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @SaCheckRole("admin")
    @PostMapping("/withdraw/{vno}")
    public CommonResult<Object> withdraw(@NotNull @PathVariable("vno") Long vno) {
        violationService.withdraw(vno);
        return CommonResult.Result(CodeAndMessage.SUCCESS, null);
    }

    @SaCheckRole("admin")
    @GetMapping
    public CommonResult<List<Violation>> getViolations() {
        List<Violation> allViolation = violationService.getAllViolation();
        return CommonResult.Result(CodeAndMessage.SUCCESS, allViolation);
    }
}
