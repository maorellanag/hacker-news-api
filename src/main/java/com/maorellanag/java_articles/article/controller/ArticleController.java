package com.maorellanag.java_articles.article.controller;

import com.maorellanag.java_articles.article.domain.internal.Article;
import com.maorellanag.java_articles.article.exception.RemoveArticleException;
import com.maorellanag.java_articles.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(
        summary = "Get article items",
        description = "Allow to get article items in paginated responses based on filters author, title, month word and tag"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of article items that meet the query criteria"),
    })
    @GetMapping("/articles")
    public List<Article> articles(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false, defaultValue = "0") int page) {
        return articleService.getFilteredArticles(author, title, month, tag, page);
    }

    @Operation(
        summary = "Delete an article item by ID",
        description = "Allow to delete an article item so that article will not be part of the responses"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Article removed successfully"),
        @ApiResponse(responseCode = "404", description = "Article not found to be removed")
    })
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) throws RemoveArticleException {
        articleService.deleteById(id);
        return new ResponseEntity<>("Article removed", HttpStatus.OK);
    }
}
