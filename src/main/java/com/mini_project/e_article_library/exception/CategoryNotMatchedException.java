package com.mini_project.e_article_library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CategoryNotMatchedException extends RuntimeException {

    public CategoryNotMatchedException(String message) {
        super(message);
    }

}
