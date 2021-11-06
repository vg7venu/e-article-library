package com.mini_project.e_article_library.controller;

import java.util.*;

import com.mini_project.e_article_library.exception.ArticleNotFoundException;
import com.mini_project.e_article_library.exception.CategoryNotMatchedException;
import com.mini_project.e_article_library.exception.SomethingWentWrongException;
import com.mini_project.e_article_library.jpa.model.ArticleDto;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.repository.ArticleRepository;
import com.mini_project.e_article_library.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    // Create Article
    @PostMapping("/create")
    public ArticleDto createArticle(@RequestBody Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setName(article.getName());
        articleDto.setTitle(article.getTitle());
        articleDto.setEmail(article.getEmail());
        try {
            articleDto.setCategory(article.getCategory());
        } catch (Exception e) {
            throw new CategoryNotMatchedException("Please check the category/" + e);
        }
        articleDto.setContent(article.getContent());
        articleDto.setDescription(article.getDescription());
        return articleRepository.save(articleDto);
    }

    // Get Article by Id
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

    // Update Article
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

    // Delete Article by Id
    @DeleteMapping("/article/{id}")
    public ResponseEntity<String> deleteArticleById(@PathVariable int id) {
        Optional<ArticleDto> article;
        ArticleDto articleDto;
        try {
            article = articleRepository.findById(id);
            articleDto = article.get();
            articleRepository.delete(articleDto);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        return ResponseEntity.ok("Article has been deleted");
    }

    // Get Article links based on Category
    @GetMapping("/{category}")
    public List<String> getArticlesByCategoryLinks(@PathVariable String category) {
        List<String> listOfURLs = new ArrayList<String>();
        Optional<List<ArticleDto>> article;
        try {
            Category cat = Category.valueOf(category);
            article = articleRepository.findByCategory(cat);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        try {
            listOfURLs = articleService.generateArticleLinksByCategory(article);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Something is problem with urls generator" + e);
        }
        return listOfURLs;
    }

    // Get all articles in the category
    @GetMapping("/category/{category}/articles")
    public ResponseEntity<Optional<List<ArticleDto>>> getArticlesByCategory(@PathVariable String category) {
        Optional<List<ArticleDto>> article;
        try {
            Category cat = Category.valueOf(category);
            article = articleRepository.findByCategory(cat);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        return new ResponseEntity<Optional<List<ArticleDto>>>(article, HttpStatus.OK);
    }

    // Get all articles in the category
    @GetMapping("/articles/all")
    public ResponseEntity<Map> getArticlesInAllCategories() {
        Map<Category, Optional<List<ArticleDto>>> map = new HashMap();
        for (Category category : Category.values()) {
            Optional<List<ArticleDto>> articles;
            try {
                articles = articleRepository.findByCategory(category);
            } catch (Exception e) {
                throw new ArticleNotFoundException("Article not found/" + e);
            }
            map.put(category, articles);
        }
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    // Get all articles using email
    @GetMapping("/articles/{email}/all")
    public ResponseEntity<Map> getArticlesByEmailInAllCategories(@PathVariable String email) {
        Map<Category, Optional<List<ArticleDto>>> map = new HashMap();
        for (Category category : Category.values()) {
            Optional<List<ArticleDto>> articles;
            try {
                articles = articleRepository.findByCategoryAndEmail(category, email);
            } catch (Exception e) {
                throw new ArticleNotFoundException("Article not found/" + e);
            }
            map.put(category, articles);
        }
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    // Find Article by name
    @GetMapping("/author/{name}/articles")
    public ResponseEntity<Optional<List<ArticleDto>>> getArticlesByName(@PathVariable String name) {
        Optional<List<ArticleDto>> article;
        try {
            article = articleRepository.findByName(name);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        return new ResponseEntity<Optional<List<ArticleDto>>>(article, HttpStatus.OK);
    }

    // Find Article by title
    @GetMapping("/article/{title}")
    public ResponseEntity<Optional<List<ArticleDto>>> getArticlesByTitle(@PathVariable String title) {
        Optional<List<ArticleDto>> article;
        try {
            article = articleRepository.findByTitle(title);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        return new ResponseEntity<Optional<List<ArticleDto>>>(article, HttpStatus.OK);
    }

    // Find Article by Author name(email)
    @GetMapping("/article/author/{email}")
    public ResponseEntity<Optional<List<ArticleDto>>> getArticlesByAuthorEmail(@PathVariable String email) {
        Optional<List<ArticleDto>> article;
        try {
            article = articleRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        if (article.get().size() == 0) {
            throw new ArticleNotFoundException("Article not found");
        }
        return new ResponseEntity<Optional<List<ArticleDto>>>(article, HttpStatus.OK);
    }

}
