package com.lyu.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
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

        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);

    }
}
