package com.mahsan.library;

import com.mahsan.library.cli.*;
import com.mahsan.library.common.*;
import com.mahsan.library.core.models.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();
    private static CliManager cliManager = new CliManager();

    public static void main(String[] args) {
        startSystem();
    }

    //-------------------------------- Core --------------------------------
    public static void startSystem(){
        showUI();
        while(true){
            CommandMode commandMode = getCommandMode();
            if(CommandMode.isInvalidCommand(commandMode)) showInputError();
            else if(CommandMode.isTerminalCommandMode(commandMode)) break;
            else processCommandMode(commandMode);
        }
    }

    //-------------------------------- UI Menu --------------------------------
    public static void showUI(){
        String title = getUITitle();
        String options = getUIOptions();
        System.out.println("\n" + title + "\n" + options);
    }
    public static String getUITitle(){
        return "-".repeat(50) + " Library Management System " + "-".repeat(50);
    }

    public static String getUIOptions(){
        StringBuilder options = new StringBuilder();
        for(int i = 0 ; i < CommandMode.getCommandModes().length ; i++){
            options.append("[%d]".formatted(i + 1));
            options.append(CommandMode.getCommandModes()[i].getCommandStr()).append("\n");
        }
        return options.toString();
    }

    //-------------------------------- UI Command Mode --------------------------------
    public static CommandMode getCommandMode(){
        System.out.println("Choose an option [1-8]:");
        String input = scanner.nextLine().trim();
        try {
            return CommandMode.getFromInt(Integer.parseInt(input));
        }catch (NumberFormatException exception){
            return CommandMode.INVALID_COMMAND;
        }
    }

    public static void showInputError(){
        System.out.println("Invalid input!");
    }

    //-------------------------------- Process commands --------------------------------
    public static void processCommandMode(CommandMode commandMode){
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

    private static void processInsertCommand(){
        String title = getInputTitle();
        if(title.isEmpty()){
            System.out.println("title is invalid!\n");
            return;
        }
        String author = getInputAuthor();
        if(author.isEmpty()){
            System.out.println("author is invalid!\n");
            return;
        }
        int releaseYear = getInputReleaseYear();
        if(releaseYear == -1){
            System.out.println("release year is invalid!\n");
            return;
        }
        Status status = getInputStatus();
        if(status == null){
            System.out.println("status is invalid!\n");
            return;
        }

        library.insertBook(new Book(title , author , releaseYear , status));
        System.out.println("Book added successfully!\n");
    }

    private static void processRemoveCommand() {
        String title = getInputTitle();
        if(title.isEmpty()){
            System.out.println("title is invalid!\n");
            return;
        }

        Book book = library.searchBooksByTitle(title);
        if(book == null){
            System.out.println("This book doesn't exists\n");
            return;
        }

        library.removeBook(book);
        System.out.println("Book removed successfully!\n");
    }

    private static void processUpdateCommand() {
        String title = getInputTitle();
        if(title.isEmpty()){
            System.out.println("title is invalid!\n");
            return;
        }
        Status status = getInputStatus();
        if(status == null){
            System.out.println("status is invalid!\n");
            return;
        }
        Book book = library.searchBooksByTitle(title);
        if(book == null){
            System.out.println("book not found!\n");
            return;
        }
        library.updateBook(book , status);
        System.out.println("Book updated successfully!\n");
    }

    private static void processPrintBooksListCommand() {
        System.out.println("Books list :");
        library.printBooksList();
        System.out.println();
    }

    private static void processSearchBooksByTitleCommand() {
        String title = getInputTitle();
        if(title.isEmpty()){
            System.out.println("title is invalid!\n");
            return;
        }
        Book book = library.searchBooksByTitle(title);
        if(book == null){
            System.out.println("book not found!\n");
            return;
        }
        System.out.println("found book : " + book + "\n");
    }

    private static void processSearchBooksByAuthorCommand(){
        String author = getInputAuthor();
        if(author.isEmpty()){
            System.out.println("author is invalid!\n");
            return;
        }
        ArrayList<Book> authorBooks = library.searchBooksByAuthor(author);
        if(authorBooks.isEmpty()){
            System.out.println("book not found!\n");
            return;
        }
        System.out.println("author books : ");
        authorBooks.forEach(System.out::println);
        System.out.println();
    }

    private static void processSortBooksCommand(){
        ArrayList<Book> sortedBooks = library.sortBooksByReleaseYear();
        if(sortedBooks.isEmpty()){
            System.out.println("library is empty!\n");
            return;
        }
        System.out.println("sorted books list : ");
        sortedBooks.forEach(System.out::println);
        System.out.println();
    }

    //-------------------------------- UI command parameters --------------------------------
    private static String getInputTitle(){
        System.out.println("\nEnter book's title : ");
        return scanner.nextLine().trim();
    }

    private static String getInputAuthor(){
        System.out.println("\nEnter book's author : ");
        return scanner.nextLine().trim();
    }

    private static int getInputReleaseYear(){
        System.out.println("\nEnter book's release year : ");
        String releaseYearStr = scanner.nextLine().trim();
        try {
            return Integer.parseInt(releaseYearStr);
        } catch (NumberFormatException exception){
            return -1;
        }
    }

    private static Status getInputStatus(){
        System.out.println("\nEnter book's Status : Exist or Borrowed or Banned");
        String statusStr = scanner.nextLine().trim();
        return switch (statusStr) {
            case "Exist" -> Status.EXIST;
            case "Borrowed" -> Status.BORROWED;
            case "Banned" -> Status.BANNED;
            default -> null;
        };
    }
}