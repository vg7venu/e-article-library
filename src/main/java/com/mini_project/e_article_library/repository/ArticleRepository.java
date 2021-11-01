package com.mini_project.e_article_library.repository;

import com.mini_project.e_article_library.entity.Article;

import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
}
