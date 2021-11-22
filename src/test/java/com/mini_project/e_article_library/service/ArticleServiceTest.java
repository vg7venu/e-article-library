package com.mini_project.e_article_library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mini_project.e_article_library.exception.ArticleNotFoundException;
import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.repository.ArticleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ArticleService.class})
@ExtendWith(SpringExtension.class)
class ArticleServiceTest {
    @MockBean
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Test
    void testCreateArticle() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");
        when(this.articleRepository.save((Article) any())).thenReturn(article);

        Article article1 = new Article();
        article1.setCategory(Category.Fiction);
        article1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        article1.setPublicationDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        article1.setId(1);
        article1.setName("Name");
        article1.setTitle("Dr");
        article1.setDescription("The characteristics of someone or something");
        article1.setContent("Not all who wander are lost");
        assertSame(article, this.articleService.createArticle(article1));
        verify(this.articleRepository).save((Article) any());
    }

    @Test
    void testGetArticle() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");
        Optional<Article> ofResult = Optional.<Article>of(article);
        when(this.articleRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(article, this.articleService.getArticle(1));
        verify(this.articleRepository).findById((Integer) any());
    }

    @Test
    void testGetArticle2() {
        when(this.articleRepository.findById((Integer) any())).thenThrow(new ArticleNotFoundException("An error occurred"));
        assertThrows(ArticleNotFoundException.class, () -> this.articleService.getArticle(1));
        verify(this.articleRepository).findById((Integer) any());
    }

    @Test
    void testGetArticle3() {
        when(this.articleRepository.findById((Integer) any())).thenReturn(Optional.<Article>empty());
        assertThrows(ArticleNotFoundException.class, () -> this.articleService.getArticle(1));
        verify(this.articleRepository).findById((Integer) any());
    }

    @Test
    void testGetAllArticles() {
        when(this.articleRepository.findByCategory((Category) any()))
                .thenReturn(Optional.<List<Article>>of(new ArrayList<Article>()));
        Map<Category, List<Article>> actualAllArticles = this.articleService.getAllArticles();
        assertEquals(6, actualAllArticles.size());
        assertTrue(actualAllArticles.get(Category.Wildlife).isEmpty());
        verify(this.articleRepository, atLeast(1)).findByCategory((Category) any());
    }

    @Test
    void testGetAllArticles2() {
        when(this.articleRepository.findByCategory((Category) any()))
                .thenThrow(new ArticleNotFoundException("An error occurred"));
        assertThrows(ArticleNotFoundException.class, () -> this.articleService.getAllArticles());
        verify(this.articleRepository).findByCategory((Category) any());
    }

    @Test
    void testGetAllArticles3() {
        when(this.articleRepository.findByCategory((Category) any())).thenReturn(Optional.<List<Article>>empty());
        assertThrows(ArticleNotFoundException.class, () -> this.articleService.getAllArticles());
        verify(this.articleRepository).findByCategory((Category) any());
    }

    @Test
    void testGetAllArticles4() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");

        Article article1 = new Article();
        article1.setCategory(Category.Fiction);
        article1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        article1.setPublicationDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        article1.setId(1);
        article1.setName("Name");
        article1.setTitle("Dr");
        article1.setDescription("The characteristics of someone or something");
        article1.setContent("Not all who wander are lost");

        ArrayList<Article> articleList = new ArrayList<Article>();
        articleList.add(article1);
        articleList.add(article);
        Optional<List<Article>> ofResult = Optional.<List<Article>>of(articleList);
        when(this.articleRepository.findByCategory((Category) any())).thenReturn(ofResult);
        Map<Category, List<Article>> actualAllArticles = this.articleService.getAllArticles();
        assertEquals(6, actualAllArticles.size());
        assertEquals(2, actualAllArticles.get(Category.Wildlife).size());
        verify(this.articleRepository, atLeast(1)).findByCategory((Category) any());
    }

    @Test
    void testGetAllArticles5() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");

        Article article1 = new Article();
        article1.setCategory(Category.Fiction);
        article1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        article1.setPublicationDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        article1.setId(1);
        article1.setName("Name");
        article1.setTitle("Dr");
        article1.setDescription("The characteristics of someone or something");
        article1.setContent("Not all who wander are lost");

        Article article2 = new Article();
        article2.setCategory(Category.Fiction);
        article2.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        article2.setPublicationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        article2.setId(1);
        article2.setName("Name");
        article2.setTitle("Dr");
        article2.setDescription("The characteristics of someone or something");
        article2.setContent("Not all who wander are lost");

        ArrayList<Article> articleList = new ArrayList<Article>();
        articleList.add(article2);
        articleList.add(article1);
        articleList.add(article);
        Optional<List<Article>> ofResult = Optional.<List<Article>>of(articleList);
        when(this.articleRepository.findByCategory((Category) any())).thenReturn(ofResult);
        Map<Category, List<Article>> actualAllArticles = this.articleService.getAllArticles();
        assertEquals(6, actualAllArticles.size());
        assertEquals(3, actualAllArticles.get(Category.Wildlife).size());
        verify(this.articleRepository, atLeast(1)).findByCategory((Category) any());
    }

    @Test
    void testGetAllArticles6() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");

        Article article1 = new Article();
        article1.setCategory(Category.Fiction);
        article1.setEmail("jane.doe@example.org");
        article1.setPublicationDate(null);
        article1.setId(1);
        article1.setName("Name");
        article1.setTitle("Dr");
        article1.setDescription("The characteristics of someone or something");
        article1.setContent("Not all who wander are lost");

        ArrayList<Article> articleList = new ArrayList<Article>();
        articleList.add(article1);
        articleList.add(article);
        Optional<List<Article>> ofResult = Optional.<List<Article>>of(articleList);
        when(this.articleRepository.findByCategory((Category) any())).thenReturn(ofResult);
        assertThrows(ArticleNotFoundException.class, () -> this.articleService.getAllArticles());
        verify(this.articleRepository).findByCategory((Category) any());
    }

    @Test
    void testDeleteArticleById() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");
        Optional<Article> ofResult = Optional.<Article>of(article);
        doNothing().when(this.articleRepository).delete((Article) any());
        when(this.articleRepository.findById((Integer) any())).thenReturn(ofResult);
        assertEquals("redirect:/article/all", this.articleService.deleteArticleById(1));
        verify(this.articleRepository).delete((Article) any());
        verify(this.articleRepository).findById((Integer) any());
    }

    @Test
    void testDeleteArticleById2() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");
        Optional<Article> ofResult = Optional.<Article>of(article);
        doThrow(new ArticleNotFoundException("An error occurred")).when(this.articleRepository).delete((Article) any());
        when(this.articleRepository.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(ArticleNotFoundException.class, () -> this.articleService.deleteArticleById(1));
        verify(this.articleRepository).delete((Article) any());
        verify(this.articleRepository).findById((Integer) any());
    }

    @Test
    void testDeleteArticleById3() {
        doNothing().when(this.articleRepository).delete((Article) any());
        when(this.articleRepository.findById((Integer) any())).thenReturn(Optional.<Article>empty());
        assertThrows(ArticleNotFoundException.class, () -> this.articleService.deleteArticleById(1));
        verify(this.articleRepository).findById((Integer) any());
    }

    @Test
    void testGetArticlesByEmailInAllCategories() {
        when(this.articleRepository.findByCategoryAndEmail((Category) any(), (String) any()))
                .thenReturn(Optional.<List<Article>>of(new ArrayList<Article>()));
        Map<Category, List<Article>> actualArticlesByEmailInAllCategories = this.articleService
                .getArticlesByEmailInAllCategories("jane.doe@example.org");
        assertEquals(6, actualArticlesByEmailInAllCategories.size());
        assertTrue(actualArticlesByEmailInAllCategories.get(Category.Wildlife).isEmpty());
        verify(this.articleRepository, atLeast(1)).findByCategoryAndEmail((Category) any(), (String) any());
    }

    @Test
    void testGetArticlesByEmailInAllCategories2() {
        when(this.articleRepository.findByCategoryAndEmail((Category) any(), (String) any()))
                .thenThrow(new ArticleNotFoundException("An error occurred"));
        assertThrows(ArticleNotFoundException.class,
                () -> this.articleService.getArticlesByEmailInAllCategories("jane.doe@example.org"));
        verify(this.articleRepository).findByCategoryAndEmail((Category) any(), (String) any());
    }

    @Test
    void testGetArticlesByEmailInAllCategories3() {
        when(this.articleRepository.findByCategoryAndEmail((Category) any(), (String) any()))
                .thenReturn(Optional.<List<Article>>empty());
        assertThrows(ArticleNotFoundException.class,
                () -> this.articleService.getArticlesByEmailInAllCategories("jane.doe@example.org"));
        verify(this.articleRepository).findByCategoryAndEmail((Category) any(), (String) any());
    }

    @Test
    void testGetArticlesByEmailInAllCategories4() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");

        Article article1 = new Article();
        article1.setCategory(Category.Fiction);
        article1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        article1.setPublicationDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        article1.setId(1);
        article1.setName("Name");
        article1.setTitle("Dr");
        article1.setDescription("The characteristics of someone or something");
        article1.setContent("Not all who wander are lost");

        ArrayList<Article> articleList = new ArrayList<Article>();
        articleList.add(article1);
        articleList.add(article);
        Optional<List<Article>> ofResult = Optional.<List<Article>>of(articleList);
        when(this.articleRepository.findByCategoryAndEmail((Category) any(), (String) any())).thenReturn(ofResult);
        Map<Category, List<Article>> actualArticlesByEmailInAllCategories = this.articleService
                .getArticlesByEmailInAllCategories("jane.doe@example.org");
        assertEquals(6, actualArticlesByEmailInAllCategories.size());
        assertEquals(2, actualArticlesByEmailInAllCategories.get(Category.Wildlife).size());
        verify(this.articleRepository, atLeast(1)).findByCategoryAndEmail((Category) any(), (String) any());
    }

    @Test
    void testGetArticlesByEmailInAllCategories5() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");

        Article article1 = new Article();
        article1.setCategory(Category.Fiction);
        article1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        article1.setPublicationDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        article1.setId(1);
        article1.setName("Name");
        article1.setTitle("Dr");
        article1.setDescription("The characteristics of someone or something");
        article1.setContent("Not all who wander are lost");

        Article article2 = new Article();
        article2.setCategory(Category.Fiction);
        article2.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        article2.setPublicationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        article2.setId(1);
        article2.setName("Name");
        article2.setTitle("Dr");
        article2.setDescription("The characteristics of someone or something");
        article2.setContent("Not all who wander are lost");

        ArrayList<Article> articleList = new ArrayList<Article>();
        articleList.add(article2);
        articleList.add(article1);
        articleList.add(article);
        Optional<List<Article>> ofResult = Optional.<List<Article>>of(articleList);
        when(this.articleRepository.findByCategoryAndEmail((Category) any(), (String) any())).thenReturn(ofResult);
        Map<Category, List<Article>> actualArticlesByEmailInAllCategories = this.articleService
                .getArticlesByEmailInAllCategories("jane.doe@example.org");
        assertEquals(6, actualArticlesByEmailInAllCategories.size());
        assertEquals(3, actualArticlesByEmailInAllCategories.get(Category.Wildlife).size());
        verify(this.articleRepository, atLeast(1)).findByCategoryAndEmail((Category) any(), (String) any());
    }

    @Test
    void testGetArticlesByEmailInAllCategories6() {
        Article article = new Article();
        article.setCategory(Category.Fiction);
        article.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        article.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        article.setId(1);
        article.setName("Name");
        article.setTitle("Dr");
        article.setDescription("The characteristics of someone or something");
        article.setContent("Not all who wander are lost");

        Article article1 = new Article();
        article1.setCategory(Category.Fiction);
        article1.setEmail("jane.doe@example.org");
        article1.setPublicationDate(null);
        article1.setId(1);
        article1.setName("Name");
        article1.setTitle("Dr");
        article1.setDescription("The characteristics of someone or something");
        article1.setContent("Not all who wander are lost");

        ArrayList<Article> articleList = new ArrayList<Article>();
        articleList.add(article1);
        articleList.add(article);
        Optional<List<Article>> ofResult = Optional.<List<Article>>of(articleList);
        when(this.articleRepository.findByCategoryAndEmail((Category) any(), (String) any())).thenReturn(ofResult);
        assertThrows(ArticleNotFoundException.class,
                () -> this.articleService.getArticlesByEmailInAllCategories("jane.doe@example.org"));
        verify(this.articleRepository).findByCategoryAndEmail((Category) any(), (String) any());
    }
}

