package com.example.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigReader {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing() // Prevents crashes if file is missing (like in CI)
            .load();

    public static String get(String key) {
        // First check System variables (CI), then .env file (Local)
        String value = System.getenv(key);
        return (value != null) ? value : dotenv.get(key);
    }
}