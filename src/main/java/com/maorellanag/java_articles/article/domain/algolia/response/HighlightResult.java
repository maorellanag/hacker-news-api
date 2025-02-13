package com.maorellanag.java_articles.article.domain.algolia.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HighlightResult {

    private String author;
    private String commentText;
    private String storyTitle;
    private String storyUrl;

}
