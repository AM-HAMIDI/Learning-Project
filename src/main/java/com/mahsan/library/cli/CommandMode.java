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
    BORROW("Borrow"),
    RETURN("Return"),
    PRINT_BORROWED_ITEMS("Print borrowed items"),
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
        if (commandIndex >= 0 && commandIndex < values().length)
            return values()[commandIndex];
        else
            return INVALID_COMMAND;
    }
}
