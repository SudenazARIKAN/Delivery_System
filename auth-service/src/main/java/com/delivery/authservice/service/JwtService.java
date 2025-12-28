package com.delivery.authservice.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.delivery.authservice.model.User;

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

    // ✅ DÜZELTİLMİŞ - userId, role, email eklenmiş
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());      // ← ROLE
        claims.put("email", user.getEmail());           // ← EMAIL
        claims.put("username", user.getUsername());     // ← USERNAME
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())    // ← USER ID (subject'e)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
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