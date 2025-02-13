package com.maorellanag.java_articles.article.service;

import com.maorellanag.java_articles.article.config.properties.ArticleProperties;
import com.maorellanag.java_articles.article.domain.algolia.response.ApiResponse;
import com.maorellanag.java_articles.article.domain.algolia.response.Hit;
import com.maorellanag.java_articles.article.domain.internal.Article;
import com.maorellanag.java_articles.article.mapper.CommonMapper;
import com.maorellanag.java_articles.article.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticleSynchronizeService {

    private final RestTemplate restTemplate;
    private final CommonMapper commonMapper;
    private final ArticleRepository articleRepository;
    private final ArticleProperties articleProperties;

    public ArticleSynchronizeService(RestTemplate restTemplate, CommonMapper commonMapper, ArticleRepository articleRepository, ArticleProperties articleProperties) {
        this.restTemplate = restTemplate;
        this.commonMapper = commonMapper;
        this.articleRepository = articleRepository;
        this.articleProperties = articleProperties;
    }


    public void synchronize() {
        ApiResponse apiResponse = restTemplate
            .getForObject(articleProperties.getUrl(), ApiResponse.class);

        List<Hit> hits = apiResponse.getHits();

        List<Article> articles = hits.stream()
            .filter(hit -> !articleIdFoundInRepository(hit.getObjectId()))
            .map(commonMapper::mapHitToArticle)
            .flatMap(Optional::stream)
            .toList();

        if (!articles.isEmpty()) {
            long beforeCount = articleRepository.count();
            articleRepository.saveAll(articles);
            log.info("{} articles stored successfully", articleRepository.count() - beforeCount);
        }
    }

    private boolean articleIdFoundInRepository(long id) {
        return articleRepository.findById(id).isPresent();
    }

}
