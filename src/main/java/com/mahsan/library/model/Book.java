package com.mahsan.library.model;

import java.util.Objects;

public class Book extends LibraryItem{
    final private String author;
    final private int releaseYear;

    public Book(String title, String author, int releaseYear, Status status) {
        super(title , status);
        this.author = author;
        this.releaseYear = releaseYear;
    }

    public String getAuthor() {
        return author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Book book))
            return false;
        else return book.getTitle().equals(title) && book.getAuthor().equals(author)
                    && book.getReleaseYear() == releaseYear && book.getStatus().equals(status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, releaseYear, status);
    }

    @Override
    public String toString() {
        return "(title : %s , author : %s , releaseYear : %d , status : %s)"
                .formatted(title, author, releaseYear, status);
    }

    @Override
    public void display() {
        System.out.printf(
                "title : %s , author : %s , releaseYear : %d , status : %s%n",
                title , author , releaseYear , status);
    }
}
