package com.productsAPI.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "HLDXWs3l-Qo87DRyMDjvjzUUCIfDp_ctZHKnEqzb4pU";
    private static final SecretKey SECRET_KEY_SPEC = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username) {
        long EXPIRATION_TIME = 3600000; // 1 hora
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY_SPEC, Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("Token recebido: " + token);
            Jwts.parser()
                    .verifyWith(SECRET_KEY_SPEC)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        System.out.println("Token recebido na validação: " + token);
        return Jwts.parser()
                .verifyWith(SECRET_KEY_SPEC)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
