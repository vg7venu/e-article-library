package com.mini_project.e_article_library.model;

public class Article {
    private String title;
    private String name;
    private String category;
    private String description;
    private String content;

    public Article() {
    }

    public Article(String title, String name, String category, String description, String content) {
        this.title = title;
        this.name = name;
        this.category = category;
        this.description = description;
        this.content = content;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
