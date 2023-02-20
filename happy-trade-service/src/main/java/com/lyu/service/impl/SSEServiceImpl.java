package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.lyu.common.CodeAndMessage;
import com.lyu.common.Message;
import com.lyu.exception.SSEException;
import com.lyu.service.SSEService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author LEE
 * @time 2023/2/4 11:12
 */
@Slf4j
@Service
public class SSEServiceImpl implements SSEService {

    /**
     * 容器，保存连接，用于输出返回
     */
    private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    @Override
    public SseEmitter createSseConnect() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        String clientId = StpUtil.getLoginIdAsString();
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
        SseEmitter sseEmitter = new SseEmitter(0L);
        // 注册回调
        sseEmitter.onCompletion(completionCallBack(clientId));
        sseEmitter.onTimeout(timeoutCallBack(clientId));
        sseEmitter.onError(errorCallBack(clientId));
        sseEmitterMap.put(clientId, sseEmitter);
        log.info("创建新的sse连接，当前用户：{}", clientId);
        try {
            sseEmitter.send(SseEmitter.event().id("0").data(clientId));
        } catch (IOException e) {
            log.error("SseEmitterServiceImpl[createSseConnect]: 创建长链接异常，客户端ID:{}", clientId, e);
            throw new SSEException(CodeAndMessage.SSE_WENT_WRONG.getCode(), e.getMessage());
        }

        return sseEmitter;
    }

    @Override
    public void closeSseConnect(String clientId) {
        SseEmitter sseEmitter = sseEmitterMap.get(clientId);
        if (sseEmitter != null) {
            sseEmitter.complete();
            removeUser(clientId);
        }
    }

    @Override
    public SseEmitter getSseEmitterByClientId(String clientId) {
        return sseEmitterMap.get(clientId);
    }

    @Override
    public void sendMsgToAllClients(Message message, String url) {
        if (CollectionUtil.isEmpty(sseEmitterMap)) {
            return;
        }
        for (Map.Entry<String, SseEmitter> entry : sseEmitterMap.entrySet()) {
            sendMsgToClientByClientId(entry.getKey(), message, url);
        }
    }


    @Override
    public void sendMsgToClientByClientId(String clientId, Message message, String url) {
        sendCustomMsgToClientByClientId(clientId, message.getId(), message.getType(), message.getTitle(), message.getMessage(), url);
    }

    @Override
    public void sendCustomMsgToClientByClientId(String clientId, String messageId, String type, String title, String message, String url) {
        if (!sseEmitterMap.containsKey(clientId)) {
            log.error("SseEmitterServiceImpl[sendMsgToClient]: 推送消息失败：客户端{}未创建长链接,失败消息:{}",
                    clientId, message);
            return;
        }

        SseEmitter.SseEventBuilder sendData = SseEmitter.event().id(messageId).data(type).data(title).data(message, MediaType.APPLICATION_JSON).data(url);
        SseEmitter sseEmitter = sseEmitterMap.get(clientId);
        try {
            sseEmitter.send(sendData);
        } catch (IOException e) {
            // 推送消息失败，记录错误日志，进行重推
            log.error("SseEmitterServiceImpl[sendMsgToClient]: 推送消息失败：{},尝试进行重推", message, e);
            boolean isSuccess = true;
            // 推送消息失败后，每隔10s推送一次，推送5次
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10000);
                    sseEmitter = sseEmitterMap.get(clientId);
                    if (sseEmitter == null) {
                        log.error("SseEmitterServiceImpl[sendMsgToClient]：{}的第{}次消息重推失败，未创建长链接", clientId, i + 1);
                        continue;
                    }
                    sseEmitter.send(sendData);
                    log.info("SseEmitterServiceImpl[sendMsgToClient]：{}的第{}次消息重推成功,{}", clientId, i + 1, message.toString());
                    return;
                } catch (Exception ex) {
                    log.error("SseEmitterServiceImpl[sendMsgToClient]：{}的第{}次消息重推失败", clientId, i + 1, ex);
                    continue;
                }
            }
        }
    }

    /**
     * 长链接完成后回调接口(即关闭连接时调用)
     *
     * @param clientId 客户端ID
     * @return java.lang.Runnable
     * @author re
     * @date 2021/12/14
     **/
    private Runnable completionCallBack(String clientId) {
        return () -> {
            log.info("结束连接：{}", clientId);
            removeUser(clientId);
        };
    }

    /**
     * 连接超时时调用
     *
     * @param clientId 客户端ID
     * @return java.lang.Runnable
     * @author re
     * @date 2021/12/14
     **/
    private Runnable timeoutCallBack(String clientId) {
        return () -> {
            log.info("连接超时：{}", clientId);
            removeUser(clientId);
        };
    }

    /**
     * 推送消息异常时，回调方法
     *
     * @param clientId 客户端ID
     * @return java.util.function.Consumer<java.lang.Throwable>
     * @author re
     * @date 2021/12/14
     **/
    private Consumer<Throwable> errorCallBack(String clientId) {
        return throwable -> {
            log.error("SseEmitterServiceImpl[errorCallBack]：连接异常,客户端ID:{}", clientId);

            // 推送消息失败后，每隔10s推送一次，推送5次
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10000);
                    SseEmitter sseEmitter = sseEmitterMap.get(clientId);
                    if (sseEmitter == null) {
                        log.error("SseEmitterServiceImpl[errorCallBack]：第{}次消息重推失败,未获取到 {} 对应的长链接", i + 1, clientId);
                        continue;
                    }
                    sseEmitter.send("失败后重新推送");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 移除用户连接
     *
     * @param clientId 客户端ID
     * @author re
     * @date 2021/12/14
     **/
    private void removeUser(String clientId) {
        sseEmitterMap.remove(clientId);
        log.info("SseEmitterServiceImpl[removeUser]:移除用户：{}", clientId);
    }
}
