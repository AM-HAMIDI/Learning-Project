package com.mahsan.library.cli;

import com.mahsan.library.common.CommandMode;
import com.mahsan.library.common.Status;

import java.util.Scanner;

public class CliManager {
    private static final Scanner scanner = new Scanner(System.in);

    public void showUI(){
        String title = getTitle();
        String options = getOptions();
        System.out.println("\n" + title + "\n" + options);
    }

    private String getTitle(){
        return "-".repeat(50) + " Library Management System " + "-".repeat(50);
    }

    private String getOptions(){
        StringBuilder options = new StringBuilder();
        for(int i = 0; i < CommandMode.getCommandModes().length ; i++){
            options.append("[%d]".formatted(i + 1));
            options.append(CommandMode.getCommandModes()[i].getCommandStr()).append("\n");
        }
        return options.toString();
    }

    public CommandMode getCommandMode(){
        System.out.println("Choose an option [1-8]:");
        String input = scanner.nextLine().trim();
        try {
            return CommandMode.getFromInt(Integer.parseInt(input));
        }catch (NumberFormatException exception){
            return CommandMode.INVALID_COMMAND;
        }
    }

    public String getInputError(){
        return "Invalid input!";
    }

    public String getInputTitle(){
        System.out.println("\nEnter book's title : ");
        return scanner.nextLine().trim();
    }

    public String getInputAuthor(){
        System.out.println("\nEnter book's author : ");
        return scanner.nextLine().trim();
    }

    public int getInputReleaseYear(){
        System.out.println("\nEnter book's release year : ");
        String releaseYearStr = scanner.nextLine().trim();
        try {
            return Integer.parseInt(releaseYearStr);
        } catch (NumberFormatException exception){
            return -1;
        }
    }

    public Status getInputStatus(){
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
