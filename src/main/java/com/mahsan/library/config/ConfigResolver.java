package com.mahsan.library.config;

public final class ConfigResolver {
    private static final String projectRootPath = System.getProperty("user.dir");

    private ConfigResolver() {
    }

    public static String getProjectRoot() {
        return projectRootPath;
    }

    public static String resolve(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return projectRootPath;
        }
        if (relativePath.startsWith("/")) {
            return projectRootPath + relativePath;
        }
        return projectRootPath + "/" + relativePath;
    }

    public static String getConfigFilePath() {
        return resolve("/config/config.json");
    }
}
