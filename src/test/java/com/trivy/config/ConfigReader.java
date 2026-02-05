package com.trivy.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigReader {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String get(String key) {
        // 1️⃣ System properties (-Dkey=value)
        String value = System.getProperty(key);

        // 2️⃣ Environment variables (CI)
        if (value == null) {
            value = System.getenv(key);
        }

        // 3️⃣ .env file (local)
        return (value != null) ? value : dotenv.get(key);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public static String getRequired(String key) {
        String value = get(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required configuration '" + key + "' is missing or empty. " +
                            "Please set it as an environment variable or system property."
            );
        }

        return value;
    }
}
