package com.mahsan.library.model;

import java.util.Objects;

public class Magazine extends LibraryItem {

    private final int issueNumber;
    private final String publisher;

    public Magazine(String title, int issueNumber, String publisher, Status status) {
        super(title, status);
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
        return "(title : %s , issueNumber : %d , publisher : %s , status : %s)"
                .formatted(title, issueNumber, publisher, status);
    }

    @Override
    public void display() {
        System.out.printf(
                "title : %s , issueNumber : %d , publisher : %s , status : %s%n",
                title, issueNumber, publisher, status
        );
    }
}
