package com.mahsan.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class Magazine extends LibraryItem implements HasPublisher{

    private final int issueNumber;
    private final String publisher;

    public Magazine(String title, int issueNumber, String publisher, Status status) {
        super("Magazine" , title, status);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public Magazine(String title, int issueNumber, String publisher, Status status , LocalDate returnDate) {
        super("Magazine" , title, status , returnDate);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
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
        return "(type : %s , title : %s , issueNumber : %d , publisher : %s , status : %s)"
                .formatted(itemTypeStr , title, issueNumber, publisher, status);
    }

    @Override
    public void display() {
        System.out.printf(
                "type : %s , title : %s , issueNumber : %d , publisher : %s , status : %s%n",
                itemTypeStr , title, issueNumber, publisher, status
        );
    }
}
