package com.mahsan.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class Book extends LibraryItem implements HasAuthor{
    final private String author;
    final private int releaseYear;

    public Book(String title, String author, int releaseYear, Status status) {
        super(LibraryItemType.BOOK , title , status);
        this.author = author;
        this.releaseYear = releaseYear;
    }

    public Book(String title, String author, int releaseYear, Status status , LocalDate returnDate) {
        super(LibraryItemType.BOOK , title , status , returnDate);
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
        if(status == Status.BORROWED)
            return "(type : %s , title : %s , author : %s , releaseYear : %d , status : %s , returnDate : %s)"
                    .formatted(itemTypeStr , title, author, releaseYear, status , getReturnDateStr());
        else
            return "(type : %s , title : %s , author : %s , releaseYear : %d , status : %s)"
                .formatted(itemTypeStr , title, author, releaseYear, status);
    }

    @Override
    public void display() {
        if(status == Status.BORROWED)
            System.out.printf(
                "type : %s , title : %s , author : %s , releaseYear : %d , status : %s , returnDate : %s%n",
                itemTypeStr , title , author , releaseYear , status , getReturnDateStr());
        else
            System.out.printf(
                    "type : %s , title : %s , author : %s , releaseYear : %d , status : %s%n",
                    itemTypeStr , title , author , releaseYear , status);
    }
}
