package com.maorellanag.java_articles.article.domain.algolia.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hit implements Serializable {

    private String author;

    @JsonProperty("objectID")
    private long objectId;

    private String title;

    private String url;

    @JsonProperty("story_title")
    private String storyTitle;

    @JsonProperty("story_url")
    private String storyUrl;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("story_id")
    private long storyId;

    @JsonProperty("comment_text")
    private String commentText;

    @JsonProperty("_tags")
    private List<String> tags;
}
