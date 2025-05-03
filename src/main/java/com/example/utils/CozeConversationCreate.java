package com.example.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CozeConversationCreate {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://api.coze.cn/v1/conversation/create");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // 设置请求头
            conn.setRequestProperty("accept", "application/json, text/plain, */*");
            conn.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            conn.setRequestProperty("authorization", "Bearer pat_43dXKE539Ao9R4tkIqk7ZEWqLuaU78qKs5iiwVqPGCbNtGoZThzBB19nVPN27b8L");
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("cookie", "gfkadpd=510023,29883");
            conn.setRequestProperty("origin", "https://api.coze.cn");
            conn.setRequestProperty("priority", "u=1, i");
            conn.setRequestProperty("referer", "https://api.coze.cn/open-platform/sdk/chatapp?sender=chat-app-sdk-1746255064762&params=%7B%22chatClientId%22%3A%22InxoPMgCYTs1G1DPOOLj7%22%2C%22chatConfig%22%3A%7B%22type%22%3A%22token%22%2C%22bot_id%22%3A%227499496303970975744%22%2C%22conversation_id%22%3A%22_21X2XrZbx-0K2ynexgAF%22%7D%2C%22componentProps%22%3A%7B%22layout%22%3A%22pc%22%2C%22lang%22%3A%22en%22%2C%22title%22%3A%22%E8%B7%A8%E6%96%87%E5%8C%96%E5%B0%8F%E5%8A%A9%E6%89%8B%22%2C%22icon%22%3Afalse%2C%22uploadable%22%3Atrue%7D%7D");
            conn.setRequestProperty("sec-ch-ua", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"");
            conn.setRequestProperty("sec-ch-ua-mobile", "?0");
            conn.setRequestProperty("sec-ch-ua-platform", "\"macOS\"");
            conn.setRequestProperty("sec-fetch-dest", "empty");
            conn.setRequestProperty("sec-fetch-mode", "cors");
            conn.setRequestProperty("sec-fetch-site", "same-origin");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");

            // 请求体
            String jsonInputString = "{\"bot_id\":\"7499496303970975744\",\"connector_id\":\"999\"}";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 读取响应
            int responseCode = conn.getResponseCode();
            System.out.println("HTTP 响应码: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (var in = new java.util.Scanner(conn.getInputStream()).useDelimiter("\\A")) {
                    String response = in.hasNext() ? in.next() : "";
                    System.out.println("响应内容:\n" + response);
                }
            } else {
                System.out.println("请求失败：" + conn.getResponseMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}