package com.roofiahmad.springstoreapp.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "frontend")
public class FrontendProperties {
    private List<String> urls;
}
