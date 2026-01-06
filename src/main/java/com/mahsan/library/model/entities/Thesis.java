package com.mahsan.library.model.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.io.JsonHandler;
import com.mahsan.library.model.base.HasAuthor;
import com.mahsan.library.model.base.Status;
import com.mahsan.library.model.library.LibraryItem;
import com.mahsan.library.model.library.LibraryItemType;

import java.time.LocalDate;
import java.util.Objects;

public class Thesis extends LibraryItem implements HasAuthor {
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

    public static Thesis getFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode, "title");
        String author = JsonHandler.getProperty(jsonNode, "author");
        int defenseYear = Integer.parseInt(JsonHandler.getProperty(jsonNode, "defenseYear"));
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));

        if (status == Status.BORROWED) {
            if(!jsonNode.has("returnDate"))
                return null;
            String returnDateStr = JsonHandler.getProperty(jsonNode, "returnDate");
            LocalDate returnDate = LocalDate.parse(returnDateStr, LibraryItem.dateTimeFormatter);
            return new Thesis(title, author, defenseYear, status, returnDate);
        } else {
            return new Thesis(title, author, defenseYear, status);
        }
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
        if (status == Status.BORROWED)
            return "(type : %s , title : %s , author : %s , defenseYear : %d , status : %s , returnDate : %s)"
                    .formatted(itemTypeStr, title, author, defenseYear, status, getReturnDateStr());
        else
            return "(type : %s , title : %s , author : %s , defenseYear : %d , status : %s)"
                    .formatted(itemTypeStr, title, author, defenseYear, status);
    }

    @Override
    public void display() {
        if (status == Status.BORROWED)
            System.out.printf(
                    "type : %s , title : %s , author : %s , defenseYear : %d , status : %s , returnDate : %s%n",
                    itemTypeStr, title, author, defenseYear, status, getReturnDateStr());
        else
            System.out.printf(
                    "type : %s , title : %s , author : %s , defenseYear : %d , status : %s%n",
                    itemTypeStr, title, author, defenseYear, status);
    }
}
