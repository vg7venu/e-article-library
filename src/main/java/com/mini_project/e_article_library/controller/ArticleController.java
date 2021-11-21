package com.mini_project.e_article_library.controller;

import java.util.List;
import java.util.Map;

import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/create")
    public String createArticle(@ModelAttribute("article") Article article) {
        article = articleService.createArticle(article);
        return "redirect:/article/" + article.getId();
    }

    @GetMapping("/article/{id}")
    public String getArticle(Model model, @PathVariable int id) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/article/all")
    public String getArticlesInAllCategories(Model model) {
        Map<Category, List<Article>> map = articleService.getAllArticles();
        model.addAttribute("allArticles", map);
        return "article-list";
    }

    @GetMapping("/createArticleForm")
    public String createArticle(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        return "create-article";
    }

    @GetMapping("/updateArticleForm/{id}")
    public String updateArticle(@PathVariable(value = "id") int id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        return "update-article";
    }

    @GetMapping("/deleteArticle/{id}")
    public String deleteArticleById(@PathVariable int id) {
        articleService.deleteArticleById(id);
        return "redirect:/article/all";
    }

    @GetMapping("/article/author/{email}")
    public String getArticlesByEmailInAllCategories(Model model, @PathVariable String email) {
        Map<Category, List<Article>> map = articleService.getArticlesByEmailInAllCategories(email);
        model.addAttribute("allArticles", map);
        model.addAttribute("email", email);
        return "articles-author";
    }

}
