package com.lyu.pool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author LEE
 * @time 2023/2/22 12:08
 */
@Configuration
public class SSEThreadPool {
    @Bean(name = "ssePoolTaskExecutor")
    public ScheduledThreadPoolExecutor executor() {
        ScheduledThreadPoolExecutor sseScheduledThreadPoll = new ScheduledThreadPoolExecutor(10, new BasicThreadFactory.
                Builder().namingPattern("sse-heartbeat-schedule-pool-%d").daemon(true).build());
        sseScheduledThreadPoll.setRemoveOnCancelPolicy(true);
        sseScheduledThreadPoll.setMaximumPoolSize(1000);
        return sseScheduledThreadPoll;
    }

}
