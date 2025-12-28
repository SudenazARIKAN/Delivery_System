package com.delivery.shipmentservice.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    // ✅ YENİ - userId çıkarma
    public Long extractUserId(String token) {
        return Long.parseLong(extractAllClaims(token).getSubject());
    }

    // ✅ YENİ - role çıkarma
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // ✅ YENİ - email çıkarma
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    // ✅ MEVCUT - username çıkarma (düzeltilmiş)
    public String extractUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    // ✅ YENİ - Token geçerli mi?
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ YENİ - Token süresi dolmuş mu?
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}