package com.example.service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class CozeApiService {

    private final String url = "https://api.coze.cn/v1/conversation/create";
    private final String authorizationToken = "Bearer pat_43dXKE539Ao9R4tkIqk7ZEWqLuaU78qKs5iiwVqPGCbNtGoZThzBB19nVPN27b8L";

    public void createConversation() {
        RestTemplate restTemplate = new RestTemplate();

        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorizationToken);
        headers.set("Accept-Language", "zh-CN,zh;q=0.9");
        headers.set("Cookie", "gfkadpd=510023,29883");
        headers.set("Origin", "https://api.coze.cn");
        headers.set("Priority", "u=1, i");
        headers.set("Referer", "https://api.coze.cn/open-platform/sdk/chatapp?sender=chat-app-sdk-1746250259521&params=%7B%22chatClientId%22%3A%22gRer899i-rK7IySeTljv5%22%2C%22chatConfig%22%3A%7B%22type%22%3A%22token%22%2C%22bot_id%22%3A%227499496303970975744%22%2C%22conversation_id%22%3A%22wi9JdOiEtAmdGL36b7Fb8%22%7D%2C%22componentProps%22%3A%7B%22layout%22%3A%22pc%22%2C%22lang%22%3A%22en%22%2C%22title%22%3A%22%E8%B7%A8%E6%96%87%E5%8C%96%E5%B0%8F%E5%8A%A9%E6%89%8B%22%2C%22icon%22%3Afalse%2C%22uploadable%22%3Atrue%7D%7D");
        headers.set("Sec-CH-UA", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"");
        headers.set("Sec-CH-UA-Mobile", "?0");
        headers.set("Sec-CH-UA-Platform", "\"macOS\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");

        // 请求体
        String jsonBody = "{\"bot_id\":\"7499496303970975744\",\"connector_id\":\"999\"}";

        // 创建请求实体
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // 打印响应结果
        System.out.println(response.getBody());
    }
}