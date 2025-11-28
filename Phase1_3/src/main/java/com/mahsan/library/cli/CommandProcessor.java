package com.mahsan.library.cli;

import com.mahsan.library.common.CommandMode;
import com.mahsan.library.common.Status;
import com.mahsan.library.core.models.Book;
import com.mahsan.library.core.service.LibraryService;

import java.util.ArrayList;

public class CommandProcessor {
    private CliManager cliManager;
    private LibraryService libraryService;

    public CommandProcessor(CliManager cliManager , LibraryService libraryService){
        this.cliManager = cliManager;
        this.libraryService = libraryService;
    }

    public void processCommandMode(CommandMode commandMode){
        switch (commandMode){
            case INSERT_BOOK -> processInsertCommand();
            case REMOVE_BOOK -> processRemoveCommand();
            case UPDATE_BOOK -> processUpdateCommand();
            case PRINT_BOOKS_LIST -> processPrintBooksListCommand();
            case SEARCH_BOOKS_BY_TITLE -> processSearchBooksByTitleCommand();
            case SEARCH_BOOKS_BY_AUTHOR -> processSearchBooksByAuthorCommand();
            case SORT_BOOKS -> processSortBooksCommand();
        }
    }

    private void processInsertCommand(){
        String title = cliManager.getInputTitle();
        if(title.isEmpty()){
            System.out.println("title is invalid!\n");
            return;
        }
        String author = cliManager.getInputAuthor();
        if(author.isEmpty()){
            System.out.println("author is invalid!\n");
            return;
        }
        int releaseYear = cliManager.getInputReleaseYear();
        if(releaseYear == -1){
            System.out.println("release year is invalid!\n");
            return;
        }
        Status status = cliManager.getInputStatus();
        if(status == null){
            System.out.println("status is invalid!\n");
            return;
        }

        libraryService.insertBook(new Book(title , author , releaseYear , status));
        System.out.println("Book added successfully!\n");
    }

    private void processRemoveCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()){
            System.out.println("title is invalid!\n");
            return;
        }

        Book book = libraryService.searchBooksByTitle(title);
        if(book == null){
            System.out.println("This book doesn't exists\n");
            return;
        }

        libraryService.removeBook(book);
        System.out.println("Book removed successfully!\n");
    }

    private void processUpdateCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()){
            System.out.println("title is invalid!\n");
            return;
        }
        Status status = cliManager.getInputStatus();
        if(status == null){
            System.out.println("status is invalid!\n");
            return;
        }
        Book book = libraryService.searchBooksByTitle(title);
        if(book == null){
            System.out.println("book not found!\n");
            return;
        }
        libraryService.updateBook(book , status);
        System.out.println("Book updated successfully!\n");
    }

    private void processPrintBooksListCommand() {
        System.out.println("Books list :");
        libraryService.printBooksList();
        System.out.println();
    }

    private void processSearchBooksByTitleCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()){
            System.out.println("title is invalid!\n");
            return;
        }
        Book book = libraryService.searchBooksByTitle(title);
        if(book == null){
            System.out.println("book not found!\n");
            return;
        }
        System.out.println("found book : " + book + "\n");
    }

    private void processSearchBooksByAuthorCommand(){
        String author = cliManager.getInputAuthor();
        if(author.isEmpty()){
            System.out.println("author is invalid!\n");
            return;
        }
        ArrayList<Book> authorBooks = libraryService.searchBooksByAuthor(author);
        if(authorBooks.isEmpty()){
            System.out.println("book not found!\n");
            return;
        }
        System.out.println("author books : ");
        authorBooks.forEach(System.out::println);
        System.out.println();
    }

    private void processSortBooksCommand(){
        ArrayList<Book> sortedBooks = libraryService.sortBooksByReleaseYear();
        if(sortedBooks.isEmpty()){
            System.out.println("library is empty!\n");
            return;
        }
        System.out.println("sorted books list : ");
        sortedBooks.forEach(System.out::println);
        System.out.println();
    }
}
