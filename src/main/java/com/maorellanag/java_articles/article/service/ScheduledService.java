package com.maorellanag.java_articles.article.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    private final ArticleSynchronizeService articleSynchronizeService;

    public ScheduledService(ArticleSynchronizeService articleSynchronizeService) {
        this.articleSynchronizeService = articleSynchronizeService;
    }

    @Scheduled(fixedDelayString = "${article.synchronize.interval-millis}")
    public void runScheduledTask() {
        articleSynchronizeService.synchronize();
    }
}
