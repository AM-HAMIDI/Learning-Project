package com.mahsan.library.model;

public abstract class LibraryItem {
    final protected String title;
    protected Status status;

    public LibraryItem(String title , Status status){
        this.title = title;
        this.status = status;
    }

    public String getTitle(){
        return title;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public abstract void display();
}
