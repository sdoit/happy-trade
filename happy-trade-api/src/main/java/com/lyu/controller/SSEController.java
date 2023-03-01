package com.lyu.controller;

import com.lyu.service.SseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/2/4 11:02
 */
@Validated
@RestController
@RequestMapping("/api/sse")
@CrossOrigin(origins = "${vue.address}")
@ApiOperation("SSE接口")
public class SSEController {
    @Resource
    private SseService sseService;

    @GetMapping("/connect")
    public SseEmitter connect() {
        return sseService.createSseConnect();
    }


}
