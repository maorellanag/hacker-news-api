package com.maorellanag.java_articles.article.repository;

import com.maorellanag.java_articles.article.domain.internal.Article;
import com.maorellanag.java_articles.article.mapper.CommonMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Repository
public class BaseArticleRepository {

    private static final int MAX_PAGE_ITEMS = 5;
    private static final String CASE_INSENSITIVE_OPTION = "i";

    private final CommonMapper commonMapper;
    private final MongoTemplate mongoTemplate;

    public BaseArticleRepository(CommonMapper commonMapper, MongoTemplate mongoTemplate) {
        this.commonMapper = commonMapper;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Article> getFilteredArticles(String author, String title, String month, String tag, int page) {
        Criteria criteria = new Criteria();

        if (isNotBlank(author)) {
            criteria.and("author").is(author);
        }

        if (isNotBlank(title)) {
            criteria.orOperator(
                Criteria.where("storyTitle").regex(title, CASE_INSENSITIVE_OPTION),
                Criteria.where("title").regex(title, CASE_INSENSITIVE_OPTION)
            );
        }

        if (isNotBlank(tag)) {
            criteria.and("tags").in(tag);
        }

        if (isNotBlank(month)) {
            int monthNumber = commonMapper.mapMonthWordToNumber(month);
            if (monthNumber > 0) {
                criteria.and("month").is(monthNumber);
            }
        }

        criteria.and("removed").is(false);

        Query query = new Query(criteria);
        query.with(PageRequest.of(page, MAX_PAGE_ITEMS,
            Sort.by(Sort.Direction.DESC, "createdAt")));

        return mongoTemplate.find(query, Article.class);
    }

}
