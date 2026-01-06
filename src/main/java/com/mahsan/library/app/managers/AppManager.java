package com.mahsan.library.app.managers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.app.handlers.AppHandler;
import com.mahsan.library.cli.*;
import com.mahsan.library.config.ConfigResolver;
import com.mahsan.library.io.*;
import com.mahsan.library.model.entities.Book;
import com.mahsan.library.model.entities.Magazine;
import com.mahsan.library.model.entities.Reference;
import com.mahsan.library.model.entities.Thesis;
import com.mahsan.library.model.library.Library;
import com.mahsan.library.model.library.LibraryItem;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AppManager {
    // App properties
    private final String configFilePath = ConfigResolver.getConfigFilePath();
    private final JsonHandler configJsonHandler = new JsonHandler(configFilePath);
    private String commandHistoryFilePath, libraryFilePath;
    private JsonHandler commandHistoryJsonHandler, libraryJsonHandler;
    boolean isSystemInitialized, isSystemRunning;
    private int commandsCount = 0;

    // App handlers
    private CliManager cliManager = new CliManager();
    private Library library = new Library();
    private AppHandler appHandler;

    public void initializeApp() {
        // Check config file
        if (!configJsonHandler.isJsonFileValid()){
            isSystemInitialized = false;
            return;
        }

        // Initialize commandHistory
        commandHistoryFilePath = ConfigResolver.resolve(configJsonHandler.getProperty("CommandHistory"));
        if (!initializeCommandHistory()){
            isSystemInitialized = false;
            return;
        }
        commandHistoryJsonHandler = new JsonHandler(commandHistoryFilePath);

        // Initialize library
        libraryFilePath = ConfigResolver.resolve(configJsonHandler.getProperty("BookListsFilePath"));
        libraryJsonHandler = new JsonHandler(libraryFilePath);
        if (!initializeLibrary()){
            isSystemInitialized = false;
            return;
        }

        appHandler = new AppHandler(this, cliManager, library);
        isSystemInitialized = true;
    }

    private boolean initializeCommandHistory() {
        try {
            PrintWriter printWriter = new PrintWriter(commandHistoryFilePath);
            printWriter.write("");
            printWriter.close();
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    private boolean initializeLibrary() {
        if (!libraryJsonHandler.isJsonFileValid()) {
            System.out.println("library file is not valid");
            return false;
        }
        ArrayList<JsonNode> jsonNodes = libraryJsonHandler.getArrayElements();
        for (JsonNode jsonNode : jsonNodes) {
            String type = JsonHandler.getProperty(jsonNode , "type");
            LibraryItem libraryItem = getNewLibraryItemFromJson(type , jsonNode);
            if(libraryItem != null)
                library.insertLibraryItem(getNewLibraryItemFromJson(type , jsonNode));
        }
        return true;
    }

    public void startApp() {
        if (!isSystemInitialized) return;
        isSystemRunning = true;
        cliManager.showUI();
        while (isSystemRunning) {
            commandsCount++;
            CommandMode commandMode = cliManager.getCommandMode();
            String result = appHandler.handleCommand(commandMode);
            addToCommandHistory(commandMode , result);
            showCommandResult(result);

            if(commandMode == CommandMode.EXIT) finishApp();
        }
    }

    private void finishApp() {
        isSystemRunning = false;
    }

    private void addToCommandHistory(CommandMode commandMode, String result) {
        ArrayList<String[]> newEntry = new ArrayList<>();
        newEntry.add(new String[] { "index", String.valueOf(commandsCount)});
        newEntry.add(new String[] { "command", commandMode.name() });
        newEntry.add(new String[] { "result", result });
        commandHistoryJsonHandler.addArrayEntry(newEntry);
    }

    private void showCommandResult(String result){
        System.out.println(result);
    }

    private LibraryItem getNewLibraryItemFromJson(String type , JsonNode jsonNode){
        return switch (type) {
            case "Book" -> Book.getFromJson(jsonNode);
            case "Magazine" -> Magazine.getFromJson(jsonNode);
            case "Reference" -> Reference.getFromJson(jsonNode);
            case "Thesis" -> Thesis.getFromJson(jsonNode);
            default -> null;
        };
    }
}
