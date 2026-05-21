package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.auth.Role;
import com.roofiahmad.springstoreapp.common.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class AdminSecurityRules implements SecurityRules {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
       registry.requestMatchers(HttpMethod.POST, "/admin").permitAll()
               .requestMatchers("/admin/**").hasRole(Role.ADMIN.name());
    }
}
