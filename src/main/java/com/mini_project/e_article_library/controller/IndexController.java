package com.mini_project.e_article_library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/403")
    public String accessDenied() {
        return "access-denied";
    }

}
