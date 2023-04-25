package com.lyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author LEE
 */
@EnableCaching
@ComponentScan(basePackages = {"cn.hutool.extra.spring", "com.lyu"})
@EnableOpenApi
@SpringBootApplication
public class HappyTradeSpringBootMain {
    public static void main(String[] args) {
        SpringApplication.run(HappyTradeSpringBootMain.class);
    }
}