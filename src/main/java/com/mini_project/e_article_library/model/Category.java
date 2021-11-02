package com.mini_project.e_article_library.model;

public enum Category {
    Fiction("fiction"), Non_Fiction("non_fiction"), Science("science"), Tech("tech"), Political("political"),
    Wildlife("wildlife");

    private final String category;

    private Category(String category) {
        this.category = category;
    }

}
