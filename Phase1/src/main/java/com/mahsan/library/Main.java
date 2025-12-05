package com.mahsan.library;

import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.cli.*;
import com.mahsan.library.model.*;
import com.mahsan.library.io.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
    private static final String projectRootPath = System.getProperty("user.dir");
    private static final String configFilePath = projectRootPath + "/config/config.json";
    private static final JsonHandler configJsonHandler = new JsonHandler(configFilePath);
    private static final String commandHistoryFilePath = projectRootPath + configJsonHandler.getProperty("CommandHistory");
    private static final JsonHandler commandHistoryJsonHandler = new JsonHandler(commandHistoryFilePath);
    private static final String bookListFilePath = projectRootPath + configJsonHandler.getProperty("BookListsFilePath");
    private static final JsonHandler libraryJsonHandler = new JsonHandler(bookListFilePath);

    private static boolean isSystemRunning = true;
    private static int commandsCount = 1;
    private static CliManager cliManager = new CliManager();
    private static Library library = new Library();

    public static void main(String[] args) {
        cleanCommandHistoryFile();
        initializeLibrary();
        cliManager.showUI();
        while(isSystemRunning){
            processCommand(cliManager.getCommandMode());
        }
    }

    private static void cleanCommandHistoryFile(){
        try{
            PrintWriter printWriter = new PrintWriter(commandHistoryFilePath);
            printWriter.write("");
            printWriter.close();
        }catch (IOException exception){}
    }

    private static void initializeLibrary(){
        if(!libraryJsonHandler.isJsonFileValid()){
            System.out.println("book lists file is not valid");
            return;
        }
        ArrayList<JsonNode> jsonNodes = libraryJsonHandler.getArrayElements();
        for(JsonNode jsonNode : jsonNodes){
            String title = JsonHandler.getProperty(jsonNode , "title");
            String author = JsonHandler.getProperty(jsonNode , "author");
            int releaseYear = Integer.parseInt(JsonHandler.getProperty(jsonNode , "releaseYear"));
            Status status = Status.getStatus(JsonHandler.getProperty(jsonNode , "status"));
            library.insertBook(new Book(title , author , releaseYear , status));
        }
    }

    private static void processCommand(CommandMode commandMode){
        String commandResult = "";
        switch (commandMode){
            case INVALID_COMMAND -> commandResult = processInvalidCommand();
            case INSERT_BOOK -> commandResult = processInsertCommand();
            case REMOVE_BOOK -> commandResult = processRemoveCommand();
            case UPDATE_BOOK -> commandResult = processUpdateCommand();
            case PRINT_BOOKS_LIST -> commandResult = processPrintBooksListCommand();
            case SEARCH_BOOKS_BY_TITLE -> commandResult = processSearchBooksByTitleCommand();
            case SEARCH_BOOKS_BY_AUTHOR -> commandResult = processSearchBooksByAuthorCommand();
            case SORT_BOOKS -> commandResult = processSortBooksCommand();
            case EXIT -> commandResult = processExitCommand();
        }
        updateCommandHistory(commandMode , commandResult);
        commandsCount++;
    }

    private static String processInvalidCommand(){
        return cliManager.getInputError() + "\n";
    }

    private static String processInsertCommand(){
        String title = cliManager.getInputTitle();
        if(title.isEmpty()) return "title is invalid!\n";

        String author = cliManager.getInputAuthor();
        if(author.isEmpty()) return "author is invalid!\n";

        int releaseYear = cliManager.getInputReleaseYear();
        if(releaseYear == -1) return "release year is invalid!\n";

        Status status = cliManager.getInputStatus();
        if(status == null) return "status is invalid!\n";

        library.insertBook(new Book(title , author , releaseYear , status));
        return "Book added successfully!\n";
    }

    private static String processRemoveCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()) return "title is invalid!\n";

        Book book = library.searchBooksByTitle(title);
        if(book == null) return "This book doesn't exists\n";

        library.removeBook(book);
        return "Book removed successfully!\n";
    }

    private static String processUpdateCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()) return "title is invalid!\n";

        Status status = cliManager.getInputStatus();
        if(status == null) return "status is invalid!\n";

        Book book = library.searchBooksByTitle(title);
        if(book == null) return "book not found!\n";

        library.updateBook(book , status);
        return "Book updated successfully!\n";
    }

    private static String processPrintBooksListCommand() {
        return "Books list :\n" + library.getBooksStringList() + "\n";
    }

    private static String processSearchBooksByTitleCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()){
            return "title is invalid!\n";
        }
        Book book = library.searchBooksByTitle(title);
        if(book == null) return "book not found!\n";
        return "found book : " + book + "\n";
    }

    private static String processSearchBooksByAuthorCommand(){
        String author = cliManager.getInputAuthor();
        if(author.isEmpty()) return "author is invalid!\n";

        ArrayList<Book> authorBooks = library.searchBooksByAuthor(author);
        if(authorBooks.isEmpty()) return "book not found!\n";

        StringBuilder result = new StringBuilder();
        result.append("author books : \n");
        for(Book book : authorBooks){
            result.append(book).append("\n");
        }
        return result.toString();
    }

    private static String processSortBooksCommand(){
        ArrayList<Book> sortedBooks = library.sortBooksByReleaseYear();
        if(sortedBooks.isEmpty()) return "library is empty!\n";

        StringBuilder result = new StringBuilder();
        result.append("sorted books list : \n");
        for(Book book : sortedBooks){
            result.append(book).append("\n");
        }
        return result.toString();
    }

    private static String processExitCommand(){
        finishSystem();
        return "system is finished!\n";
    }

    private static void finishSystem(){
        isSystemRunning = false;
    }

    private static void updateCommandHistory(CommandMode commandMode , String result){
        ArrayList<String[]> newEntry = new ArrayList<>();
        newEntry.add(new String[]{"index" , String.valueOf(commandsCount)});
        newEntry.add(new String[]{"command", commandMode.name()});
        newEntry.add(new String[]{"result" , result});
        commandHistoryJsonHandler.addArrayEntry(newEntry);
    }
}