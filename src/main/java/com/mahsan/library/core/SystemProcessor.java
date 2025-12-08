package com.mahsan.library.core;

import com.mahsan.library.cli.*;
import com.mahsan.library.model.*;

import java.util.ArrayList;

public class SystemProcessor {
    private SystemManager systemManager;
    private CliManager cliManager;
    private Library library;

    public SystemProcessor(SystemManager systemManager, CliManager cliManager, Library library) {
        this.systemManager = systemManager;
        this.cliManager = cliManager;
        this.library = library;
    }

    public void processCommand(CommandMode commandMode) {
        String commandResult = "";
        switch (commandMode) {
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
        systemManager.updateCommandHistory(commandMode, commandResult);
    }

    private String processInvalidCommand() {
        return cliManager.getInputError() + "\n";
    }

    private String processInsertCommand() {
        String title = cliManager.getInputTitle();
        if (title.isEmpty())
            return "title is invalid!\n";

        String author = cliManager.getInputAuthor();
        if (author.isEmpty())
            return "author is invalid!\n";

        int releaseYear = cliManager.getInputReleaseYear();
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
