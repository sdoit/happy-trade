package com.lyu.config;

import com.lyu.interceptor.CaptchaInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LEE
 * @time 2023/2/23 12:06
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${vue.address}")
    private String[] vueAddress;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CaptchaInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/sse/**")
                .excludePathPatterns("/api/type")
                .excludePathPatterns("/api/tag");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins(vueAddress)
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);

    }
}
