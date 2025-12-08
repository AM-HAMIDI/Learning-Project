package com.mahsan.library.cli;

import java.util.Arrays;

public enum CommandMode {
    INVALID_COMMAND("Invalid command"),
    INSERT_BOOK("Insert book"),
    REMOVE_BOOK("Remove book"),
    UPDATE_BOOK("Update book"),
    PRINT_BOOKS_LIST("Print books list"),
    SEARCH_BOOKS_BY_TITLE("Search books by title"),
    SEARCH_BOOKS_BY_AUTHOR("Search books by author"),
    SORT_BOOKS("Sort books by release year"),
    EXIT("Exit");

    private final String commandStr;
    private static final CommandMode[] commandModes = Arrays.copyOfRange(values(), 1, values().length);

    CommandMode(String commandStr) {
        this.commandStr = commandStr;
    }

    public String getCommandStr() {
        return commandStr;
    }

    public static CommandMode[] getCommandModes() {
        return commandModes;
    }

    public static CommandMode getFromInt(int commandIndex) {
        if (commandIndex < 1 || commandIndex > 8)
            return INVALID_COMMAND;
        else
            return values()[commandIndex];
    }
}
