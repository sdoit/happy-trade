package com.lyu.config;

import com.lyu.interceptor.CaptchaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LEE
 * @time 2023/2/23 12:06
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CaptchaInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/sse/**")
                .excludePathPatterns("/api/type")
                .excludePathPatterns("/api/tag");
    }
}
