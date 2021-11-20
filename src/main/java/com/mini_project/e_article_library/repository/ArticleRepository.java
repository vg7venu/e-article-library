package com.mini_project.e_article_library.repository;

import java.util.List;
import java.util.Optional;

import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {
    Optional<List<Article>> findByCategory(Category category);

    Optional<List<Article>> findByName(String name);

    Optional<List<Article>> findByTitle(String name);

    Optional<List<Article>> findByEmail(String email);

    Optional<List<Article>> findByCategoryAndEmail(Category category, String email);
}
