package com.lyu.task;

import com.lyu.sse.SseSession;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author LEE
 * @time 2023/2/22 21:18
 */
@Slf4j
public class HeartBeatTask implements Runnable {

    private final String clientId;

    public HeartBeatTask(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void run() {
        log.info("MSG: SseHeartbeat | ID: {} | Date: {}", clientId, LocalDateTime.now());
        SseSession.send(clientId, "ping");
    }
}