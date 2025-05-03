package com.example.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.example.entity.TokenRefreshRequest;
import com.example.entity.TokenResponse;
import com.example.service.CozeApiService;
import com.example.service.JwtService;
import com.example.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private JwtService jwtService;
    private CozeApiService cozeService;
    private OAuthService oAuthService;



    @GetMapping("/call-coze")
    public ResponseEntity<String> callCozeApi() {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL("https://api.coze.cn/v1/conversation/create");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // 设置 headers
            conn.setRequestProperty("accept", "application/json, text/plain, */*");
            conn.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            conn.setRequestProperty("authorization", "Bearer pat_43dXKE539Ao9R4tkIqk7ZEWqLuaU78qKs5iiwVqPGCbNtGoZThzBB19nVPN27b8L");
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("priority", "u=1, i");
            conn.setRequestProperty("sec-ch-ua", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"");
            conn.setRequestProperty("sec-ch-ua-mobile", "?0");
            conn.setRequestProperty("sec-ch-ua-platform", "\"macOS\"");
            conn.setRequestProperty("sec-fetch-dest", "empty");
            conn.setRequestProperty("sec-fetch-mode", "cors");
            conn.setRequestProperty("sec-fetch-site", "same-origin");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");
            conn.setRequestProperty("cookie", "gfkadpd=510023,29883");
            conn.setRequestProperty("Referer", "https://api.coze.cn/open-platform/sdk/chatapp?sender=chat-app-sdk-1746255064762&params=%7B%22chatClientId%22%3A%22InxoPMgCYTs1G1DPOOLj7%22%2C%22chatConfig%22%3A%7B%22type%22%3A%22token%22%2C%22bot_id%22%3A%227499496303970975744%22%2C%22conversation_id%22%3A%22_21X2XrZbx-0K2ynexgAF%22%7D%2C%22componentProps%22%3A%7B%22layout%22%3A%22pc%22%2C%22lang%22%3A%22en%22%2C%22title%22%3A%22%E8%B7%A8%E6%96%87%E5%8C%96%E5%B0%8F%E5%8A%A9%E6%89%8B%22%2C%22icon%22%3Afalse%2C%22uploadable%22%3Atrue%7D%7D");

            // 请求体
            String json = "{\"bot_id\":\"7499496303970975744\",\"connector_id\":\"999\"}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();

            try (Scanner scanner = new Scanner(
                    responseCode == 200 ? conn.getInputStream() : conn.getErrorStream()).useDelimiter("\\A")) {
                output.append(scanner.hasNext() ? scanner.next() : "");
            }

            conn.disconnect();
        } catch (Exception e) {
            output.append("调用失败：").append(e.getMessage());
        }

        return ResponseEntity.ok(output.toString());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createConversation() {

        try {
            String[] command = {
                    "bash", "-c",
                    "curl 'https://api.coze.cn/v1/conversation/create' " +
                            "-H 'accept: application/json, text/plain, */*' " +
                            "-H 'accept-language: zh-CN,zh;q=0.9' " +
                            "-H 'authorization: Bearer pat_43dXKE539Ao9R4tkIqk7ZEWqLuaU78qKs5iiwVqPGCbNtGoZThzBB19nVPN27b8L' " +
                            "-H 'content-type: application/json' " +
                            "-H 'cookie: gfkadpd=510023,29883' " +
                            "-H 'origin: https://api.coze.cn' " +
                            "-H 'priority: u=1, i' " +
                            "-H 'referer: https://api.coze.cn/open-platform/sdk/chatapp?sender=chat-app-sdk-1746250259521&params=%7B%22chatClientId%22%3A%22gRer899i-rK7IySeTljv5%22%2C%22chatConfig%22%3A%7B%22type%22%3A%22token%22%2C%22bot_id%22%3A%227499496303970975744%22%2C%22conversation_id%22%3A%22wi9JdOiEtAmdGL36b7Fb8%22%7D%2C%22componentProps%22%3A%7B%22layout%22%3A%22pc%22%2C%22lang%22%3A%22en%22%2C%22title%22%3A%22%E8%B7%A8%E6%96%87%E5%8C%96%E5%B0%8F%E5%8A%A9%E6%89%8B%22%2C%22icon%22%3Afalse%2C%22uploadable%22%3Atrue%7D%7D' " +
                            "-H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36' " +
                            "--data-raw '{\"bot_id\":\"7499496303970975744\",\"connector_id\":\"999\"}'"
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return ResponseEntity.ok(output.toString());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("curl 调用失败，退出码：" + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("执行 curl 出错：" + e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        System.out.println("Received request: " + request);
        System.out.println("Received token: " + request.getToken());
        System.out.println("Received userId: " + request.getUserId());

        try {
            String[] parts = request.getToken().split("\\.");
            if (parts.length != 3) {
                System.out.println("Invalid JWT token structure.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JWT format");
            }

            System.out.println("Header: " + parts[0]);
            System.out.println("Payload: " + parts[1]);
            System.out.println("Signature: " + parts[2]);

            // 使用 URL-safe Base64 解码器
            Base64.Decoder urlDecoder = Base64.getUrlDecoder();

            String decodedHeaderStr = new String(urlDecoder.decode(parts[0]), StandardCharsets.UTF_8);
            String decodedPayloadStr = new String(urlDecoder.decode(parts[1]), StandardCharsets.UTF_8);
            String decodedSignatureStr = new String(urlDecoder.decode(parts[2]), StandardCharsets.UTF_8); // 可选

            System.out.println("Decoded Header: " + decodedHeaderStr);
            System.out.println("Decoded Payload: " + decodedPayloadStr);
            System.out.println("Decoded Signature: " + decodedSignatureStr);

            // 生成新的Token（可以加上校验逻辑）
            String newToken = jwtService.generateNewToken(request.getUserId());
            boolean flag = jwtService.isTokenValid(newToken);
            System.out.println("isgood" + flag);
            System.out.println("New Token: " + newToken);

            newToken = OAuthService.requestOAuthAccessToken(newToken);
            System.out.printf("------oauthAccessToken: %s\n", newToken);
            System.out.printf(newToken);
            return ResponseEntity.ok(new TokenResponse(newToken));

        } catch (Exception e) {
            System.out.println("1" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error refreshing token");
        }
    }
}