package com.roofiahmad.springstoreapp.filters;

import com.roofiahmad.springstoreapp.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       var authHeader = request.getHeader("Authorization");
       if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           filterChain.doFilter(request, response);
           return;
       }

       String token = authHeader.replace("Bearer ", "");
       var jwtToken = jwtService.parseToken(token);
       if(jwtToken== null || !jwtToken.isExpired()){
           filterChain.doFilter(request, response);
           return;
       }

       var user = jwtToken.getUserPrincipal();

       var authentication = new UsernamePasswordAuthenticationToken(
               user,
               null,
               user.getAuthorities());

       authentication.setDetails(
               new WebAuthenticationDetailsSource().buildDetails(request)
       );

       SecurityContextHolder.getContext().setAuthentication(authentication);
       filterChain.doFilter(request, response);
    }
}
