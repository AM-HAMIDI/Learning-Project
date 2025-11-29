package com.mahsan.library.core.models;

import com.mahsan.library.core.container.*;

public class Library {
    private GenericLinkedList<Book> books;

    public Library(){
        books = new GenericLinkedList<>();
    }

    public GenericLinkedList<Book> getBooks(){
        return books;
    }

    public void setBooks(GenericLinkedList<Book> books){
        this.books = books;
    }
}
