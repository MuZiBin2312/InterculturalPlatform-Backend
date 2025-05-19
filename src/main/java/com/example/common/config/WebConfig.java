package com.example.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/register")
//                .excludePathPatterns("/files/**")
//                .excludePathPatterns(
//                        "/api/refresh-token", // ✅ 放行刷新token的接口
//                        "/api/login",         // ✅ 登录/注册
//                        "/api/call-coze"       // ✅ 如果有注册接口，也别拦
//
//                )
//
//            // 游客访问首页
//                .excludePathPatterns("/category/*")
//                .excludePathPatterns("/news/*")
//                .excludePathPatterns("/video/*")
//                .excludePathPatterns("/banner/*")
//        ;
    }
}