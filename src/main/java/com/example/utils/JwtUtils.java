package com.example.utils;

import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    public static String generateJWT() throws Exception {
        // 读取私钥
        byte[] keyBytes = Files.readAllBytes(Paths.get("path/to/private_key.pem"));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));

        // 生成JWT
        return Jwts.builder()
                .setIssuer("1114586840596") // OAuth 应用的 ID
                .setAudience("api.coze.cn") // API 端点
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 过期时间
                .setId("random-unique-id") // 随机字符串，防止重放攻击
                .claim("session_name", "user_12345") // 会话标识
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
