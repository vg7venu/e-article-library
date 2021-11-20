package com.mini_project.e_article_library.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mini_project.e_article_library.exception.ArticleNotFoundException;
import com.mini_project.e_article_library.exception.SomethingWentWrongException;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.repository.ArticleRepository;
import com.mini_project.e_article_library.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;

// @RestController
@Controller
// @RequestMapping("/v1/")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "Create Article", tags = "Create and Modify Articles")
    @PostMapping("/create")
    // public ArticleDto createArticle(@RequestBody Article article) {
    public String createArticle(@ModelAttribute("article") Article article) {
        // Article articleDto = new Article();
        // articleDto.setName(article.getName());
        // articleDto.setTitle(article.getTitle());
        // articleDto.setEmail(article.getEmail());
        // try {
        // articleDto.setCategory(article.getCategory());
        // } catch (Exception e) {
        // throw new CategoryNotMatchedException("Please check the category/" + e);
        // }
        // articleDto.setContent(article.getContent());
        // articleDto.setDescription(article.getDescription());
        articleRepository.save(article);
        return "redirect:/article/all";
    }

    @Operation(summary = "Get Articles using article Id", tags = "Retrieve Articles")
    @GetMapping("/article/{id}")
    // public ResponseEntity<ArticleDto> getArticle(@PathVariable int id) {
    public String getArticle(Model model, @PathVariable int id) {
        Article article;
        try {
            article = articleRepository.findById(id).get();
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        model.addAttribute("article", article);

        // return ResponseEntity.ok(articleDto);
        return "article";
    }

    @Operation(summary = "Modify Article using Id", tags = "Create and Modify Articles")
    @PutMapping("/article/modify/{id}")
    public Article updateArticle(@PathVariable int id, @RequestBody Article articleDetails) {
        Optional<Article> article;
        Article articleDto;
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

    @Operation(summary = "Delete Article by Id", tags = "Create and Modify Articles")
    @GetMapping("/deleteArticle/{id}")
    public String deleteArticleByIdd(@PathVariable int id) {
        Optional<Article> article;
        Article articleDto;
        try {
            article = articleRepository.findById(id);
            articleDto = article.get();
            articleRepository.delete(articleDto);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        // return ResponseEntity.ok("Article has been deleted");
        // return "redirect:/";
        return "redirect:/article/all";
    }

    @Operation(summary = "Delete Article by Id", tags = "Create and Modify Articles")
    @DeleteMapping("/article/delete/{id}")
    public ResponseEntity<String> deleteArticleById(@PathVariable int id) {
        Optional<Article> article;
        Article articleDto;
        try {
            article = articleRepository.findById(id);
            articleDto = article.get();
            articleRepository.delete(articleDto);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        return ResponseEntity.ok("Article has been deleted");
    }

    @Operation(summary = "Get Articles links by Category", tags = "Links")
    @GetMapping("/{category}")
    public List<String> getArticlesByCategoryLinks(@PathVariable String category) {
        List<String> listOfURLs = new ArrayList<String>();
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
        try {
            listOfURLs = articleService.generateArticleLinksByCategory(article);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Something is problem with urls generator" + e);
        }
        return listOfURLs;
    }

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

    @Operation(summary = "Get All Articles in all Categories", tags = "Retrieve Articles")
    @GetMapping("/article/all")
    // public ResponseEntity<Map> getArticlesInAllCategories() {
    public String getArticlesInAllCategories(Model model) {
        Map<Category, List<Article>> map = new HashMap();
        for (Category category : Category.values()) {
            List<Article> articles;
            try {
                articles = articleRepository.findByCategory(category).get();
            } catch (Exception e) {
                throw new ArticleNotFoundException("Article not found/" + e);
            }
            map.put(category, articles);
        }
        model.addAttribute("allArticles", map);
        return "article-list";
        // return new ResponseEntity<Map>(map, HttpStatus.OK);

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
