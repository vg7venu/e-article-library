package com.mini_project.e_article_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EArticleLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EArticleLibraryApplication.class, args);
	}

}
