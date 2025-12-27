package com.mahsan.library.app;

import com.mahsan.library.cli.*;
import com.mahsan.library.model.*;

import java.util.HashMap;

public class AppHandler {
    private AppManager systemManager;
    private CliManager cliManager;
    private Library library;
    private AllTypesHandler allTypesHandler;
    private HashMap<LibraryItemType, ItemHandler> itemHandlersMap = new HashMap<>();

    public AppHandler(AppManager systemManager, CliManager cliManager, Library library) {
        this.systemManager = systemManager;
        this.cliManager = cliManager;
        this.library = library;
        this.allTypesHandler = new AllTypesHandler(cliManager, library);
        initializeItemHandlersMap();
    }

    private void initializeItemHandlersMap() {
        itemHandlersMap.put(LibraryItemType.BOOK, new BookHandler(cliManager, library));
        itemHandlersMap.put(LibraryItemType.MAGAZINE, new MagazineHandler(cliManager, library));
        itemHandlersMap.put(LibraryItemType.REFERENCE, new ReferenceHandler(cliManager, library));
        itemHandlersMap.put(LibraryItemType.THESIS, new ThesisHandler(cliManager, library));
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
            case SEARCH -> commandResult = handleSearchCommand();
            case SORT -> commandResult = handleSortCommand();
            case BORROW -> commandResult = handleBorrowCommand();
            case RETURN -> commandResult = handleReturnCommand();
            case PRINT_BORROWED_ITEMS -> commandResult = handlePrintBorrowedItems();
            case EXIT -> commandResult = handleExitCommand();
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
        LibraryItemType itemType = getItemTypeFromUser(false);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";

        return itemHandlersMap.get(itemType).handleInsertItem();
    }

    private String handleRemoveCommand() {
        LibraryItemType itemType = getItemTypeFromUser(true);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if (itemType == LibraryItemType.ALL)
            return allTypesHandler.handleRemoveItem();
        else
            return itemHandlersMap.get(itemType).handleRemoveItem();
    }

    private String handleUpdateCommand() {
        LibraryItemType itemType = getItemTypeFromUser(false);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else
            return itemHandlersMap.get(itemType).handleUpdateItem();
    }

    private String handlePrintListCommand() {
        LibraryItemType itemType = getItemTypeFromUser(true);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if (itemType == LibraryItemType.ALL)
            return allTypesHandler.handlePrintItemsList();
        else
            return itemHandlersMap.get(itemType).handlePrintItemsList();
    }

    private String handleSearchCommand() {
        LibraryItemType itemType = getItemTypeFromUser(true);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if (itemType == LibraryItemType.ALL)
            return allTypesHandler.handleSearchItems();
        else
            return itemHandlersMap.get(itemType).handleSearchItems();
    }

    private String handleSortCommand() {
        // We Suppose that the sort operation is only for Book items
        return itemHandlersMap.get(LibraryItemType.BOOK).handleSortItems();
    }

    private String handleBorrowCommand(){
        LibraryItemType itemType = getItemTypeFromUser(false);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else
            return itemHandlersMap.get(itemType).handleBorrowItems();

    }

    private String handleReturnCommand(){
        LibraryItemType itemType = getItemTypeFromUser(false);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else
            return itemHandlersMap.get(itemType).handleReturnItems();
    }

    private String handlePrintBorrowedItems(){
        LibraryItemType itemType = getItemTypeFromUser(true);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if(itemType == LibraryItemType.ALL)
            return allTypesHandler.handlePrintBorrowedItems();
        else
            return itemHandlersMap.get(itemType).handlePrintBorrowedItems();
    }

    private String handleExitCommand() {
        systemManager.finishApp();
        return "system is finished!\n";
    }

    private LibraryItemType getItemTypeFromUser(boolean allOptions){
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(allOptions));
        return cliManager.getLibraryItemTypeOption(allOptions);
    }
}
