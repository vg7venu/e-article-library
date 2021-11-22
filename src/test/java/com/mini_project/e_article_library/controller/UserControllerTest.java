package com.mini_project.e_article_library.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini_project.e_article_library.exception.UserNotFoundException;
import com.mini_project.e_article_library.jpa.model.Roles;
import com.mini_project.e_article_library.jpa.model.User;
import com.mini_project.e_article_library.repository.UserRepository;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setPassword("iloveyou");
        user.setUserName("janedoe");
        user.setFullName("Dr Jane Doe");
        user.setId(1);
        user.setEnabled(true);
        user.setRoles(new HashSet<Roles>());
        when(this.userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setPassword("iloveyou");
        user1.setUserName("janedoe");
        user1.setFullName("Dr Jane Doe");
        user1.setId(1);
        user1.setEnabled(true);
        user1.setRoles(new HashSet<Roles>());
        String content = (new ObjectMapper()).writeValueAsString(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"fullName\":\"Dr Jane Doe\",\"userName\":\"janedoe\",\"password\":\"iloveyou\",\"enabled\":true,"
                                        + "\"roles\":[]}"));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        User user = new User();
        user.setPassword("iloveyou");
        user.setUserName("janedoe");
        user.setFullName("Dr Jane Doe");
        user.setId(1);
        user.setEnabled(true);
        user.setRoles(new HashSet<Roles>());
        when(this.userRepository.findByUserName((String) any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/user/{email}",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"fullName\":\"Dr Jane Doe\",\"userName\":\"janedoe\",\"password\":\"iloveyou\",\"enabled\":true,"
                                        + "\"roles\":[]}"));
    }

    @Test
    void testGetUserByEmail2() throws Exception {
        when(this.userRepository.findByUserName((String) any())).thenThrow(new UserNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/user/{email}",
                "jane.doe@example.org");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

