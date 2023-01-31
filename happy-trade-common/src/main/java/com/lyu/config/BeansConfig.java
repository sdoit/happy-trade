package com.lyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * @author LEE
 * @time 2023/1/29 20:23
 */
@Configuration
public class BeansConfig {
    @Bean(name = "dateTimeFormatterAliPay")
    public DateTimeFormatter getDateTimeFormatterAliPay() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Bean(name = "dateTimeFormatterUpload")
    public DateTimeFormatter getDateTimeFormatterUpload() {
        return DateTimeFormatter.ofPattern("yyyyMMdd");
    }
}
