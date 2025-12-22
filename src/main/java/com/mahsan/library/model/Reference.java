package com.mahsan.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class Reference extends LibraryItem implements HasPublisher{
    private final String category;
    private final String publisher;

    public Reference(String title , String category , String publisher , Status status){
        super("Reference" , title , status);
        this.category = category;
        this.publisher = publisher;
    }

    public Reference(String title , String category , String publisher , Status status , LocalDate returnDate){
        super("Reference" , title , status , returnDate);
        this.category = category;
        this.publisher = publisher;
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
        return "(type : %s , title : %s , category : %s , publisher : %s , status : %s)"
                .formatted(itemTypeStr , title, category, publisher, status);
    }

    @Override
    public void display() {
        System.out.printf(
                "type : %s , title : %s , category : %s , publisher : %s , status : %s%n",
                itemTypeStr , title , category , publisher , status);
    }
}
