package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.lyu.common.ContentType;
import com.lyu.common.Message;
import com.lyu.common.SseConstant;
import com.lyu.exception.SSEException;
import com.lyu.service.SseService;
import com.lyu.service.UserMessageService;
import com.lyu.sse.SseSession;
import com.lyu.task.HeartBeatTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LEE
 * @time 2023/2/4 11:12
 */
@Slf4j
@Service
public class SseServiceImpl implements SseService {

    @Resource
    private ScheduledThreadPoolExecutor ssePoolTaskExecutor;
    @Lazy
    @Resource
    private UserMessageService userMessageService;

    @Override
    public SseEmitter createSseConnect() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        String clientId = StpUtil.getLoginIdAsString();
        SseEmitter sseEmitter = new SseEmitter(0L);
        final ScheduledFuture<?> future = ssePoolTaskExecutor.scheduleAtFixedRate(new HeartBeatTask(clientId), 0, SseConstant.SSE_HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
        sseEmitter.onCompletion(() -> {
            log.info("MSG: SseConnectCompletion | EmitterHash: {} |ID: {} | Date: {}", sseEmitter.hashCode(), clientId, LocalDateTime.now());
            SseSession.onCompletion(clientId, future);
        });
        sseEmitter.onTimeout(() -> {
            log.error("MSG: SseConnectTimeout | EmitterHash: {} |ID: {} | Date: {}", sseEmitter.hashCode(), clientId, LocalDateTime.now());
            SseSession.onError(clientId, new SSEException(0, "TimeOut(clientId: " + clientId + ")"));
        });
        sseEmitter.onError(t -> {
            log.error("MSG: SseConnectError | EmitterHash: {} |ID: {} | Date: {}", sseEmitter.hashCode(), clientId, LocalDateTime.now());
            SseSession.onError(clientId, new SSEException(0, "Error(clientId: " + clientId + ")"));
        });
        SseSession.add(clientId, sseEmitter);
        SseSession.send(clientId, "ping");
        log.info("创建新的sse连接，当前用户：{}", clientId);
        //推送未读的通知
        userMessageService.tryPushUnreadNotifications(Long.valueOf(clientId));
        return sseEmitter;
    }

    @Override
    public void closeSseConnect(String clientId) {
        SseSession.del(clientId);
    }


    @Override
    public void sendMsgToAllClients(Message message, String url) {
        for (String clientId : SseSession.getAllClient()) {
            sendMsgToClientByClientId(clientId, message, url);
        }
    }


    @Override
    public boolean sendMsgToClientByClientId(String clientId, Message message, String url) {
        return sendCustomMsgToClientByClientId(clientId, message.getId(), message.getNotifyType(), message.getTitle(), message.getMessage(), url, message.getType(), ContentType.TEXT);
    }

    @Override
    public boolean sendCustomMsgToClientByClientId(String clientId, String messageId, String type, String title, String message, String url, String flag, ContentType contentType) {
        if (!SseSession.exist(clientId)) {
            log.error("SseEmitterServiceImpl[sendMsgToClient]: 推送消息失败：客户端{}未创建长链接,失败消息:{}", clientId, message);
            return false;
        }

        SseEmitter.SseEventBuilder sendData = SseEmitter.event().data(StrUtil.emptyIfNull(type)).data(StrUtil.emptyIfNull(messageId)).
                data(StrUtil.emptyIfNull(title)).data(message, MediaType.APPLICATION_JSON).data(StrUtil.emptyIfNull(url)).data(StrUtil.emptyIfNull(flag)).data(contentType.getName());
        return SseSession.send(clientId, sendData);
    }

    @Override
    public void close(String clientId) {
        log.info("MSG: 关闭SSE连接 | ID: {} | Date: {}", clientId, LocalDateTime.now());
        SseSession.del(clientId);
    }


}
