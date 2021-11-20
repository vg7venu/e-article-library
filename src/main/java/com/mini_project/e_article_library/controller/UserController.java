package com.mini_project.e_article_library.controller;

import com.mini_project.e_article_library.exception.UserNotFoundException;
import com.mini_project.e_article_library.jpa.model.User;
import com.mini_project.e_article_library.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user;
        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with given email:" + email);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

}
