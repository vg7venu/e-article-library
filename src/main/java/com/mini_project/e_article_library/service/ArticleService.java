package com.mini_project.e_article_library.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mini_project.e_article_library.jpa.model.ArticleDto;
import com.mini_project.e_article_library.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<String> generateArticleLinksByCategory(Optional<List<ArticleDto>> article) {
        List<String> listOfURLs = new ArrayList<String>();
        for (int i = 0; i < article.get().size(); i++) {
            URI location = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/articles").path("/{id}")
                    .buildAndExpand(article.get().get(i).getId()).toUri();
            listOfURLs.add(location.toString());
        }
        return listOfURLs;
    }
}
