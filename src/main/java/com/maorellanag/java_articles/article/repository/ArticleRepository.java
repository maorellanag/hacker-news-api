package com.maorellanag.java_articles.article.repository;

import com.maorellanag.java_articles.article.domain.internal.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, Long> {

}
