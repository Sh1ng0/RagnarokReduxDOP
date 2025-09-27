package com.ragnarok.engine.logger;

import org.slf4j.Logger;

import java.text.MessageFormat;

/**
 * A contract for creating structured, enum-based logging events.
 * <p>
 * This interface centralizes the logging logic into a default {@code log} method,
 * ensuring that all log event enums across the application behave consistently
 * without duplicating code. Enums implementing this interface are responsible only
 * for defining the log events, their severity levels, and their message templates.
 * <p>
 * This approach promotes high cohesion (each service can have its own log events)
 * while adhering to the DRY (Don't Repeat Yourself) principle for the logging mechanism itself.
 */
public interface Loggable {

    /**
     * Defines the severity level of a log event, mapping directly to SLF4J levels.
     * This nested enum is part of the interface to be accessible by all implementing enums.
     */
    enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }

    /**
     * Returns the severity level of the specific log event.
     *
     * @return The {@link LogLevel} of the event.
     */
    LogLevel getLevel();

    /**
     * Returns the raw, unformatted message template for the log event.
     *
     * @return The message string, typically with placeholders like {0}, {1}, etc.
     */
    String getMessageTemplate();

    /**
     * Writes this event to the provided logger if the corresponding log level is enabled.
     * <p>
     * This default method contains the core logging logic. It first performs a cheap
     * "guard clause" check (e.g., {@code logger.isInfoEnabled()}) to avoid the expensive
     * work of formatting the message string if the log level is disabled in the
     * logging configuration.
     *
     * @param logger The SLF4J logger instance from the calling class.
     * @param params The contextual data to be inserted into the message template.
     */
    default void log(Logger logger, Object... params) {
        switch (this.getLevel()) {
            case DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(MessageFormat.format(this.getMessageTemplate(), params));
                }
                break;
            case INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(MessageFormat.format(this.getMessageTemplate(), params));
                }
                break;
            case WARN:
                if (logger.isWarnEnabled()) {
                    logger.warn(MessageFormat.format(this.getMessageTemplate(), params));
                }
                break;
            case ERROR:
                if (logger.isErrorEnabled()) {
                    logger.error(MessageFormat.format(this.getMessageTemplate(), params));
                }
                break;
        }
    }
}