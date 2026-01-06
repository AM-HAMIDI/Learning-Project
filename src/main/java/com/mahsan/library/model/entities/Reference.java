package com.mahsan.library.model.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.io.JsonHandler;
import com.mahsan.library.model.base.HasPublisher;
import com.mahsan.library.model.base.Status;
import com.mahsan.library.model.library.LibraryItem;
import com.mahsan.library.model.library.LibraryItemType;

import java.time.LocalDate;
import java.util.Objects;

public class Reference extends LibraryItem implements HasPublisher {
    private final String category;
    private final String publisher;

    public Reference(String title , String category , String publisher , Status status){
        super(LibraryItemType.REFERENCE , title , status);
        this.category = category;
        this.publisher = publisher;
    }

    public Reference(String title , String category , String publisher , Status status , LocalDate returnDate){
        super(LibraryItemType.REFERENCE  , title , status , returnDate);
        this.category = category;
        this.publisher = publisher;
    }

    public static Reference getFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode , "title");
        String category = JsonHandler.getProperty(jsonNode , "category");
        String publisher = JsonHandler.getProperty(jsonNode , "publisher");
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));

        if (status == Status.BORROWED) {
            if(!jsonNode.has("returnDate"))
                return null;
            String returnDateStr = JsonHandler.getProperty(jsonNode, "returnDate");
            LocalDate returnDate = LocalDate.parse(returnDateStr, LibraryItem.dateTimeFormatter);
            return new Reference(title, category, publisher, status, returnDate);
        } else {
            return new Reference(title, category, publisher, status);
        }
    }

    public String getCategory(){
        return category;
    }

    public String getPublisher(){
        return publisher;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Reference reference))
            return false;
        else return reference.getTitle().equals(title) && reference.getStatus().equals(status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title , category , publisher , status);
    }

    @Override
    public String toString() {
        if (status == Status.BORROWED)
            return "(type : %s , title : %s , category : %s , publisher : %s , status : %s , returnDate : %s)"
                    .formatted(itemTypeStr, title, category, publisher, status, getReturnDateStr());
        else
            return "(type : %s , title : %s , category : %s , publisher : %s , status : %s)"
                    .formatted(itemTypeStr, title, category, publisher, status);
    }

    @Override
    public void display() {
        if (status == Status.BORROWED)
            System.out.printf(
                    "type : %s , title : %s , category : %s , publisher : %s , status : %s , returnDate : %s%n",
                    itemTypeStr, title, category, publisher, status, getReturnDateStr());
        else
            System.out.printf(
                    "type : %s , title : %s , category : %s , publisher : %s , status : %s%n",
                    itemTypeStr, title, category, publisher, status);
    }
}
