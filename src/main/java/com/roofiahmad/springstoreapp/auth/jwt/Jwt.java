package com.roofiahmad.springstoreapp.auth.jwt;

import com.roofiahmad.springstoreapp.auth.UserPrincipal;
import com.roofiahmad.springstoreapp.auth.constant.Role;
import com.roofiahmad.springstoreapp.user.entity.User;
import com.roofiahmad.springstoreapp.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    public UserPrincipal getUserPrincipalFromRefreshToken(UserRepository userRepository) {
        Long userId = Long.parseLong(claims.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        var userPrincipal = new UserPrincipal();
        userPrincipal.setId(user.getId());
        userPrincipal.setName(user.getName());
        userPrincipal.setEmail(user.getEmail());
        userPrincipal.setRole(user.getRole());
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
