package com.mahsan.library;

import com.mahsan.library.cli.*;
import com.mahsan.library.common.*;
import com.mahsan.library.config.ConfigManager;
import com.mahsan.library.core.models.*;
import com.mahsan.library.core.service.LibraryService;

public class Main {
    private static ConfigManager configManager = ConfigManager.getInstance();
    private static CliManager cliManager = new CliManager();
    private static Library library = new Library();
    private static LibraryService libraryService = new LibraryService(library);
    private static CommandProcessor commandProcessor = new CommandProcessor(cliManager , libraryService);
    private static boolean isSystemRunning = true;

    public static void main(String[] args) {
        startSystem();
    }

    public static void startSystem(){
        ConfigManager.getInstance().cleanCommandHistoryFile();
        libraryService.initializeLibrary(configManager.getBookListsAbsoluteFilePath());
        cliManager.showUI();
        while(isSystemRunning){
            CommandMode commandMode = cliManager.getCommandMode();
            commandProcessor.processCommandMode(commandMode);
        }
    }

    public static void finishSystem(){
        isSystemRunning = false;
    }
}