package com.mahsan.library.core.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.common.Status;
import com.mahsan.library.config.ConfigManager;
import com.mahsan.library.core.container.GenericNode;
import com.mahsan.library.core.models.*;
import com.mahsan.library.util.JsonHandler;

import java.util.ArrayList;
import java.util.Comparator;

public class LibraryService {
    private Library library;

    public LibraryService(Library library){
        this.library = library;
    }

    public void initializeLibrary(String bookListsFilePath){
        String bookListsAbsoluteFilePath = ConfigManager.getProjectRootPath() + bookListsFilePath;
        JsonHandler jsonHandler = new JsonHandler(bookListsAbsoluteFilePath);
        if(!jsonHandler.isJsonFileValid()){
            System.out.println("book lists file is not valid");
            return;
        }
        ArrayList<JsonNode> jsonNodes = jsonHandler.getArrayElements();
        for(JsonNode jsonNode : jsonNodes){
            String title = JsonHandler.getProperty(jsonNode , "title");
            String author = JsonHandler.getProperty(jsonNode , "author");
            int releaseYear = Integer.parseInt(JsonHandler.getProperty(jsonNode , "releaseYear"));
            Status status = Status.getStatus(JsonHandler.getProperty(jsonNode , "status"));
            insertBook(new Book(title , author , releaseYear , status));
        }
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
