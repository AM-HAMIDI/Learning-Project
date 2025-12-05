package com.mahsan.library.model;

import java.util.Objects;

public class Book {
    final private String title;
    final private String author;
    final private int releaseYear;
    private Status status;

    public Book(String title , String author , int releaseYear , Status status){
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        setStatus(status);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof Book book)) return false;
        else if(object == this) return true;
        else {
            return book.getTitle().equals(title) && book.getAuthor().equals(author)
                    && book.getReleaseYear() == releaseYear && book.getStatus().equals(status);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(title , author , releaseYear , status);
    }

    @Override
    public String toString() {
        return "(title : %s , author : %s , releaseYear : %d , status : %s)"
                .formatted(title , author , releaseYear , status);
    }
}
