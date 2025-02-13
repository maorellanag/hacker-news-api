package com.maorellanag.java_articles.article.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "articles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {

    @Id
    private long id;
    private String author;
    private LocalDateTime createdAt;
    private List<String> tags;
    @JsonIgnore
    private boolean removed;
    @JsonIgnore
    private int month;

    private String title;
    private String storyTitle;
    private String url;
    private String storyUrl;

    private String commentText;
}
