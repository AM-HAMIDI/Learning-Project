package com.mahsan.library.core.service;

import com.mahsan.library.common.Status;
import com.mahsan.library.core.container.GenericNode;
import com.mahsan.library.core.models.*;

import java.util.ArrayList;
import java.util.Comparator;

public class LibraryService {
    private Library library;

    public LibraryService(Library library){
        this.library = library;
    }

    public void initializeLibrary(String bookListsDirPath){

    }

    public void insertBook(Book book){
        library.getBooks().insert(book);
    }

    public void removeBook(Book book){
        library.getBooks().removeByKey(book);
    }

    public void updateBook(Book book , Status status){
        GenericNode<Book> node = library.getBooks().getHeadNode();
        while(node != null){
            if(node.getData().equals(book)){
                node.getData().setStatus(status);
                break;
            }else node = node.getNextNode();
        }
    }

    public void printBooksList(){
        library.getBooks().printList();
    }

    public Book searchBooksByTitle(String title){
        GenericNode<Book> node = library.getBooks().getHeadNode();
        while(node != null){
            if(node.getData().getTitle().equals(title)) return node.getData();
            else node = node.getNextNode();
        }
        return null;
    }

    public ArrayList<Book> searchBooksByAuthor(String author){
        ArrayList<Book> authorBooks = new ArrayList<>();
        GenericNode<Book> node = library.getBooks().getHeadNode();
        while(node != null){
            if(node.getData().getAuthor().equals(author)) authorBooks.add(node.getData());
            node = node.getNextNode();
        }
        return authorBooks;
    }

    public ArrayList<Book> sortBooksByReleaseYear(){
        ArrayList<Book> sortedBooks = library.getBooks().getKeysArrayList();
        sortedBooks.sort(Comparator.comparingInt(Book::getReleaseYear));
        return sortedBooks;
    }
}
