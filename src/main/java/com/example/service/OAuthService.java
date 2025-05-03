package com.example.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

public class OAuthService {

    private static final String OAUTH_TOKEN_URL = "https://api.coze.cn/api/permission/oauth2/token"; // 这里替换为实际的 OAuth 地址

    public static String requestOAuthAccessToken(String jwtToken) {
        RestTemplate restTemplate = new RestTemplate();

        // 组装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // 设置 Content-Type 为 application/json
        headers.set("Authorization", "Bearer" + jwtToken); // 添加Authorization头，包含JWT

        // 打印请求头，确保正确设置了 Authorization 和 Content-Type
        System.out.println("Request Headers:");
//        headers.forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.printf(headers.toString());
        // 构造请求体
        String requestBody = "{\n" +
                "    \"duration_seconds\": 86399,\n" +
                "    \"grant_type\": \"urn:ietf:params:oauth:grant-type:jwt-bearer\"\n" +
                "}";

        // 打印请求体，确保请求体格式正确
        System.out.println("Request Body: " + requestBody);

        // 构造请求实体
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(requestBody, headers);

        // 发送POST请求到OAuth服务并获取响应
        ResponseEntity<String> response = restTemplate.exchange(OAUTH_TOKEN_URL, HttpMethod.POST, entity, String.class);
        System.out.println(response);
        // 打印响应的body和状态码，便于调试
        System.out.printf("Response Status Code: %s\n", response.getStatusCode());
        System.out.printf("Response Body: %s\n", response.getBody());

        // 返回获取到的OAuth访问令牌（如果有）
        return response.getBody();
    }
}
