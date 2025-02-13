package com.maorellanag.java_articles.article.mapper;

import com.maorellanag.java_articles.article.domain.algolia.response.Hit;
import com.maorellanag.java_articles.article.domain.internal.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CommonMapperTest {

    private final CommonMapper mapper = new CommonMapper();

    @Test
    void mapHitToArticle_whenHitContainsAllProperties_thenSuccessMap() {
        // given
        Hit hit = Hit.builder()
            .author("Thomas123")
            .storyTitle("Java 21 overview")
            .storyUrl("http://test.com")
            .createdAt("2025-01-20T01:54:19Z")
            .commentText("great!")
            .storyId(23L)
            .tags(List.of("tag1", "tag2"))
            .objectId(45L)
            .build();

        // when
        Optional<Article> optionalArticle = mapper.mapHitToArticle(hit);

        // then
        assertTrue(optionalArticle.isPresent());

        Article article = optionalArticle.get();
        assertEquals(45L, article.getId());
        assertEquals("Thomas123", article.getAuthor());
        assertEquals("Java 21 overview", article.getStoryTitle());
        assertEquals("http://test.com", article.getStoryUrl());
        assertEquals(20, article.getCreatedAt().getDayOfMonth());
        assertEquals(1, article.getCreatedAt().getMonth().getValue());
        assertEquals(2025, article.getCreatedAt().getYear());
        assertEquals(1, article.getMonth());
        assertEquals("great!", article.getCommentText());
        assertEquals(2, article.getTags().size());
    }

    @Test
    void mapHitToArticle_whenHitDoNotHaveStoryTitle_thenEmptyArticle() {
        // given
        Hit hit = Hit.builder()
            .author("Thomas123")
            .storyTitle(null)
            .storyUrl("http://test.com")
            .createdAt("2025-01-20T01:54:19Z")
            .commentText("great!")
            .storyId(23L)
            .build();

        // when
        Optional<Article> optionalArticle = mapper.mapHitToArticle(hit);

        // then
        assertFalse(optionalArticle.isPresent());
    }

    @Test
    void mapHitToArticle_whenHitDoNotHaveStoryUrl_thenSuccessMap() {
        // given
        Hit hit = Hit.builder()
            .author("Thomas123")
            .storyTitle("java 21 overview")
            .storyUrl(null)
            .createdAt("2025-01-20T01:54:19Z")
            .commentText("great!")
            .storyId(23L)
            .build();

        // when
        Optional<Article> optionalArticle = mapper.mapHitToArticle(hit);

        // then
        assertTrue(optionalArticle.isPresent());
    }

    @ParameterizedTest
    @CsvSource({"january,1", "april,4", "july,7", "october,10"})
    void mapMonthWordToNumber_whenLowercaseWord_thenCorrectMonthNumber(String monthWord, int expectedMontNumber) {
        int monthNumber = mapper.mapMonthWordToNumber(monthWord);
        assertEquals(expectedMontNumber, monthNumber);
    }

    @ParameterizedTest
    @CsvSource({"JANUARY,1", "APRIL,4", "JULY,7", "OCTOBER,10"})
    void mapMonthWordToNumber_whenUppercaseWord_thenCorrectMonthNumber(String monthWord, int expectedMontNumber) {
        int monthNumber = mapper.mapMonthWordToNumber(monthWord);
        assertEquals(expectedMontNumber, monthNumber);
    }

    @Test
    void mapMonthWordToNumber_whenNoValidMonthName_thenZeroMonthNumber() {
        int monthNumber = mapper.mapMonthWordToNumber("apple");
        assertEquals(0, monthNumber);
    }

}