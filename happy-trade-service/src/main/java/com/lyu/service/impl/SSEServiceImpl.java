package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.lyu.common.Message;
import com.lyu.service.SSEService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LEE
 * @time 2023/2/4 11:12
 */
@Slf4j
@Service
public class SSEServiceImpl implements SSEService {
    private static final Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    @Override
    public SseEmitter addEmitter() {
        String uid = StpUtil.getLoginIdAsString();


        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onCompletion(() -> sseEmitterMap.remove(uid));
        sseEmitter.onTimeout(() -> sseEmitterMap.remove(uid));
        sseEmitter.onError((e) -> log.error(e.toString()));
        sseEmitterMap.put(uid, sseEmitter);
        return sseEmitter;

    }

    @Override
    public void sendMessage(String uid, String messageId, String message, String url) {
        if (StrUtil.isBlank(uid)) {
            uid = StpUtil.getLoginIdAsString();
        }
        if (sseEmitterMap.containsKey(uid)) {
            SseEmitter sseEmitter = sseEmitterMap.get(uid);
            try {
                log.debug("发送消息=>" + uid + "=>" + messageId + "=>" + message);
                sseEmitter.send(SseEmitter.event().id(String.valueOf(messageId)).data(message).data(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(String uid, Message message, String url) {
        sendMessage(uid, message.getId(), message.getMessage(), url);
    }


}
