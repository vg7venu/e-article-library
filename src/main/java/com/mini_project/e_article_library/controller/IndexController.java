package com.mini_project.e_article_library.controller;

import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
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
}
