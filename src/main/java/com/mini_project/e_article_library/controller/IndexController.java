package com.mini_project.e_article_library.controller;

import com.mini_project.e_article_library.exception.ArticleNotFoundException;
import com.mini_project.e_article_library.jpa.model.ArticleDto;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(value = { "/", "/index" })
    public String welcomePage(Model model) {
        model.addAttribute("Title", "Welcome to E- Article Library");
        return "index";
    }

    @GetMapping("/createArticleForm")
    public String createArticle(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        return "create-article";
    }

    @GetMapping("/updateArticleForm/{id}")
    public String updateArticle(@PathVariable(value = "id") int id, Model model) {
        Article fetchArticle;
        try {
            fetchArticle = articleRepository.findById(id).get();
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        // Article article = new Article();
        model.addAttribute("article", fetchArticle);
        return "update-article";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "access-denied";
    }

}
