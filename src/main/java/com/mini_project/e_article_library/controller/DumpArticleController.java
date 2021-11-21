package com.mini_project.e_article_library.controller;

import java.util.List;
import java.util.Optional;

import com.mini_project.e_article_library.exception.ArticleNotFoundException;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class DumpArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Operation(summary = "Get All Articles in that Category", tags = "Retrieve Articles")
    @GetMapping("/category/{category}/articles")
    public ResponseEntity<Optional<List<Article>>> getArticlesByCategory(@PathVariable String category) {
        Optional<List<Article>> article;
        try {
            Category cat = Category.valueOf(category);
            article = articleRepository.findByCategory(cat);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        return new ResponseEntity<Optional<List<Article>>>(article, HttpStatus.OK);
    }
}
