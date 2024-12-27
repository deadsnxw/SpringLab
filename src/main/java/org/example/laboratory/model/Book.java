package org.example.laboratory.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class Book {
    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private List<String> keywords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
