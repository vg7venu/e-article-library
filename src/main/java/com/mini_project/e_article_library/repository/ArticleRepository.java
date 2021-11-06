package com.mini_project.e_article_library.repository;

import java.util.List;
import java.util.Optional;

import com.mini_project.e_article_library.jpa.model.ArticleDto;
import com.mini_project.e_article_library.model.Category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleDto, Integer> {
    Optional<List<ArticleDto>> findByCategory(Category category);

    Optional<List<ArticleDto>> findByName(String name);

    Optional<List<ArticleDto>> findByTitle(String name);

    Optional<List<ArticleDto>> findByEmail(String email);

    Optional<List<ArticleDto>> findByCategoryAndEmail(Category category, String email);
}
