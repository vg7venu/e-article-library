package com.mini_project.e_article_library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class SomethingWentWrongException extends RuntimeException {

    public SomethingWentWrongException(String message) {
        super(message);
    }

}
