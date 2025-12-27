package com.mahsan.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class Thesis extends LibraryItem implements HasAuthor{
    private final String author;
    private final int defenseYear;

    public Thesis(String title, String author, int defenseYear, Status status) {
        super(LibraryItemType.THESIS , title, status);
        this.author = author;
        this.defenseYear = defenseYear;
    }

    public Thesis(String title, String author, int defenseYear, Status status , LocalDate returnDate) {
        super(LibraryItemType.THESIS , title, status , returnDate);
        this.author = author;
        this.defenseYear = defenseYear;
    }

    public String getAuthor() {
        return author;
    }

    public int getDefenseYear() {
        return defenseYear;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Thesis thesis))
            return false;

        return title.equals(thesis.getTitle())
                && author.equals(thesis.getAuthor())
                && defenseYear == thesis.getDefenseYear()
                && status.equals(thesis.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, defenseYear, status);
    }

    @Override
    public String toString() {
        return "(type : %s , title : %s , author : %s , defenseYear : %d , status : %s)"
                .formatted(itemTypeStr , title, author, defenseYear, status);
    }

    @Override
    public void display() {
        System.out.printf(
                "type : %s , title : %s , author : %s , defenseYear : %d , status : %s%n",
                itemTypeStr , title, author, defenseYear, status
        );
    }
}
