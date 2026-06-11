package com.roofiahmad.springstoreapp.user.security;

import com.roofiahmad.springstoreapp.common.security.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityRules implements SecurityRules {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry.requestMatchers(HttpMethod.POST,"/users").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/{id}").hasRole("ADMIN");
    }
}
