package com.maorellanag.java_articles.article.service;

import com.maorellanag.java_articles.article.domain.internal.Article;
import com.maorellanag.java_articles.article.exception.RemoveArticleException;
import com.maorellanag.java_articles.article.repository.ArticleRepository;
import com.maorellanag.java_articles.article.repository.BaseArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final BaseArticleRepository baseArticleRepository;

    public ArticleService(ArticleRepository articleRepository, BaseArticleRepository baseArticleRepository) {
        this.articleRepository = articleRepository;
        this.baseArticleRepository = baseArticleRepository;
    }

    public List<Article> getFilteredArticles(String author, String title, String month, String tag, int page) {
        return baseArticleRepository.getFilteredArticles(author, title, month, tag, page);
    }

    public void deleteById(long id) throws RemoveArticleException {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new RemoveArticleException("Article not found with ID = " + id));

        if (article.isRemoved()) {
            throw new RemoveArticleException("Article already removed for ID = " + id);
        }

        article.setRemoved(true);
        articleRepository.save(article);
        log.info("article removed. ID = {}", id);
    }
}
