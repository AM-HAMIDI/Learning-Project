package com.mahsan.library.cli;

import com.mahsan.library.model.LibraryItemType;
import com.mahsan.library.model.Status;
import java.util.Scanner;

public class CliManager {
    private static final Scanner scanner = new Scanner(System.in);

    public void showUI() {
        String title = getTitle();
        String options = getCommandModeOptions();
        System.out.println("\n" + title + "\n" + options);
    }

    public Scanner getScanner(){
        return scanner;
    }

    private String getTitle() {
        return "-".repeat(50) + " Library Management System " + "-".repeat(50);
    }

    public String getCommandModeOptions() {
        StringBuilder options = new StringBuilder();
        for (int i = 0; i < CommandMode.getCommandModes().length; i++) {
            options.append("[%d]".formatted(i + 1));
            options.append(CommandMode.getCommandModes()[i].getCommandStr()).append("\n");
        }
        return options.toString();
    }

    public String getLibraryItemTypeOptions(boolean allOptions){
        StringBuilder typeOptions = new StringBuilder();
        if(allOptions){
            for (int i = 0; i < LibraryItemType.getItemTypes().length; i++) {
                typeOptions.append("[%d]".formatted(i + 1));
                typeOptions.append(LibraryItemType.getItemTypes()[i].getTypeStr()).append("\n");
            }
        } else {
            for (int i = 0; i < LibraryItemType.getItemTypes().length - 1; i++) {
                typeOptions.append("[%d]".formatted(i + 1));
                typeOptions.append(LibraryItemType.getItemTypes()[i].getTypeStr()).append("\n");
            }
        }

        return typeOptions.toString();
    }

    public CommandMode getCommandMode() {
        System.out.println("Choose an option [1-8]:");
        String input = scanner.nextLine().trim();
        try {
            return CommandMode.getFromInt(Integer.parseInt(input));
        } catch (NumberFormatException exception) {
            return CommandMode.INVALID_COMMAND;
        }
    }

    public LibraryItemType getLibraryItemTypeOption(boolean allOptions) {
        if(allOptions){
            System.out.println("Choose a type [1-5]");
            try {
                String input = scanner.nextLine().trim();
                return LibraryItemType.getFromInt(Integer.parseInt(input));
            } catch (NumberFormatException exception){
                return LibraryItemType.INVALID_TYPE;
            }
        }
        else{
            System.out.println("Choose a type [1-4]");
            try {
                String input = scanner.nextLine().trim();
                int inputOption = Integer.parseInt(input);
                if(inputOption == 5)
                    return LibraryItemType.INVALID_TYPE;
                else
                    return LibraryItemType.getFromInt(inputOption);
            } catch (NumberFormatException exception){
                return LibraryItemType.INVALID_TYPE;
            }
        }
    }

    public String getInputError() {
        return "Invalid input!";
    }

    public String getInputString(String field){
        System.out.println("\nEnter " + field + " : ");
        return scanner.nextLine().trim();
    }

    public int getInputInteger(String field){
        System.out.println("\nEnter " + field + " : ");
        String releaseYearStr = scanner.nextLine().trim();
        try {
            return Integer.parseInt(releaseYearStr);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public Status getInputStatus() {
        System.out.println("\nEnter book's Status : Exist or Borrowed or Banned");
        String statusStr = scanner.nextLine().trim();
        return switch (statusStr) {
            case "Exist" -> Status.EXIST;
            case "Borrowed" -> Status.BORROWED;
            case "Banned" -> Status.BANNED;
            default -> null;
        };
    }
}
