package com.mahsan.library;

import com.mahsan.library.app.AppManager;

public class Main {
    public static final String projectRootPath = System.getProperty("user.dir");

    public static void main(String[] args) {
        AppManager systemManager = new AppManager();
        systemManager.initializeSystem();
        systemManager.startSystem();
    }
}
