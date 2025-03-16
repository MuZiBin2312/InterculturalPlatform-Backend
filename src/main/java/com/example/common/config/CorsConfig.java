package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 允许本地开发地址和外网地址
        corsConfiguration.addAllowedOrigin("http://localhost:8080");  // 本地开发地址
        corsConfiguration.addAllowedOrigin("https://550295mjyq84.vicp.fun");  // 外网地址

        // 允许所有的请求头
        corsConfiguration.addAllowedHeader("*");

        // 允许所有请求方法
        corsConfiguration.addAllowedMethod("*");

        // 支持带有凭证（cookies, headers）的跨域请求
        corsConfiguration.setAllowCredentials(true);  // 确保设置为true

        // 处理OPTIONS请求
        corsConfiguration.addAllowedMethod("OPTIONS");

        // 配置跨域路径
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}