package com.mahsan.library.cli;

import java.util.Arrays;

public enum CommandMode {
    INVALID_COMMAND("Invalid command"),
    HELP("Help"),
    INSERT("Insert"),
    REMOVE("Remove"),
    UPDATE("Update"),
    PRINT_LIST("Print list"),
    SEARCH("Search"),
    SORT("Sort"),
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
