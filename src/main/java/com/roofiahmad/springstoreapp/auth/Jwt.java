package com.roofiahmad.springstoreapp.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;


public class Jwt {
   private final Claims claims;
   private final SecretKey secretKey;

   public Jwt(Claims claims, SecretKey secretKey) {
       this.claims = claims;
       this.secretKey = secretKey;
   }

    public Boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public UserPrincipal getUserPrincipal() {
        var userPrincipal = new UserPrincipal();
        userPrincipal.setName(claims.get("name", String.class));
        userPrincipal.setEmail(claims.get("email", String.class));
        userPrincipal.setId(Long.parseLong(claims.getSubject()));
        userPrincipal.setRole(Role.valueOf(claims.get("role", String.class)));
        return userPrincipal;
    }

    @Override
    public String toString(){
       return Jwts.builder()
               .claims(claims)
               .signWith(secretKey)
               .compact();
    }
}
