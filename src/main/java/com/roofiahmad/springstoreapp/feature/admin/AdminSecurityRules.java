package com.roofiahmad.springstoreapp.feature.admin;

import com.roofiahmad.springstoreapp.feature.auth.constant.Role;
import com.roofiahmad.springstoreapp.infra.security.config.SecurityRules;
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
