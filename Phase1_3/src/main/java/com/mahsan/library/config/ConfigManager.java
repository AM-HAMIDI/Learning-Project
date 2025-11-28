package com.mahsan.library.config;

import com.mahsan.library.util.*;

import java.io.IOException;
import java.io.PrintWriter;

public class ConfigManager {
    private static ConfigManager configManager;

    private static final String projectRootPath = System.getProperty("user.dir");
    private final String projectConfigFilePath = projectRootPath + "/config/config.json";
    private final JsonHandler jsonHandler = new JsonHandler(projectConfigFilePath);
    private int commandsCount = 1;

    private ConfigManager(){}

    public static ConfigManager getInstance(){
        if(configManager == null) configManager = new ConfigManager();
        return configManager;
    }

    public String getBookListsAbsoluteFilePath(){
        return projectRootPath + jsonHandler.getProperty("BookListsFilePath");
    }

    public String getCommandHistoryAbsoluteFilePath(){
        return projectRootPath + jsonHandler.getProperty("CommandHistory");
    }

    public static String getProjectRootPath(){
        return projectRootPath;
    }

    public int getCommandsCount(){
        return commandsCount;
    }

    public void incrementCommandsCount(){
        commandsCount++;
    }

    public void cleanCommandHistoryFile(){
        try{
            PrintWriter printWriter = new PrintWriter(getCommandHistoryAbsoluteFilePath());
            printWriter.write("");
            printWriter.close();
        }catch (IOException exception){}
    }
}
