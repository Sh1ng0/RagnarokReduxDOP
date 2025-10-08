package com.ragnarok.engine.db;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * A type-safe record to hold database connection properties.
 */
public record DatabaseConfig(String url, String user, String password) {

    /**
     * A simple utility class to load configuration from a properties file.
     */
    public static class Loader {
        /**
         * Loads database configuration from the specified properties file.
         *
         * @param fileName The name of the properties file on the classpath.
         * @return A {@link DatabaseConfig} record with the loaded properties.
         * @throws IOException If the file cannot be read.
         */
        public static DatabaseConfig load(String fileName) throws IOException {
            Properties props = new Properties();
            try (InputStream input = Loader.class.getClassLoader().getResourceAsStream(fileName)) {
                if (input == null) {
                    throw new IOException(fileName + " not found in the classpath");
                }
                props.load(input);
            }


            return new DatabaseConfig(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        }
    }
}