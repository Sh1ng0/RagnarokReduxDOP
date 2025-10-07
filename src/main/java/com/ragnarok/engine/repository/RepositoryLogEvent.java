package com.ragnarok.engine.repository;


import com.ragnarok.engine.logger.Loggable;

public enum RepositoryLogEvent implements Loggable {

    // DEBUG Events for detailed flow tracing
    SEARCHING_BY_ID(LogLevel.DEBUG, "Searching for item template with ID: {0}."),
    RECORD_FOUND_BY_ID(LogLevel.DEBUG, "Record found for ID: {0}. Category: ''{1}''."),
    DESERIALIZATION_SUCCESS(LogLevel.DEBUG, "JSON for ID: {0} successfully deserialized into {1}."),

    // INFO Events for significant but normal outcomes
    RECORD_NOT_FOUND_BY_ID(LogLevel.INFO, "No item template found for ID: {0}."),

    // ERROR Events for critical data/integrity issues
    DESERIALIZATION_FAILED(LogLevel.ERROR, "CRITICAL: Failed to deserialize JSON for ID: {0}. Error: {1}"),
    UNKNOWN_CATEGORY(LogLevel.ERROR, "CRITICAL: Unknown category ''{0}'' found in database for ID: {1}.");

    private final LogLevel level;
    private final String messageTemplate;

    RepositoryLogEvent(LogLevel level, String messageTemplate) {
        this.level = level;
        this.messageTemplate = messageTemplate;
    }

    @Override
    public LogLevel getLevel() {
        return level;
    }

    @Override
    public String getMessageTemplate() {
        return messageTemplate;
    }
}