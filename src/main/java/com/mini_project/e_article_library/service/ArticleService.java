package com.mini_project.e_article_library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    private final String BASEURL = "http://localhost:8080/v1/articles/";

    public List<String> generateArticleLinksByCategory(Optional<List<Article>> article) {
        List<String> listOfURLs = new ArrayList<String>();
        for (int i = 0; i < article.get().size(); i++) {
            listOfURLs.add(BASEURL + article.get().get(i).getId());
        }
        return listOfURLs;
    }
}
