package com.maorellanag.java_articles.article.service;

import com.maorellanag.java_articles.article.config.properties.ArticleProperties;
import com.maorellanag.java_articles.article.domain.algolia.response.ApiResponse;
import com.maorellanag.java_articles.article.domain.algolia.response.Hit;
import com.maorellanag.java_articles.article.domain.internal.Article;
import com.maorellanag.java_articles.article.mapper.CommonMapper;
import com.maorellanag.java_articles.article.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleSynchronizeServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommonMapper commonMapper;
    @Mock
    private ArticleProperties articleProperties;
    @InjectMocks
    private ArticleSynchronizeService articleSynchronizeService;

    @BeforeEach
    void setup() {
        Mockito.when(articleProperties.getUrl()).thenReturn("http://test.com");
    }

    @Test
    void retrieve_whenOneHit_thenArticlesSavedInRepository() {
        // given
        Hit hit = Hit.builder()
            .author("Thomas123")
            .storyTitle("wow")
            .storyUrl("http://test.com")
            .build();

        when(restTemplate.getForObject(anyString(), any()))
            .thenReturn(ApiResponse.builder()
                .hits(List.of(hit))
                .build());

        when(commonMapper.mapHitToArticle(any()))
            .thenReturn(Optional.of(Article.builder().build()));

        // when
        articleSynchronizeService.synchronize();

        // then
        Mockito.verify(articleRepository).saveAll(Mockito.anyCollection());
    }

    @Test
    void retrieve_whenNoHits_thenNoArticlesSavedInRepository() {
        // given
        when(restTemplate.getForObject(anyString(), any()))
            .thenReturn(ApiResponse.builder()
                .hits(Collections.EMPTY_LIST)
                .build());

        // when
        articleSynchronizeService.synchronize();

        // then
        Mockito.verify(articleRepository, Mockito.never()).saveAll(Mockito.anyCollection());
    }

}