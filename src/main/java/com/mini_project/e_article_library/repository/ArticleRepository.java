package com.mini_project.e_article_library.repository;

import com.mini_project.e_article_library.jpa.model.ArticleDto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleDto, Integer> {
}
