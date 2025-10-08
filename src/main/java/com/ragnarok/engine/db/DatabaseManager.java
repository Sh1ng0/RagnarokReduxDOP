package com.ragnarok.engine.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Manages the application's database connection pool and jOOQ DSL context.
 */
public final class DatabaseManager {

    private static final HikariDataSource dataSource;
    private static final DSLContext jooq;

    static {
        try {

            DatabaseConfig dbConfig = DatabaseConfig.Loader.load("application.properties");


            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(dbConfig.url());
            hikariConfig.setUsername(dbConfig.user());
            hikariConfig.setPassword(dbConfig.password());

            // Performance settings
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            hikariConfig.setMaximumPoolSize(10);


            dataSource = new HikariDataSource(hikariConfig);
            jooq = new DefaultDSLContext(dataSource, SQLDialect.MYSQL);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    /**
     * Helper method to load a properties file from the classpath.
     */
    private static Properties loadProperties(String fileName) throws IOException {
        Properties props = new Properties();
        try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IOException(fileName + " not found in the classpath");
            }
            props.load(input);
        }
        return props;
    }

    private DatabaseManager() {

    }

    public static DSLContext getJooqContext() {
        return jooq;
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}