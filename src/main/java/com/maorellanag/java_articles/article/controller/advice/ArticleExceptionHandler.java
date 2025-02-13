package com.maorellanag.java_articles.article.controller.advice;

import com.maorellanag.java_articles.article.exception.RemoveArticleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArticleExceptionHandler {

    @ExceptionHandler(RemoveArticleException.class)
    public ResponseEntity<String> handleArticleNotFoundException(RemoveArticleException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
