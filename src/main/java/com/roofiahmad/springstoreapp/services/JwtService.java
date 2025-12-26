package com.roofiahmad.springstoreapp.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    final long TOKEN_EXPIRATION_TIME = 86400; // 1 day
    @Value("${spring.jwt.secret}")
    private String SECRET;

    public String generateJwtToken(String email) {
       return Jwts.builder()
                .setSubject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * TOKEN_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();

    }
}
