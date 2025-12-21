package com.mahsan.library.app;

import com.mahsan.library.cli.*;
import com.mahsan.library.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AppHandler {
    private AppManager systemManager;
    private CliManager cliManager;
    private Library library;
    private AllTypesHandler allTypesHandler;
    private HashMap<LibraryItemType , ItemHandler> itemHandlersMap = new HashMap<>();

    public AppHandler(AppManager systemManager, CliManager cliManager, Library library) {
        this.systemManager = systemManager;
        this.cliManager = cliManager;
        this.library = library;
        this.allTypesHandler = new AllTypesHandler(cliManager , library);
        initializeItemHandlersMap();
    }

    private void initializeItemHandlersMap(){
        itemHandlersMap.put(LibraryItemType.BOOK , new BookHandler(cliManager , library));
        itemHandlersMap.put(LibraryItemType.MAGAZINE , new MagazineHandler(cliManager , library));
        itemHandlersMap.put(LibraryItemType.REFERENCE , new ReferenceHandler(cliManager , library));
        itemHandlersMap.put(LibraryItemType.THESIS , new ThesisHandler(cliManager , library));
    }

    public void handleCommand(CommandMode commandMode) {
        String commandResult = "";
        switch (commandMode) {
            case INVALID_COMMAND -> commandResult = handleInvalidCommand();
            case HELP -> commandResult = handleHelpCommand();
            case INSERT -> commandResult = handleInsertCommand();
            case REMOVE -> commandResult = handleRemoveCommand();
            case UPDATE -> commandResult = handleUpdateCommand();
            case PRINT_LIST -> commandResult = handlePrintListCommand();
            case SEARCH -> commandResult = processSearchCommand();
            case SORT -> commandResult = processSortCommand();
            case EXIT -> commandResult = processExitCommand();
        }
        systemManager.AddToCommandHistory(commandMode, commandResult);
        systemManager.showCommandResult(commandResult);
    }

    private String handleInvalidCommand() {
        return cliManager.getInputError() + "\n";
    }

    private String handleHelpCommand() {
        return cliManager.getCommandModeOptions() + "\n";
    }

    private String handleInsertCommand() {
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(false));
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption(false);

        if(itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";

        return itemHandlersMap.get(itemType).handleInsertItem();
    }

    private String handleRemoveCommand() {
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(true));
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption(true);

        if(itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if(itemType == LibraryItemType.ALL)
            return allTypesHandler.handleRemoveItem();
        else
            return itemHandlersMap.get(itemType).handleRemoveItem();
    }

    private String handleUpdateCommand() {
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(false));
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption(false);

        if(itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else
            return itemHandlersMap.get(itemType).handleUpdateItem();
    }

    private String handlePrintListCommand() {
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(true));
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption(true);

        if(itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if(itemType == LibraryItemType.ALL)
            return allTypesHandler.handleRemoveItem();
        else
            return itemHandlersMap.get(itemType).handleRemoveItem();
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
