package com.maorellanag.java_articles.article.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "article.synchronize")
@Data
public class ArticleProperties {

    private long intervalMillis;
    private String url;

}
