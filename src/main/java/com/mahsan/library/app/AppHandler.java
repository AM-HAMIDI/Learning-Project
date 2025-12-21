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

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";

        return itemHandlersMap.get(itemType).handleInsertItem();
    }

    private String handleRemoveCommand() {
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(true));
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption(true);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if (itemType == LibraryItemType.ALL)
            return allTypesHandler.handleRemoveItem();
        else
            return itemHandlersMap.get(itemType).handleRemoveItem();
    }

    private String handleUpdateCommand() {
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(false));
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption(false);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else
            return itemHandlersMap.get(itemType).handleUpdateItem();
    }

    private String handlePrintListCommand() {
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(true));
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption(true);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if (itemType == LibraryItemType.ALL)
            return allTypesHandler.handlePrintItemsList();
        else
            return itemHandlersMap.get(itemType).handlePrintItemsList();
    }

    private String handleSearchCommand() {
        System.out.println("Enter Type : ");
        System.out.println(cliManager.getLibraryItemTypeOptions(true));
        LibraryItemType itemType = cliManager.getLibraryItemTypeOption(true);

        if (itemType == LibraryItemType.INVALID_TYPE)
            return "type is invalid!\n";
        else if (itemType == LibraryItemType.ALL)
            return allTypesHandler.handleSearchItems();
        else
            return itemHandlersMap.get(itemType).handleSearchItems();
    }

    private String handleSortCommand() {
        return itemHandlersMap.get(LibraryItemType.BOOK).handleSortItems();
    }

    private String processExitCommand() {
        systemManager.finishSystem();
        return "system is finished!\n";
    }
}
