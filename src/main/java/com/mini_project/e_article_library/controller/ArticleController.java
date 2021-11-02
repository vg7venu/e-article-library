package com.mini_project.e_article_library.controller;

import java.util.Optional;

import com.mini_project.e_article_library.jpa.model.ArticleDto;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/create")
    public ArticleDto createArticle(@RequestBody Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setName(article.getName());
        articleDto.setTitle(article.getTitle());
        articleDto.setCategory(article.getCategory());
        articleDto.setContent(article.getContent());
        articleDto.setDescription(article.getDescription());
        return articleRepository.save(articleDto);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable int id) {
        Optional<ArticleDto> article = articleRepository.findById(id);
        ArticleDto articleDto = article.get();
        return ResponseEntity.ok(articleDto);
    }

}
