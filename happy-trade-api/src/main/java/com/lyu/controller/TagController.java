package com.lyu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.Tag;
import com.lyu.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/14 19:43
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/tag")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("tag接口")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping("/{keyword}")
    public CommonResult<List<Tag>> getTagsLike(@PathVariable("keyword") @NotNull String keyword) {
        StpUtil.checkLogin();
        List<Tag> tagsLike = tagService.getTagsLike(keyword);
        return CommonResult.Result(CodeAndMessage.SUCCESS, tagsLike);
    }
}
