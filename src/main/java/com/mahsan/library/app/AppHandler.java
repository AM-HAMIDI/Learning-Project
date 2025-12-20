package com.mahsan.library.app;

import com.mahsan.library.cli.*;
import com.mahsan.library.model.*;

import java.util.ArrayList;

public class AppHandler {
    private AppManager systemManager;
    private CliManager cliManager;
    private Library library;

    public AppHandler(AppManager systemManager, CliManager cliManager, Library library) {
        this.systemManager = systemManager;
        this.cliManager = cliManager;
        this.library = library;
    }

    public void processCommand(CommandMode commandMode) {
        String commandResult = "";
        switch (commandMode) {
            case INVALID_COMMAND -> commandResult = processInvalidCommand();
            case HELP -> commandResult = processHelpCommand();
            case INSERT -> commandResult = processInsertCommand();
            case REMOVE -> commandResult = processRemoveCommand();
            case UPDATE -> commandResult = processUpdateCommand();
            case PRINT_LIST -> commandResult = processPrintListCommand();
            case SEARCH -> commandResult = processSearchCommand();
            case SORT -> commandResult = processSortCommand();
            case EXIT -> commandResult = processExitCommand();
        }
        systemManager.AddToCommandHistory(commandMode, commandResult);
        systemManager.showCommandResult(commandResult);
    }

    private String processInvalidCommand() {
        return cliManager.getInputError() + "\n";
    }

    private String processHelpCommand() {
        return cliManager.getCommandModeOptions() + "\n";
    }

    private String processInsertCommand() {
        System.out.println(cliManager.getLibraryItemTypeOptions());
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption();

        if(itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";

        String title = cliManager.getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        String author = cliManager.getInputString("author");
        if (author.isEmpty())
            return "author is invalid!\n";

        int releaseYear = cliManager.getInputInteger("releaseYear");
        if (releaseYear == -1)
            return "release year is invalid!\n";

        Status status = cliManager.getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        library.insertBook(new Book(title, author, releaseYear, status));
        return "Book added successfully!\n";
    }

    private String processRemoveCommand() {
        String title = cliManager.getInputTitle();
        if (title.isEmpty())
            return "title is invalid!\n";

        Book book = library.searchBooksByTitle(title);
        if (book == null)
            return "This book doesn't exists\n";

        library.removeBook(book);
        return "Book removed successfully!\n";
    }

    private String processUpdateCommand() {
        String title = cliManager.getInputTitle();
        if (title.isEmpty())
            return "title is invalid!\n";

        Status status = cliManager.getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        Book book = library.searchBooksByTitle(title);
        if (book == null)
            return "book not found!\n";

        library.updateBook(book, status);
        return "Book updated successfully!\n";
    }

    private String processPrintBooksListCommand() {
        return "Books list :\n" + library.getBooksStringList() + "\n";
    }

    private String processSearchBooksByTitleCommand() {
        String title = cliManager.getInputTitle();
        if (title.isEmpty()) {
            return "title is invalid!\n";
        }
        Book book = library.searchBooksByTitle(title);
        if (book == null)
            return "book not found!\n";
        return "found book : " + book + "\n";
    }

    private String processSearchBooksByAuthorCommand() {
        String author = cliManager.getInputAuthor();
        if (author.isEmpty())
            return "author is invalid!\n";

        ArrayList<Book> authorBooks = library.searchBooksByAuthor(author);
        if (authorBooks.isEmpty())
            return "book not found!\n";

        StringBuilder result = new StringBuilder();
        result.append("author books : \n");
        for (Book book : authorBooks) {
            result.append(book).append("\n");
        }
        return result.toString();
    }

    private String processSortBooksCommand() {
        ArrayList<Book> sortedBooks = library.sortBooksByReleaseYear();
        if (sortedBooks.isEmpty())
            return "library is empty!\n";

        StringBuilder result = new StringBuilder();
        result.append("sorted books list : \n");
        for (Book book : sortedBooks) {
            result.append(book).append("\n");
        }
        return result.toString();
    }

    private String processExitCommand() {
        systemManager.finishSystem();
        return "system is finished!\n";
    }
}
