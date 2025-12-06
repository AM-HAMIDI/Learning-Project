package com.mahsan.library.model;

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
        Book oldBook = new Book(book.getTitle() , book.getAuthor() , book.getReleaseYear() , book.getStatus());
        GenericNode<Book> node = books.getHeadNode();
        while(node != null){
            if(node.getData().equals(oldBook)) node.getData().setStatus(status);
            node = node.getNextNode();
        }
    }

    public String getBooksStringList(){
        StringBuilder list = new StringBuilder();
        GenericNode<Book> node = books.getHeadNode();
        while(node != null){
            list.append(node).append("\n");
            node = node.getNextNode();
        }
        return list.toString();
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
        ArrayList<Book> sortedBooks = getBooksArrayList();
        sortedBooks.sort(Comparator.comparingInt(Book::getReleaseYear));
        return sortedBooks;
    }

    public GenericLinkedList<Book> getBooks(){
        return books;
    }

    public ArrayList<Book> getBooksArrayList(){
        ArrayList<Book> booksArrayList = new ArrayList<>();
        GenericNode<Book> node = books.getHeadNode();
        while(node != null){
            booksArrayList.add(node.getData());
            node = node.getNextNode();
        }
        return booksArrayList;
    }
}