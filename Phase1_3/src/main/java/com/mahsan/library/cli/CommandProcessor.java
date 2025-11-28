package com.mahsan.library.cli;

import com.mahsan.library.Main;
import com.mahsan.library.common.CommandMode;
import com.mahsan.library.common.Status;
import com.mahsan.library.config.ConfigManager;
import com.mahsan.library.core.models.Book;
import com.mahsan.library.core.service.LibraryService;
import com.mahsan.library.util.JsonHandler;

import java.util.ArrayList;

public class CommandProcessor {
    private CliManager cliManager;
    private LibraryService libraryService;

    public CommandProcessor(CliManager cliManager , LibraryService libraryService){
        this.cliManager = cliManager;
        this.libraryService = libraryService;
    }

    public void processCommandMode(CommandMode commandMode){
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
        finishCommand(commandMode , commandResult);
    }

    private String processInvalidCommand(){
        return cliManager.getInputError() + "\n";
    }

    private String processExitCommand(){
        Main.finishSystem();
        return "system is finished!\n";
    }

    private String processInsertCommand(){
        String title = cliManager.getInputTitle();
        if(title.isEmpty()) return "title is invalid!\n";

        String author = cliManager.getInputAuthor();
        if(author.isEmpty()) return "author is invalid!\n";

        int releaseYear = cliManager.getInputReleaseYear();
        if(releaseYear == -1) return "release year is invalid!\n";

        Status status = cliManager.getInputStatus();
        if(status == null) return "status is invalid!\n";

        libraryService.insertBook(new Book(title , author , releaseYear , status));
        return "Book added successfully!\n";
    }

    private String processRemoveCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()) return "title is invalid!\n";

        Book book = libraryService.searchBooksByTitle(title);
        if(book == null) return "This book doesn't exists\n";

        libraryService.removeBook(book);
        return "Book removed successfully!\n";
    }

    private String processUpdateCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()) return "title is invalid!\n";

        Status status = cliManager.getInputStatus();
        if(status == null) return "status is invalid!\n";

        Book book = libraryService.searchBooksByTitle(title);
        if(book == null) return "book not found!\n";

        libraryService.updateBook(book , status);
        return "Book updated successfully!\n";
    }

    private String processPrintBooksListCommand() {
        return "Books list :\n" + libraryService.getBooksStringList() + "\n";
    }

    private String processSearchBooksByTitleCommand() {
        String title = cliManager.getInputTitle();
        if(title.isEmpty()){
            return "title is invalid!\n";
        }
        Book book = libraryService.searchBooksByTitle(title);
        if(book == null) return "book not found!\n";
        return "found book : " + book + "\n";
    }

    private String processSearchBooksByAuthorCommand(){
        String author = cliManager.getInputAuthor();
        if(author.isEmpty()) return "author is invalid!\n";

        ArrayList<Book> authorBooks = libraryService.searchBooksByAuthor(author);
        if(authorBooks.isEmpty()) return "book not found!\n";

        StringBuilder result = new StringBuilder();
        result.append("author books : \n");
        for(Book book : authorBooks){
            result.append(book).append("\n");
        }
        return result.toString();
    }

    private String processSortBooksCommand(){
        ArrayList<Book> sortedBooks = libraryService.sortBooksByReleaseYear();
        if(sortedBooks.isEmpty()) return "library is empty!\n";

        StringBuilder result = new StringBuilder();
        result.append("sorted books list : \n");
        for(Book book : sortedBooks){
            result.append(book).append("\n");
        }
        return result.toString();
    }

    private void finishCommand(CommandMode commandMode , String result){
        String commandHistoryFilePath = ConfigManager.getInstance().getCommandHistoryAbsoluteFilePath();
        JsonHandler jsonHandler = new JsonHandler(commandHistoryFilePath);
        ArrayList<String[]> newEntry = new ArrayList<>();
        newEntry.add(new String[]{"index" , String.valueOf(ConfigManager.getInstance().getCommandsCount())});
        newEntry.add(new String[]{"command", commandMode.name()});
        newEntry.add(new String[]{"result" , result});
        jsonHandler.addArrayEntry(newEntry);
        ConfigManager.getInstance().incrementCommandsCount();
    }
}
