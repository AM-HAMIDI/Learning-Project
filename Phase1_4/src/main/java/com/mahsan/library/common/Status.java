package com.mahsan.library.common;

public enum Status {
    EXIST,
    BORROWED,
    BANNED;

    public static Status getStatus(String status){
        return switch (status) {
            case "EXIST" -> EXIST;
            case "BORROWED" -> BORROWED;
            case "BANNED" -> BANNED;
            default -> null;
        };
    }
}

