package com.lyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author LEE
 */
@EnableOpenApi
@SpringBootApplication
public class HappyTradeSpringBootMain {
    public static void main(String[] args) {
        SpringApplication.run(HappyTradeSpringBootMain.class);
    }
}