package com.ragnarok.engine.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * An abstract base class for all integration tests in the engine.
 * It manages the lifecycle of a shared MySQL Docker container and prepares
 * the necessary application components (DSLContext, repositories) for tests to use.
 */
@Testcontainers
public abstract class BaseIntegrationTest {

    /**
     * The shared MySQL container. The @Container annotation makes JUnit 5 manage its lifecycle.
     * It's 'static' so it's started only once for all tests that extend this class,
     * which massively speeds up the test suite.
     */
    @Container
    protected static final MySQLContainer<?> mysqlContainer =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("helheim_db")
                    .withUsername("testuser")
                    .withPassword("testpass");


    // These components will be initialized once and then shared with all subclass tests.
    protected static DSLContext dslContext;
    protected static DbItemTemplateRepository dbItemTemplateRepository;
    protected static ObjectMapper objectMapper; // We'll need this too.

    /**
     * This method runs once before any tests in the subclasses.
     * It's responsible for setting up the database connection, running schema scripts,
     * and instantiating the repositories.
     */
    @BeforeAll
    static void setup() {
        // Step 1: Create a DataSource pointing to the running container.
        // We use HikariCP for a robust connection pool.
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(mysqlContainer.getJdbcUrl());
        config.setUsername(mysqlContainer.getUsername());
        config.setPassword(mysqlContainer.getPassword());
        DataSource dataSource = new HikariDataSource(config);

        // Step 2: Create the jOOQ DSLContext.
        dslContext = DSL.using(dataSource, SQLDialect.MYSQL);

        // Step 3: Run our SQL scripts to prepare the database schema and data.
        runScript("schema.sql");
        runScript("data.sql");

        // Step 4: Instantiate the ObjectMapper for JSON processing.
        objectMapper = new ObjectMapper();

        // Step 5: Instantiate the repository we want to test.
        dbItemTemplateRepository = new DbItemTemplateRepository(dslContext, objectMapper);
    }

    /**
     * Helper method to read a SQL script from the classpath and execute it.
     *
     * @param fileName The name of the script file in src/test/resources.
     */
    private static void runScript(String fileName) {
        try (var inputStream = BaseIntegrationTest.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("Cannot find script file: " + fileName);
            }
            String script = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Split the script into individual statements using the semicolon as a delimiter.
            String[] statements = script.split(";");

            // Execute each statement one by one.
            for (String statement : statements) {
                // Trim the statement to remove whitespace and ignore if it's empty.
                if (!statement.trim().isEmpty()) {
                    dslContext.execute(statement);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read or execute script: " + fileName, e);
        }
    }


}
