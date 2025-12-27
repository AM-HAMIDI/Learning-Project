package com.mahsan.library;

import com.mahsan.library.app.AppManager;

public class Main {
    public static void main(String[] args) {
        AppManager appManager = new AppManager();
        appManager.initializeApp();
        appManager.startApp();
    }
}