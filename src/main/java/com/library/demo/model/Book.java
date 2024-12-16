package com.library.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

    private String isbn;
    private String title;
    private String author;
    private boolean available;
    // TODO: Implementar constructor, getters y setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
