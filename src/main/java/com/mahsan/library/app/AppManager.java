package com.mahsan.library.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.cli.*;
import com.mahsan.library.config.ConfigResolver;
import com.mahsan.library.model.*;
import com.mahsan.library.io.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppManager {
    private final String configFilePath = ConfigResolver.getConfigFilePath();
    private final JsonHandler configJsonHandler = new JsonHandler(configFilePath);
    private String commandHistoryFilePath, libraryFilePath;
    private JsonHandler commandHistoryJsonHandler, libraryJsonHandler;

    boolean isSystemInitialized, isSystemRunning;
    private int commandsCount = 0;
    private CliManager cliManager = new CliManager();
    private Library library = new Library();
    private AppHandler systemProcessor;

    public void initializeApp() {
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
        systemProcessor = new AppHandler(this, cliManager, library);
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
        if (!isSystemInitialized)
            return;
        isSystemRunning = true;
        cliManager.showUI();
        while (isSystemRunning) {
            commandsCount++;
            systemProcessor.handleCommand(cliManager.getCommandMode());
        }
    }

    protected void finishApp() {
        isSystemRunning = false;
    }

    protected void AddToCommandHistory(CommandMode commandMode, String result) {
        ArrayList<String[]> newEntry = new ArrayList<>();
        newEntry.add(new String[] { "index", String.valueOf(commandsCount) });
        newEntry.add(new String[] { "command", commandMode.name() });
        newEntry.add(new String[] { "result", result });
        commandHistoryJsonHandler.addArrayEntry(newEntry);
    }

    protected void showCommandResult(String result){
        System.out.println(result);
    }

    private LibraryItem getNewLibraryItemFromJson(String type , JsonNode jsonNode){
        return switch (type) {
            case "Book" -> getNewBookFromJson(jsonNode);
            case "Magazine" -> getNewMagazineFromJson(jsonNode);
            case "Reference" -> getNewReferenceFromJson(jsonNode);
            case "Thesis" -> getNewThesisFromJson(jsonNode);
            default -> null;
        };
    }

    private Book getNewBookFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode, "title");
        String author = JsonHandler.getProperty(jsonNode, "author");
        int releaseYear = Integer.parseInt(JsonHandler.getProperty(jsonNode, "releaseYear"));
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));

        if (status == Status.BORROWED) {
            if(!jsonNode.has("returnDate"))
                return null;
            String returnDateStr = JsonHandler.getProperty(jsonNode, "returnDate");
            LocalDate returnDate = LocalDate.parse(returnDateStr, LibraryItem.dateTimeFormatter);
            return new Book(title, author, releaseYear, status, returnDate);
        } else {
            return new Book(title, author, releaseYear, status);
        }
    }

    private Magazine getNewMagazineFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode , "title");
        int issueNumber = Integer.parseInt(JsonHandler.getProperty(jsonNode , "issueNumber"));
        String publisher = JsonHandler.getProperty(jsonNode , "publisher");
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));

        if (status == Status.BORROWED) {
            if(!jsonNode.has("returnDate"))
                return null;
            String returnDateStr = JsonHandler.getProperty(jsonNode, "returnDate");
            LocalDate returnDate = LocalDate.parse(returnDateStr, LibraryItem.dateTimeFormatter);
            return new Magazine(title, issueNumber, publisher, status, returnDate);
        } else {
            return new Magazine(title, issueNumber, publisher, status);
        }
    }

    private Reference getNewReferenceFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode , "title");
        String category = JsonHandler.getProperty(jsonNode , "category");
        String publisher = JsonHandler.getProperty(jsonNode , "publisher");
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));

        if (status == Status.BORROWED) {
            if(!jsonNode.has("returnDate"))
                return null;
            String returnDateStr = JsonHandler.getProperty(jsonNode, "returnDate");
            LocalDate returnDate = LocalDate.parse(returnDateStr, LibraryItem.dateTimeFormatter);
            return new Reference(title, category, publisher, status, returnDate);
        } else {
            return new Reference(title, category, publisher, status);
        }
    }

    private Thesis getNewThesisFromJson(JsonNode jsonNode){
        String title = JsonHandler.getProperty(jsonNode, "title");
        String author = JsonHandler.getProperty(jsonNode, "author");
        int defenseYear = Integer.parseInt(JsonHandler.getProperty(jsonNode, "defenseYear"));
        Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));

        if (status == Status.BORROWED) {
            if(!jsonNode.has("returnDate"))
                return null;
            String returnDateStr = JsonHandler.getProperty(jsonNode, "returnDate");
            LocalDate returnDate = LocalDate.parse(returnDateStr, LibraryItem.dateTimeFormatter);
            return new Thesis(title, author, defenseYear, status, returnDate);
        } else {
            return new Thesis(title, author, defenseYear, status);
        }
    }
}
