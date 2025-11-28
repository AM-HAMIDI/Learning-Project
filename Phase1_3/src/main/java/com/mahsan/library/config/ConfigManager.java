package com.mahsan.library.config;

import com.mahsan.library.util.*;

public class ConfigManager {
    private static final String projectRootPath = System.getProperty("user.dir");
    private static final String configFilePath = projectRootPath + "/config/config.json";
    private String bookListsFilePath;

    public ConfigManager(){

    }
}
