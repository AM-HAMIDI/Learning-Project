package com.mahsan.library.config;

import com.mahsan.library.util.*;

public class ConfigManager {
    private static ConfigManager configManager;

    private static final String projectRootPath = System.getProperty("user.dir");
    private final String projectConfigFilePath = projectRootPath + "/config/config.json";
    private final JsonHandler jsonHandler = new JsonHandler(projectConfigFilePath);

    private ConfigManager(){}

    public static ConfigManager getInstance(){
        if(configManager == null) configManager = new ConfigManager();
        return configManager;
    }

    public String getBooksListsFilePath(){
        return jsonHandler.getProperty("BookListsFilePath");
    }

    public static String getProjectRootPath(){
        return projectRootPath;
    }

}
