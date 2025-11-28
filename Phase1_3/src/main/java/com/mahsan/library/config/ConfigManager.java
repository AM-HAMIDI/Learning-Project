package com.mahsan.library.config;

import com.mahsan.library.util.*;

public class ConfigManager {
    private static ConfigManager configManager;

    private final String projectRootPath = System.getProperty("user.dir");
    private final String projectConfigFilePath = projectRootPath + "/config/config.json";
    private final JsonHandler jsonHandler = new JsonHandler(projectConfigFilePath);

    private ConfigManager(){}

    public ConfigManager getInstance(){
        if(configManager == null) configManager = new ConfigManager();
        return configManager;
    }

    public String getBooksListsDirPath(){
        return jsonHandler.getProperty("BookListsDirPath");
    }
}
