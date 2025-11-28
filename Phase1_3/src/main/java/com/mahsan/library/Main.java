package com.mahsan.library;

import com.mahsan.library.cli.*;
import com.mahsan.library.common.*;
import com.mahsan.library.config.ConfigManager;
import com.mahsan.library.core.models.*;
import com.mahsan.library.core.service.LibraryService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ConfigManager configManager;
    private static CliManager cliManager = new CliManager();
    private static Library library = new Library();
    private static LibraryService libraryService = new LibraryService(library);
    private static CommandProcessor commandProcessor = new CommandProcessor(cliManager , libraryService);

    public static void main(String[] args) {
        startSystem();
    }

    public static void startSystem(){
        cliManager.showUI();
        while(true){
            CommandMode commandMode = cliManager.getCommandMode();
            if(CommandMode.isInvalidCommand(commandMode)) cliManager.showInputError();
            else if(CommandMode.isTerminalCommandMode(commandMode)) break;
            else commandProcessor.processCommandMode(commandMode);
        }
    }
}