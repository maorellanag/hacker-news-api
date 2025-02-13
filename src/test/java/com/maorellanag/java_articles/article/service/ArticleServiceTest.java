package com.maorellanag.java_articles.article.service;

import com.maorellanag.java_articles.article.domain.internal.Article;
import com.maorellanag.java_articles.article.exception.RemoveArticleException;
import com.maorellanag.java_articles.article.mapper.CommonMapper;
import com.maorellanag.java_articles.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommonMapper commonMapper;
    @Captor
    private ArgumentCaptor<Article> articleCaptor;
    @InjectMocks
    private ArticleService articleService;

    @Test
    void deleteById_whenArticleFoundInRepository_thenArticleMarkedAsRemoved() throws RemoveArticleException {
        // given
        Article article = Article.builder().build();
        Mockito.when(articleRepository.findById(Mockito.eq(1L)))
            .thenReturn(Optional.of(article));

        // when
        articleService.deleteById(1);

        // then
        verify(articleRepository).save(articleCaptor.capture());
        Article value = articleCaptor.getValue();
        assertTrue(value.isRemoved());
    }

    @Test
    void deleteById_whenArticleNotFoundInRepository_thenRemoveArticleException() {
        // given
        Mockito.when(articleRepository.findById(Mockito.eq(1L)))
            .thenReturn(Optional.empty());

        RemoveArticleException exception = assertThrows(RemoveArticleException.class, () -> articleService.deleteById(1));
        assertEquals("Article not found with ID = 1", exception.getMessage());
    }

    @Test
    void deleteById_whenArticleWasAlreadyMarkedAsRemoved_thenRemoveArticleException() {
        // given
        Article article = Article.builder().removed(true).build();
        Mockito.when(articleRepository.findById(Mockito.eq(1L)))
            .thenReturn(Optional.of(article));

        RemoveArticleException exception = assertThrows(RemoveArticleException.class, () -> articleService.deleteById(1));
        assertEquals("Article already removed for ID = 1", exception.getMessage());
    }


}