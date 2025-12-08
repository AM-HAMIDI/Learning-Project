package com.mahsan.library;

import com.mahsan.library.core.SystemManager;

public class Main {
    public static final String projectRootPath = System.getProperty("user.dir");

    public static void main(String[] args) {
        SystemManager systemManager = new SystemManager();
        systemManager.initializeSystem();
        systemManager.startSystem();
    }
}
