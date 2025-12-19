package com.mahsan.library.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.cli.*;
import com.mahsan.library.config.ConfigResolver;
import com.mahsan.library.model.*;
import com.mahsan.library.io.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SystemManager {
    private final String configFilePath = ConfigResolver.getConfigFilePath();
    private final JsonHandler configJsonHandler = new JsonHandler(configFilePath);
    private String commandHistoryFilePath, libraryFilePath;
    private JsonHandler commandHistoryJsonHandler, libraryJsonHandler;

    boolean isSystemInitialized, isSystemRunning;
    private int commandsCount = 0;
    private CliManager cliManager = new CliManager();
    private Library library = new Library();
    private SystemProcessor systemProcessor;

    public void initializeSystem() {
        if (!configJsonHandler.isJsonFileValid())
            isSystemInitialized = false;
        commandHistoryFilePath = ConfigResolver.resolve(configJsonHandler.getProperty("CommandHistory"));
        if (!initializeCommandHistory())
            isSystemInitialized = false;
        commandHistoryJsonHandler = new JsonHandler(commandHistoryFilePath);
        libraryFilePath = ConfigResolver.resolve(configJsonHandler.getProperty("BookListsFilePath"));
        libraryJsonHandler = new JsonHandler(libraryFilePath);
        if (!initializeLibrary())
            isSystemInitialized = false;
        systemProcessor = new SystemProcessor(this, cliManager, library);
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
            library.insertLibraryItem(getNewLibraryItemFromJson(type , jsonNode));
        }
        return true;
    }

    private LibraryItem getNewLibraryItemFromJson(String type , JsonNode jsonNode){
        switch (type) {
            case "Book":
                return getNewBookFromJson(jsonNode);
            case "Magazine":
                return getNewMagazineFromJson(jsonNode);
            case "Reference":
                return getNewReferenceFromJson(jsonNode);
            case "Thesis":
                return getNewThesisFromJson(jsonNode);
        }
    }

    private Book getNewBookFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode, "title");
        String author = JsonHandler.getProperty(jsonNode, "author");
        int releaseYear = Integer.parseInt(JsonHandler.getProperty(jsonNode, "releaseYear"));
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));
        return new Book(title , author , releaseYear , status);
    }

    private Magazine getNewMagazineFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode , "title");
        int issueNumber = Integer.parseInt(JsonHandler.getProperty(jsonNode, "issueNumber"));
        String publisher = JsonHandler.getProperty(jsonNode , "publisher");
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));
        return new Magazine(title , issueNumber , publisher , status);
    }

    private Reference getNewReferenceFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode , "title");
        String category = JsonHandler.getProperty(jsonNode , "category");
        String publisher = JsonHandler.getProperty(jsonNode , "publisher");
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));
        return new Reference(title , category , publisher , status);
    }

    private Thesis getNewThesisFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode, "title");
        String author = JsonHandler.getProperty(jsonNode, "author");
        int defenseYear = Integer.parseInt(JsonHandler.getProperty(jsonNode, "defenseYear"));
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));
        return new Thesis(title , author , defenseYear , status);
    }

    public void startSystem() {
        if (!isSystemInitialized)
            return;
        isSystemRunning = true;
        cliManager.showUI();
        while (isSystemRunning) {
            commandsCount++;
            systemProcessor.processCommand(cliManager.getCommandMode());
        }
    }

    protected void finishSystem() {
        isSystemRunning = false;
    }

    protected void updateCommandHistory(CommandMode commandMode, String result) {
        ArrayList<String[]> newEntry = new ArrayList<>();
        newEntry.add(new String[] { "index", String.valueOf(commandsCount) });
        newEntry.add(new String[] { "command", commandMode.name() });
        newEntry.add(new String[] { "result", result });
        commandHistoryJsonHandler.addArrayEntry(newEntry);
    }
}
