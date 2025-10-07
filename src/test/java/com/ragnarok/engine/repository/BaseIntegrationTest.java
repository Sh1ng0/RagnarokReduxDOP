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

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(mysqlContainer.getJdbcUrl());
        config.setUsername(mysqlContainer.getUsername());
        config.setPassword(mysqlContainer.getPassword());
        DataSource dataSource = new HikariDataSource(config);

        dslContext = DSL.using(dataSource, SQLDialect.MYSQL);

        runScript("schema.sql");
        runScript("data.sql");


        objectMapper = new ObjectMapper();


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


            String[] statements = script.split(";");

            for (String statement : statements) {

                if (!statement.trim().isEmpty()) {
                    dslContext.execute(statement);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read or execute script: " + fileName, e);
        }
    }


}
