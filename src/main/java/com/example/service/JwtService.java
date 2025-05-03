package com.example.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    // ======== 请替换为你自己的 RSA 密钥对字符串 ========
    private static final String PRIVATE_KEY_STR = """
-----BEGIN PRIVATE KEY-----
MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCyrlsH1y0xZCXJ
HdFgZs9GAHFlyFWzmIizTCK9jeuDEDQCBab6nccThKtcTmcLL2R5Bz6XIu5stpES
X7KIt3m/1bnPrk4MtdzkPxeOkOgaxRGtbak6MPam+Xes7ZVVBF1LZvXkuJpVslVW
xbehZJ/2iCaCDye94bOux81ayAN661zvxNPAtReeMR0+BM8n9HXZKHeNI+TuW6hr
exCLNpQVySIecAB4xwSc9X9kj8D8SPMW56Kb4rkUVBTWkEqtCsMar+cjRk4zWiK3
LcPfrVjW75FNY2H0vkxrr7+960cijxFMnJH8LwxW8O1sJqLIYo4n8TJPgdnboDau
Rwgki/rfAgMBAAECggEARRHT+3horlaFqrQdixPC3M7wBsXFpyp38UMljLuPBfdd
+0gfD3KtETTwHrrp5y93ObecsMSegKSj+IPImkO+SvL9pDO/VPd5/zsKSqBmMG7s
Uet5hpaFye7WHaErYBVTUQHoXHUlp6nLhkLgpIEmuqBPrmfYV3SnRhFTJtv5SGqL
XKMY/I1F6QLgcQJRCgZn9YBY76MO+HzU8xy/Z+O/JxrJQeG8tdyd+T/pnRbzLCtY
kCdton3MDox3lRpvKnwdVtY/9xixId8qLi4isifzq54DJQ8VJNZkiG/b65iZH/NJ
cmyPR/uBhcBsoJICxEXPCWe/lWUTC+CdDnxLF6Hb7QKBgQDwvGYcL2yMczEgeUl3
BlHRTmcRoOlPaEqWm3SjnwCAOjHpuzFuuf5FPPqagV+XsDbl2WSZ+A7qrzmYBGhI
jIH0tS+V0HWvGvfUsytTSsVPV8rkfIHgL1bJ8YHTmnu3bswXwWeJrs8iGew+B+4n
mRh3zkteCBG1U4mbuVi8Vs8acwKBgQC+ArBI8xBNtI3VodiX4GbbAoyqMEb8s8y7
27118fwdHZqANB+6MgKqyr9msE2CW6w5b3OqdyNIejU6yvioXlW/F7h7/oag/9sO
Z68afMyIUjviUTMNxjzWNKGnU9l/Nw8wcCkYm0ccmuf+HbccbjQe20odU/yzl2LN
4Dd6JuLm5QKBgCATOhevSes8veLxxhQRx3z30UvMAMoPv0Bp3hSe7tYm6teVlXSH
W5WmFk3XxwDS/b2KRZkuL+6dAjN7AtoHvpj4Oyre5485LKQuayth3wA/Vk0zOarB
I/MsNnwXLTy3ioHvxvfVTsvgYFWzgMvN3jC4T9prgNCEHOWewG/3sQh5AoGAHux9
GEJGwv7BnqUqhP1GgBpYHlKhGvuANIq8uPdbGrn4rSjOiRUejgFVSTckRjT5QJe8
8V9WZLNO+nSSJ4TC28jWg4eXDZjWY+/H9b2d5AnOKTUh4/oBaLNEI+FnomapmC6I
4bJTznn/i8H2TTA6V1NFKqTCmc/niq4VGVI+12kCgYBbECNhpd4PGzC3+WiicobZ
x2T9+qLTBQ4O0VWpcqsx1BVhDrrZgVa1tOxxRcb08R8Z22qpYKL91qOXJQ+lRTpy
jCGYpWWVDxSbCVvhYpNp9tBCYk/MpcIJs4n+zfOMmZQt5G9gNY8jhxYEKpXMYKX4
/5V9hMv8JW8EkzE04VzlhA==
-----END PRIVATE KEY-----

            """;

    private static final String PUBLIC_KEY_STR = """
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsq5bB9ctMWQlyR3RYGbP
RgBxZchVs5iIs0wivY3rgxA0AgWm+p3HE4SrXE5nCy9keQc+lyLubLaREl+yiLd5
v9W5z65ODLXc5D8XjpDoGsURrW2pOjD2pvl3rO2VVQRdS2b15LiaVbJVVsW3oWSf
9ogmgg8nveGzrsfNWsgDeutc78TTwLUXnjEdPgTPJ/R12Sh3jSPk7luoa3sQizaU
FckiHnAAeMcEnPV/ZI/A/EjzFueim+K5FFQU1pBKrQrDGq/nI0ZOM1oity3D361Y
1u+RTWNh9L5Ma6+/vetHIo8RTJyR/C8MVvDtbCaiyGKOJ/EyT4HZ26A2rkcIJIv6
3wIDAQAB
-----END PUBLIC KEY-----

            """;

    private static PrivateKey getPrivateKey() throws Exception {
        String key = PRIVATE_KEY_STR.replaceAll("-----\\w+ PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(key);
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private static PublicKey getPublicKey() throws Exception {
        String key = PUBLIC_KEY_STR.replaceAll("-----\\w+ PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(key);
        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
    }

    // 生成 JWT（RS256签名）
    public String generateNewToken(String userId) {
        try {
            return Jwts.builder()
                    .setHeaderParam("alg", "RS256")
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("kid", "fH17GOmyMKmv19PkVQR0eifR5hPt2V3A8LgFKU8fcdI")  // 可选
                    .claim("iss", "1114586840596")
                    .claim("aud", "api.coze.cn")
                    .claim("iat", System.currentTimeMillis() / 1000)
                    .claim("exp", (System.currentTimeMillis() + 3600000) / 1000)
                    .claim("jti", java.util.UUID.randomUUID().toString())
                    .claim("session_name", userId)
                    .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("生成JWT失败", e);
        }
    }

    // 校验 Token 是否有效
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
