package com.mahsan.library.model;

import java.util.Arrays;

public enum LibraryItemType {
    INVALID_TYPE("invalid type"),
    BOOK("book"),
    MAGAZINE("magazine"),
    REFERENCE("reference"),
    THESIS("thesis"),
    ALL("all");

    private final String typeStr;
    private static final LibraryItemType[] itemTypes = Arrays.copyOfRange(values(), 1, values().length);

    LibraryItemType(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public static LibraryItemType[] getItemTypes() {
        return itemTypes;
    }

    public static LibraryItemType getFromInt(int typeIndex) {
        if (typeIndex < 1 || typeIndex > 5)
            return INVALID_TYPE;
        else
            return values()[typeIndex];
    }
}
