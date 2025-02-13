package com.maorellanag.java_articles.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class SecurityProperties {

    private String secretKey;
    private long expirationTime;

}
