package com.mini_project.e_article_library.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mini_project.e_article_library.exception.ArticleNotFoundException;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.repository.ArticleRepository;
import com.mini_project.e_article_library.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class ArticleController {

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

    @Operation(summary = "Get All Articles of that Email in all Categories", tags = "Retrieve Articles")
    @GetMapping("/articles/{email}/all")
    public ResponseEntity<Map> getArticlesByEmailInAllCategories(@PathVariable String email) {
        Map<Category, Optional<List<Article>>> map = new HashMap();
        for (Category category : Category.values()) {
            Optional<List<Article>> articles;
            try {
                articles = articleRepository.findByCategoryAndEmail(category, email);
            } catch (Exception e) {
                throw new ArticleNotFoundException("Article not found/" + e);
            }
            map.put(category, articles);
        }
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @Operation(summary = "Get Articles by the Name", tags = "Retrieve Articles")
    @GetMapping("/author/{name}/articles")
    public ResponseEntity<Optional<List<Article>>> getArticlesByName(@PathVariable String name) {
        Optional<List<Article>> article;
        try {
            article = articleRepository.findByName(name);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        return new ResponseEntity<Optional<List<Article>>>(article, HttpStatus.OK);
    }

    @Operation(summary = "Get Articles using Title", tags = "Retrieve Articles")
    @GetMapping("/article/title/{title}")
    public ResponseEntity<Optional<List<Article>>> getArticlesByTitle(@PathVariable String title) {
        Optional<List<Article>> article;
        try {
            article = articleRepository.findByTitle(title);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        return new ResponseEntity<Optional<List<Article>>>(article, HttpStatus.OK);
    }

    @Operation(summary = "Get Articles using Author email", tags = "Retrieve Articles")
    @GetMapping("/article/author/{email}")
    public ResponseEntity<Optional<List<Article>>> getArticlesByAuthorEmail(@PathVariable String email) {
        Optional<List<Article>> article;
        try {
            article = articleRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        return new ResponseEntity<Optional<List<Article>>>(article, HttpStatus.OK);
    }

}
