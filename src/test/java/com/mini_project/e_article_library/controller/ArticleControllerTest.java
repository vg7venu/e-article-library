//package com.mini_project.e_article_library.controller;
//
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mini_project.e_article_library.exception.SomethingWentWrongException;
//import com.mini_project.e_article_library.jpa.model.ArticleDto;
//import com.mini_project.e_article_library.model.Article;
//import com.mini_project.e_article_library.model.Category;
//import com.mini_project.e_article_library.repository.ArticleRepository;
//import com.mini_project.e_article_library.service.ArticleService;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@ContextConfiguration(classes = { ArticleController.class })
//@ExtendWith(SpringExtension.class)
//class ArticleControllerTest {
//        @Autowired
//        private ArticleController articleController;
//
//        @MockBean
//        private ArticleRepository articleRepository;
//
//        @MockBean
//        private ArticleService articleService;
//
//        @Test
//        void testCreateArticle() throws Exception {
//                ArticleDto articleDto = new ArticleDto();
//                articleDto.setCategory(Category.Fiction);
//                articleDto.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto.setId(1);
//                articleDto.setName("Name");
//                articleDto.setTitle("Dr");
//                articleDto.setDescription("The characteristics of someone or something");
//                articleDto.setContent("Not all who wander are lost");
//                when(this.articleRepository.save((ArticleDto) any())).thenReturn(articleDto);
//
//                Article article = new Article();
//                article.setCategory(Category.Fiction);
//                article.setEmail("jane.doe@example.org");
//                article.setName("Name");
//                article.setTitle("Dr");
//                article.setDescription("The characteristics of someone or something");
//                article.setContent("Not all who wander are lost");
//                String content = (new ObjectMapper()).writeValueAsString(article);
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/create")
//                                .contentType(MediaType.APPLICATION_JSON).content(content);
//                MockMvcBuilders.standaloneSetup(this.articleController).build().perform(requestBuilder)
//                                .andExpect(MockMvcResultMatchers.status().isOk())
//                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                                .andExpect(MockMvcResultMatchers.content().string(
//                                                "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"title\":\"Dr\",\"category\":\"Fiction\",\"publicationDate"
//                                                                + "\":0,\"content\":\"Not all who wander are lost\",\"description\":\"The characteristics of someone or"
//                                                                + " something\"}"));
//        }
//
//        @Test
//        void testDeleteArticleById() throws Exception {
//                ArticleDto articleDto = new ArticleDto();
//                articleDto.setCategory(Category.Fiction);
//                articleDto.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto.setId(1);
//                articleDto.setName("Name");
//                articleDto.setTitle("Dr");
//                articleDto.setDescription("The characteristics of someone or something");
//                articleDto.setContent("Not all who wander are lost");
//                Optional<ArticleDto> ofResult = Optional.<ArticleDto>of(articleDto);
//                doNothing().when(this.articleRepository).delete((ArticleDto) any());
//                when(this.articleRepository.findById((Integer) any())).thenReturn(ofResult);
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/article/{id}", 1);
//                MockMvcBuilders.standaloneSetup(this.articleController).build().perform(requestBuilder)
//                                .andExpect(MockMvcResultMatchers.status().isOk())
//                                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
//                                .andExpect(MockMvcResultMatchers.content().string("Article has been deleted"));
//        }
//
//        @Test
//        void testDeleteArticleById2() throws Exception {
//                ArticleDto articleDto = new ArticleDto();
//                articleDto.setCategory(Category.Fiction);
//                articleDto.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto.setId(1);
//                articleDto.setName("Name");
//                articleDto.setTitle("Dr");
//                articleDto.setDescription("The characteristics of someone or something");
//                articleDto.setContent("Not all who wander are lost");
//                Optional<ArticleDto> ofResult = Optional.<ArticleDto>of(articleDto);
//                doThrow(new SomethingWentWrongException("An error occurred")).when(this.articleRepository)
//                                .delete((ArticleDto) any());
//                when(this.articleRepository.findById((Integer) any())).thenReturn(ofResult);
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/article/{id}", 1);
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testDeleteArticleById3() throws Exception {
//                doNothing().when(this.articleRepository).delete((ArticleDto) any());
//                when(this.articleRepository.findById((Integer) any())).thenReturn(Optional.<ArticleDto>empty());
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/article/{id}", 1);
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticle() throws Exception {
//                ArticleDto articleDto = new ArticleDto();
//                articleDto.setCategory(Category.Fiction);
//                articleDto.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto.setId(1);
//                articleDto.setName("Name");
//                articleDto.setTitle("Dr");
//                articleDto.setDescription("The characteristics of someone or something");
//                articleDto.setContent("Not all who wander are lost");
//                Optional<ArticleDto> ofResult = Optional.<ArticleDto>of(articleDto);
//                when(this.articleRepository.findById((Integer) any())).thenReturn(ofResult);
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/articles/{id}", 1);
//                MockMvcBuilders.standaloneSetup(this.articleController).build().perform(requestBuilder)
//                                .andExpect(MockMvcResultMatchers.status().isOk())
//                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                                .andExpect(MockMvcResultMatchers.content().string(
//                                                "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"title\":\"Dr\",\"category\":\"Fiction\",\"publicationDate"
//                                                                + "\":0,\"content\":\"Not all who wander are lost\",\"description\":\"The characteristics of someone or"
//                                                                + " something\"}"));
//        }
//
//        @Test
//        void testGetArticle2() throws Exception {
//                when(this.articleRepository.findById((Integer) any()))
//                                .thenThrow(new SomethingWentWrongException("An error occurred"));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/articles/{id}", 1);
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticle3() throws Exception {
//                when(this.articleRepository.findById((Integer) any())).thenReturn(Optional.<ArticleDto>empty());
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/articles/{id}", 1);
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByAuthorEmail() throws Exception {
//                when(this.articleRepository.findByEmail((String) any()))
//                                .thenReturn(Optional.<List<ArticleDto>>of(new ArrayList<ArticleDto>()));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/article/author/{email}",
//                                "jane.doe@example.org");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByAuthorEmail2() throws Exception {
//                when(this.articleRepository.findByEmail((String) any()))
//                                .thenThrow(new SomethingWentWrongException("An error occurred"));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/article/author/{email}",
//                                "jane.doe@example.org");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByAuthorEmail3() throws Exception {
//                ArticleDto articleDto = new ArticleDto();
//                articleDto.setCategory(Category.Fiction);
//                articleDto.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto.setId(1);
//                articleDto.setName("?");
//                articleDto.setTitle("Dr");
//                articleDto.setDescription("The characteristics of someone or something");
//                articleDto.setContent("Not all who wander are lost");
//
//                ArrayList<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
//                articleDtoList.add(articleDto);
//                Optional<List<ArticleDto>> ofResult = Optional.<List<ArticleDto>>of(articleDtoList);
//                when(this.articleRepository.findByEmail((String) any())).thenReturn(ofResult);
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/article/author/{email}",
//                                "jane.doe@example.org");
//                MockMvcBuilders.standaloneSetup(this.articleController).build().perform(requestBuilder)
//                                .andExpect(MockMvcResultMatchers.status().isOk())
//                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                                .andExpect(MockMvcResultMatchers.content().string(
//                                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"title\":\"Dr\",\"category\":\"Fiction\",\"publicationDate"
//                                                                + "\":0,\"content\":\"Not all who wander are lost\",\"description\":\"The characteristics of someone or"
//                                                                + " something\"}]"));
//        }
//
//        @Test
//        void testGetArticlesByAuthorEmail4() throws Exception {
//                when(this.articleRepository.findByTitle((String) any()))
//                                .thenReturn(Optional.<List<ArticleDto>>of(new ArrayList<ArticleDto>()));
//                when(this.articleRepository.findByEmail((String) any())).thenReturn(Optional.<List<ArticleDto>>empty());
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/article/author/{email}",
//                                "", "Uri Vars");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByCategory() throws Exception {
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//                                .get("/v1/category/{category}/articles", "Category");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByCategory2() throws Exception {
//                MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/v1/category/{category}/articles",
//                                "Category");
//                getResult.contentType("Not all who wander are lost");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(getResult);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByCategoryLinks() throws Exception {
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/{category}", "Category");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByCategoryLinks2() throws Exception {
//                MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/v1/{category}", "Category");
//                getResult.contentType("Not all who wander are lost");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(getResult);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByEmailInAllCategories() throws Exception {
//                when(this.articleRepository.findByCategoryAndEmail(
//                                (com.mini_project.e_article_library.model.Category) any(), (String) any()))
//                                                .thenThrow(new SomethingWentWrongException("An error occurred"));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/articles/{email}/all",
//                                "jane.doe@example.org");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByEmailInAllCategories4() throws Exception {
//                when(this.articleRepository.findByCategory((com.mini_project.e_article_library.model.Category) any()))
//                                .thenThrow(new SomethingWentWrongException("An error occurred"));
//                when(this.articleRepository.findByCategoryAndEmail(
//                                (com.mini_project.e_article_library.model.Category) any(), (String) any()))
//                                                .thenReturn(Optional.<List<ArticleDto>>of(new ArrayList<ArticleDto>()));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/articles/{email}/all",
//                                "", "Uri Vars");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByName() throws Exception {
//                when(this.articleRepository.findByName((String) any()))
//                                .thenReturn(Optional.<List<ArticleDto>>of(new ArrayList<ArticleDto>()));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/author/{name}/articles",
//                                "Name");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByName2() throws Exception {
//                when(this.articleRepository.findByName((String) any()))
//                                .thenThrow(new SomethingWentWrongException("An error occurred"));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/author/{name}/articles",
//                                "Name");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByName3() throws Exception {
//                ArticleDto articleDto = new ArticleDto();
//                articleDto.setCategory(Category.Fiction);
//                articleDto.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto.setId(1);
//                articleDto.setName("?");
//                articleDto.setTitle("Dr");
//                articleDto.setDescription("The characteristics of someone or something");
//                articleDto.setContent("Not all who wander are lost");
//
//                ArrayList<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
//                articleDtoList.add(articleDto);
//                Optional<List<ArticleDto>> ofResult = Optional.<List<ArticleDto>>of(articleDtoList);
//                when(this.articleRepository.findByName((String) any())).thenReturn(ofResult);
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/author/{name}/articles",
//                                "Name");
//                MockMvcBuilders.standaloneSetup(this.articleController).build().perform(requestBuilder)
//                                .andExpect(MockMvcResultMatchers.status().isOk())
//                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                                .andExpect(MockMvcResultMatchers.content().string(
//                                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"title\":\"Dr\",\"category\":\"Fiction\",\"publicationDate"
//                                                                + "\":0,\"content\":\"Not all who wander are lost\",\"description\":\"The characteristics of someone or"
//                                                                + " something\"}]"));
//        }
//
//        @Test
//        void testGetArticlesByTitle() throws Exception {
//                when(this.articleRepository.findByTitle((String) any()))
//                                .thenReturn(Optional.<List<ArticleDto>>of(new ArrayList<ArticleDto>()));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/article/{title}", "Dr");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByTitle2() throws Exception {
//                when(this.articleRepository.findByTitle((String) any()))
//                                .thenThrow(new SomethingWentWrongException("An error occurred"));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/article/{title}", "Dr");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesByTitle3() throws Exception {
//                ArticleDto articleDto = new ArticleDto();
//                articleDto.setCategory(Category.Fiction);
//                articleDto.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto.setId(1);
//                articleDto.setName("?");
//                articleDto.setTitle("Dr");
//                articleDto.setDescription("The characteristics of someone or something");
//                articleDto.setContent("Not all who wander are lost");
//
//                ArrayList<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
//                articleDtoList.add(articleDto);
//                Optional<List<ArticleDto>> ofResult = Optional.<List<ArticleDto>>of(articleDtoList);
//                when(this.articleRepository.findByTitle((String) any())).thenReturn(ofResult);
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/article/{title}", "Dr");
//                MockMvcBuilders.standaloneSetup(this.articleController).build().perform(requestBuilder)
//                                .andExpect(MockMvcResultMatchers.status().isOk())
//                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                                .andExpect(MockMvcResultMatchers.content().string(
//                                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"title\":\"Dr\",\"category\":\"Fiction\",\"publicationDate"
//                                                                + "\":0,\"content\":\"Not all who wander are lost\",\"description\":\"The characteristics of someone or"
//                                                                + " something\"}]"));
//        }
//
//        @Test
//        void testGetArticlesByTitle4() throws Exception {
//                when(this.articleRepository.findByTitle((String) any())).thenReturn(Optional.<List<ArticleDto>>empty());
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/article/{title}", "",
//                                "Uri Vars");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testGetArticlesInAllCategories() throws Exception {
//                when(this.articleRepository.findByCategory((com.mini_project.e_article_library.model.Category) any()))
//                                .thenThrow(new SomethingWentWrongException("An error occurred"));
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/articles/all");
//                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.articleController).build()
//                                .perform(requestBuilder);
//                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        void testUpdateArticle() throws Exception {
//                ArticleDto articleDto = new ArticleDto();
//                articleDto.setCategory(Category.Fiction);
//                articleDto.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto.setPublicationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto.setId(1);
//                articleDto.setName("Name");
//                articleDto.setTitle("Dr");
//                articleDto.setDescription("The characteristics of someone or something");
//                articleDto.setContent("Not all who wander are lost");
//                Optional<ArticleDto> ofResult = Optional.<ArticleDto>of(articleDto);
//
//                ArticleDto articleDto1 = new ArticleDto();
//                articleDto1.setCategory(Category.Fiction);
//                articleDto1.setEmail("jane.doe@example.org");
//                LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//                articleDto1.setPublicationDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//                articleDto1.setId(1);
//                articleDto1.setName("Name");
//                articleDto1.setTitle("Dr");
//                articleDto1.setDescription("The characteristics of someone or something");
//                articleDto1.setContent("Not all who wander are lost");
//                when(this.articleRepository.save((ArticleDto) any())).thenReturn(articleDto1);
//                when(this.articleRepository.findById((Integer) any())).thenReturn(ofResult);
//
//                Article article = new Article();
//                article.setCategory(Category.Fiction);
//                article.setEmail("jane.doe@example.org");
//                article.setName("Name");
//                article.setTitle("Dr");
//                article.setDescription("The characteristics of someone or something");
//                article.setContent("Not all who wander are lost");
//                String content = (new ObjectMapper()).writeValueAsString(article);
//                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/articles/{id}", 1)
//                                .contentType(MediaType.APPLICATION_JSON).content(content);
//                MockMvcBuilders.standaloneSetup(this.articleController).build().perform(requestBuilder)
//                                .andExpect(MockMvcResultMatchers.status().isOk())
//                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                                .andExpect(MockMvcResultMatchers.content().string(
//                                                "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"title\":\"Dr\",\"category\":\"Fiction\",\"publicationDate"
//                                                                + "\":0,\"content\":\"Not all who wander are lost\",\"description\":\"The characteristics of someone or"
//                                                                + " something\"}"));
//        }
//}
