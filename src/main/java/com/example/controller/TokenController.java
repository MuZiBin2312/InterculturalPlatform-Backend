package com.example.controller;

import com.example.entity.TokenRefreshRequest;
import com.example.entity.TokenResponse;
import com.example.service.JwtService;
import com.example.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private JwtService jwtService;

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
            boolean flag=jwtService.isTokenValid(newToken);
            System.out.println("isgood"+flag);
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