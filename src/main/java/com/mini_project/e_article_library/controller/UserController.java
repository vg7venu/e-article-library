package com.mini_project.e_article_library.controller;

import com.mini_project.e_article_library.exception.UserNotFoundException;
import com.mini_project.e_article_library.jpa.model.User;
import com.mini_project.e_article_library.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/user/validate")
    public ResponseEntity<String> validateUser(@RequestParam String email) {
        User user;
        try {
            user = getUserByEmail(email).getBody();
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with given email:" + email);
        }
        try {
            if (user.getRole().equals("Author")) {
                return new ResponseEntity<String>(user.getFirstName() + user.getLastName() + "is an Author",
                        HttpStatus.FOUND);
            } else {
                return new ResponseEntity<String>(user.getFirstName() + user.getLastName() + "is a Reader",
                        HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with given email:" + email);
        }
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

}
