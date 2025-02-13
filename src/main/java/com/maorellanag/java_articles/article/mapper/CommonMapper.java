package com.maorellanag.java_articles.article.mapper;

import com.maorellanag.java_articles.article.domain.algolia.response.Hit;
import com.maorellanag.java_articles.article.domain.internal.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Component
@Slf4j
public class CommonMapper {

    public Optional<Article> mapHitToArticle(Hit hit) {
        Article article = null;

        if (isNotBlank(hit.getStoryTitle()) || isNotBlank(hit.getTitle())) {

            article = Article.builder()
                .id(hit.getObjectId())
                .author(hit.getAuthor())
                .title(hit.getTitle())
                .storyTitle(hit.getStoryTitle())
                .url(hit.getUrl())
                .storyUrl(hit.getStoryUrl())
                .commentText(hit.getCommentText())
                .createdAt(parseToLocalDateTime(hit.getCreatedAt()))
                .tags(hit.getTags())
                .removed(false)
                .build();

            article.setMonth(article.getCreatedAt().getMonth().getValue());
        }

        return Optional.ofNullable(article);
    }

    public int mapMonthWordToNumber(String monthWord) {
        int monthNumber = 0;
        try {
            monthNumber = Month.valueOf(monthWord.toUpperCase(Locale.US)).getValue();
        } catch (Exception e) {
            log.warn("Could not convert month word to number", e);
        }
        return monthNumber;
    }

    private static LocalDateTime parseToLocalDateTime(String dateString) {
        Instant instant = Instant.parse(dateString);
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

}
