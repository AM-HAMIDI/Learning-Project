package com.mahsan.library.core.models;

import com.mahsan.library.common.Status;
import com.mahsan.library.core.container.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Library {
    private GenericLinkedList<Book> books;

    public Library(){
        books = new GenericLinkedList<>();
    }

    public void insertBook(Book book){
        books.insert(book);
    }

    public void removeBook(Book book){
        books.removeByKey(book);
    }

    public void updateBook(Book book , Status status){
        GenericNode<Book> node = books.getHeadNode();
        while(node != null){
            if(node.getData().equals(book)){
                node.getData().setStatus(status);
                break;
            }else node = node.getNextNode();
        }
    }

    public void printBooksList(){
        books.printList();
    }

    public Book searchBooksByTitle(String title){
        GenericNode<Book> node = books.getHeadNode();
        while(node != null){
            if(node.getData().getTitle().equals(title)) return node.getData();
            else node = node.getNextNode();
        }
        return null;
    }

    public ArrayList<Book> searchBooksByAuthor(String author){
        ArrayList<Book> authorBooks = new ArrayList<>();
        GenericNode<Book> node = books.getHeadNode();
        while(node != null){
            if(node.getData().getAuthor().equals(author)) authorBooks.add(node.getData());
            node = node.getNextNode();
        }
        return authorBooks;
    }

    public ArrayList<Book> sortBooksByReleaseYear(){
        ArrayList<Book> sortedBooks = books.getKeysArrayList();
        sortedBooks.sort(Comparator.comparingInt(Book::getReleaseYear));
        return sortedBooks;
    }
}
