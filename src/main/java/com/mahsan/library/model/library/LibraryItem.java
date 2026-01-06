package com.mahsan.library.model.library;

import com.mahsan.library.model.base.Status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class LibraryItem {
    final protected LibraryItemType itemType;
    final protected String itemTypeStr;
    final protected String title;
    protected Status status;
    protected LocalDate returnDate;
    final public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public LibraryItem(LibraryItemType itemType , String title , Status status){
        this.itemType = itemType;
        this.itemTypeStr = itemType.getTypeStr();
        this.title = title;
        this.status = status;
        this.returnDate = null;
    }

    public LibraryItem(LibraryItemType itemType , String title , Status status , LocalDate returnDate){
        this.itemType = itemType;
        this.itemTypeStr = itemType.getTypeStr();
        this.title = title;
        this.status = status;
        this.returnDate = returnDate;
    }

    public LibraryItemType getItemType(){
        return itemType;
    }

    public String getTitle(){
        return title;
    }

    public Status getStatus(){
        return status;
    }

    public LocalDate getReturnDate(){
        return returnDate;
    }

    public String getReturnDateStr(){
        return returnDate.format(dateTimeFormatter);
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void setReturnDate(LocalDate returnDate){
        this.returnDate = returnDate;
    }

    public abstract void display();
}
