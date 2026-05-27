package com.roofiahmad.springstoreapp.profile;

import com.roofiahmad.springstoreapp.common.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ProfileSecurityRules implements SecurityRules{
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry.requestMatchers(HttpMethod.POST,"/profile").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/profile").hasRole("USER");
    }
}
