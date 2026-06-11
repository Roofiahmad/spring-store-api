package com.roofiahmad.springstoreapp.auth.jwt;

import com.roofiahmad.springstoreapp.auth.constant.TokenType;
import com.roofiahmad.springstoreapp.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(User user) {
       return generateToken(user, jwtConfig.getAccessTokenExpiration(), TokenType.ACCESS_TOKEN);
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration(), TokenType.REFRESH_TOKEN);
    }

    private Jwt generateToken(User user, int tokenExpiration, TokenType tokenType) {
        Claims claims;
        if (tokenType == TokenType.REFRESH_TOKEN ) {
            claims = Jwts.claims()
                    .subject(user.getId().toString())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000L))
                    .build();
        } else {
            claims = Jwts.claims()
                    .subject(user.getId().toString())
                    .add("email", user.getEmail())
                    .add("name", user.getName())
                    .add("role", user.getRole())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000L))
                    .build();
        }

       return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt parseToken(String token) {
        try{
            var claims = Jwts.parser()
                    .verifyWith(jwtConfig.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }

    }

}
