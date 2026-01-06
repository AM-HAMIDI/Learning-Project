package com.mahsan.library.model.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.io.JsonHandler;
import com.mahsan.library.model.base.HasPublisher;
import com.mahsan.library.model.base.Status;
import com.mahsan.library.model.library.LibraryItem;
import com.mahsan.library.model.library.LibraryItemType;

import java.time.LocalDate;
import java.util.Objects;

public class Magazine extends LibraryItem implements HasPublisher {

    private final int issueNumber;
    private final String publisher;

    public Magazine(String title, int issueNumber, String publisher, Status status) {
        super(LibraryItemType.MAGAZINE , title, status);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public Magazine(String title, int issueNumber, String publisher, Status status , LocalDate returnDate) {
        super(LibraryItemType.MAGAZINE  , title, status , returnDate);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public static Magazine getFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode , "title");
        int issueNumber = Integer.parseInt(JsonHandler.getProperty(jsonNode , "issueNumber"));
        String publisher = JsonHandler.getProperty(jsonNode , "publisher");
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));

        if (status == Status.BORROWED) {
            if(!jsonNode.has("returnDate"))
                return null;
            String returnDateStr = JsonHandler.getProperty(jsonNode, "returnDate");
            LocalDate returnDate = LocalDate.parse(returnDateStr, LibraryItem.dateTimeFormatter);
            return new Magazine(title, issueNumber, publisher, status, returnDate);
        } else {
            return new Magazine(title, issueNumber, publisher, status);
        }
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Magazine magazine))
            return false;

        return title.equals(magazine.getTitle())
                && issueNumber == magazine.getIssueNumber()
                && publisher.equals(magazine.getPublisher())
                && status.equals(magazine.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, issueNumber, publisher, status);
    }

    @Override
    public String toString() {
        if (status == Status.BORROWED)
            return "(type : %s , title : %s , issueNumber : %d , publisher : %s , status : %s , returnDate : %s)"
                    .formatted(itemTypeStr, title, issueNumber, publisher, status, getReturnDateStr());
        else
            return "(type : %s , title : %s , issueNumber : %d , publisher : %s , status : %s)"
                    .formatted(itemTypeStr, title, issueNumber, publisher, status);
    }

    @Override
    public void display() {
        if (status == Status.BORROWED)
            System.out.printf(
                    "type : %s , title : %s , issueNumber : %d , publisher : %s , status : %s , returnDate : %s%n",
                    itemTypeStr, title, issueNumber, publisher, status, getReturnDateStr());
        else
            System.out.printf(
                    "type : %s , title : %s , issueNumber : %d , publisher : %s , status : %s%n",
                    itemTypeStr, title, issueNumber, publisher, status);
    }
}
