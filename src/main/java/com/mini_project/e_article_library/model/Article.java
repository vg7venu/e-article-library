package com.mini_project.e_article_library.model;

public class Article {
    private String title;
    private String name;
    private String email;
    private Category category;
    private String description;
    private String content;

    public Article(String title, String name, String email, Category category, String description, String content) {
        this.title = title;
        this.name = name;
        this.email = email;
        this.category = category;
        this.description = description;
        this.content = content;
    }

    public Article() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
