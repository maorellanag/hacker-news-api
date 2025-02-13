package com.maorellanag.java_articles.article.domain.algolia.response;

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
public class ApiResponse implements Serializable {
    private List<Hit> hits;
}
