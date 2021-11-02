package com.mini_project.e_article_library.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mini_project.e_article_library.exception.ArticleNotFoundException;
import com.mini_project.e_article_library.exception.CategoryNotMatchedException;
import com.mini_project.e_article_library.jpa.model.ArticleDto;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        try {
            articleDto.setCategory(article.getCategory());
        } catch (Exception e) {
            throw new CategoryNotMatchedException("Please check the category/" + e);
        }
        articleDto.setContent(article.getContent());
        articleDto.setDescription(article.getDescription());
        return articleRepository.save(articleDto);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable int id) {
        Optional<ArticleDto> article;
        ArticleDto articleDto;
        try {
            article = articleRepository.findById(id);
            articleDto = article.get();
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        return ResponseEntity.ok(articleDto);
    }

    @PutMapping("/articles/{id}")
    public ArticleDto updateArticle(@PathVariable int id, @RequestBody Article articleDetails) {
        Optional<ArticleDto> article;
        ArticleDto articleDto;
        try {
            article = articleRepository.findById(id);
            articleDto = article.get();
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (articleDetails.getName() != null) {
            articleDto.setName(articleDetails.getName());
        }
        if (articleDetails.getTitle() != null) {
            articleDto.setTitle(articleDetails.getTitle());
        }
        if (articleDetails.getCategory() != null) {
            articleDto.setCategory(articleDetails.getCategory());
        }
        if (articleDetails.getContent() != null) {
            articleDto.setContent(articleDetails.getContent());
        }
        if (articleDetails.getDescription() != null) {
            articleDto.setDescription(articleDetails.getDescription());
        }

        return articleRepository.save(articleDto);
    }

    @GetMapping("/{category}")
    public List<String> getFictionArticles(@PathVariable String category) {
        List<String> listofUrls = new ArrayList<String>();
        Optional<List<ArticleDto>> article;
        try {
            Category cat = Category.valueOf(category);
            article = articleRepository.findByCategory(cat);

            for (int i = 0; i < article.get().size(); i++) {
                URI location = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/articles")
                        .path("/{id}").buildAndExpand(article.get().get(i).getId()).toUri();
                listofUrls.add(location.toString());
            }
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        return listofUrls;
    }

}
