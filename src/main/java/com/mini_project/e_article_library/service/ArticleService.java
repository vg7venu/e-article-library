package com.mini_project.e_article_library.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mini_project.e_article_library.exception.ArticleNotFoundException;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article getArticle(int id) {
        Article article;
        try {
            article = articleRepository.findById(id).get();
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        return article;
    }

    public Map<Category, List<Article>> getAllArticles() {
        Map<Category, List<Article>> map = new HashMap();
        for (Category category : Category.values()) {
            List<Article> articles;
            try {
                articles = articleRepository.findByCategory(category).get();
                Collections.sort(articles, new Comparator<Article>() {
                    public int compare(Article a1, Article a2) {
                        return a1.getPublicationDate().compareTo(a2.getPublicationDate());
                    }
                });
                Collections.reverse(articles);
            } catch (Exception e) {
                throw new ArticleNotFoundException("Article not found/" + e);
            }
            map.put(category, articles);
        }
        return map;
    }

    public String deleteArticleById(int id) {
        Article article;
        try {
            article = articleRepository.findById(id).get();
            articleRepository.delete(article);
        } catch (Exception e) {
            throw new ArticleNotFoundException("Article not found/" + e);
        }
        return "redirect:/article/all";
    }

    public Map<Category, List<Article>> getArticlesByEmailInAllCategories(String email) {
        Map<Category, List<Article>> map = new HashMap();
        for (Category category : Category.values()) {
            List<Article> articles;
            try {
                articles = articleRepository.findByCategoryAndEmail(category, email).get();
                Collections.sort(articles, new Comparator<Article>() {
                    public int compare(Article a1, Article a2) {
                        return a1.getPublicationDate().compareTo(a2.getPublicationDate());
                    }
                });
                Collections.reverse(articles);
            } catch (Exception e) {
                throw new ArticleNotFoundException("Article not found/" + e);
            }
            map.put(category, articles);
        }
        return map;
    }
}
