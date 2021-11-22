package com.mini_project.e_article_library.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.mini_project.e_article_library.model.Article;
import com.mini_project.e_article_library.model.Category;
import com.mini_project.e_article_library.service.ArticleService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ArticleController.class})
@ExtendWith(SpringExtension.class)
class ArticleControllerTest {
    @Autowired
    private ArticleController articleController;

    @MockBean
    private ArticleService articleService;

    @Test
    void testCreateArticle() throws Exception {
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
        when(this.articleService.createArticle((Article) any())).thenReturn(article);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/create");
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/article/1"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/article/1"));
    }

    @Test
    void testGetArticle() throws Exception {
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
        when(this.articleService.getArticle(anyInt())).thenReturn(article);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/article/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.view().name("article"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("article"));
    }

    @Test
    void testDeleteArticleById() throws Exception {
        when(this.articleService.deleteArticleById(anyInt())).thenReturn("42");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/deleteArticle/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/article/all"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/article/all"));
    }

    @Test
    void testDeleteArticleById2() throws Exception {
        when(this.articleService.deleteArticleById(anyInt())).thenReturn("42");
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/deleteArticle/{id}", 1);
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/article/all"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/article/all"));
    }

    @Test
    void testGetArticlesByEmailInAllCategories() throws Exception {
        when(this.articleService.getArticlesByEmailInAllCategories((String) any()))
                .thenReturn(new HashMap<Category, List<Article>>(1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/article/author/{email}",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allArticles", "email"))
                .andExpect(MockMvcResultMatchers.view().name("articles-author"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("articles-author"));
    }

    @Test
    void testGetArticlesByEmailInAllCategories2() throws Exception {
        when(this.articleService.getArticlesByEmailInAllCategories((String) any()))
                .thenReturn(new HashMap<Category, List<Article>>(1));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/article/author/{email}",
                "jane.doe@example.org");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allArticles", "email"))
                .andExpect(MockMvcResultMatchers.view().name("articles-author"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("articles-author"));
    }

    @Test
    void testCreateArticle2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/createArticleForm");
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.view().name("create-article"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("create-article"));
    }

    @Test
    void testCreateArticle3() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/createArticleForm");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.view().name("create-article"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("create-article"));
    }

    @Test
    void testGetArticlesInAllCategories() throws Exception {
        when(this.articleService.getAllArticles()).thenReturn(new HashMap<Category, List<Article>>(1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/article/all");
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allArticles"))
                .andExpect(MockMvcResultMatchers.view().name("article-list"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("article-list"));
    }

    @Test
    void testGetArticlesInAllCategories2() throws Exception {
        when(this.articleService.getAllArticles()).thenReturn(new HashMap<Category, List<Article>>(1));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/article/all");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allArticles"))
                .andExpect(MockMvcResultMatchers.view().name("article-list"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("article-list"));
    }

    @Test
    void testUpdateArticle() throws Exception {
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
        when(this.articleService.getArticle(anyInt())).thenReturn(article);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/updateArticleForm/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.view().name("update-article"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("update-article"));
    }
}

