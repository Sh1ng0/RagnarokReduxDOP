package com.ragnarok.engine.logger;

import org.slf4j.Logger;

import java.text.MessageFormat;
// En una implementación real, importaríamos un Logger de una librería como SLF4J
// import org.slf4j.Logger;

/**
 * A centralized, enum-based API for structured, type-safe game events.
 * <p>
 * Each constant in this enum represents a specific, predefined event that can occur
 * within the game engine. It acts as a clean front-end to the underlying logging
 * framework (SLF4J + Logback), ensuring all log messages are consistent and centrally managed.
 */
public enum LogEvent {

    // --- Inventory Events ---
    INVENTORY_FULL(LogLevel.WARN, "Inventory is full. Cannot add item with templateId {0}."),
    ITEM_STACKED(LogLevel.DEBUG, "Stacked {0} of item {1}. New quantity: {2}."),
    ITEM_ADDED(LogLevel.DEBUG, "Added new stack for item with templateId {0}."),



    // --- Equipment Events (NUEVOS) ---
    EQUIP_SUCCESS(LogLevel.INFO, "Item ''{0}'' equipped in slot {1}. Items returned: {2}."),
    EQUIP_FAIL_LEVEL(LogLevel.WARN, "Level requirement not met for item ''{0}''. Required: {1}, Actor has: {2}."),
    EQUIP_FAIL_JOB(LogLevel.WARN, "Job requirement not met for item ''{0}''. Actor job: {1}, Required jobs: {2}."),
    EQUIP_FAIL_SLOT(LogLevel.WARN, "Item ''{0}'' is not compatible with slot {1}."),
    EQUIP_FAIL_DUAL_WIELD(LogLevel.WARN, "Dual wield incompatible. Main hand ''{0}'' conflicts with off-hand ''{1}''."),
    UNEQUIP_SUCCESS(LogLevel.INFO, "Item ''{0}'' unequipped from slot {1}."),
    EQUIPMENT_ITEM_ADDED(LogLevel.DEBUG, "Added unique equipment ''{0}'' (UUID: {1}) to inventory."),

    // --- Data Integrity Warnings ---
    INVALID_ITEM_CATEGORY(LogLevel.ERROR, "CRITICAL DATA ERROR: An ItemStack with templateId {0} was categorized as EQUIPMENT!"),
    ACTOR_HAS_NO_EQUIPMENT_COMPONENT(LogLevel.WARN, "Attempted to equip/unequip on an actor with no equipment component."),
    JOB_NOT_FOUND_IN_REPOSITORY(LogLevel.ERROR, "CRITICAL DATA ERROR: Job with ID ''{0}'' not found in JobRepository."),
    JOB_HIERARCHY_BROKEN(LogLevel.WARN, "Data integrity issue: Parent job with ID ''{0}'' not found in repository during hierarchy check."),
    // --- Combat Events ---
    ACTOR_DEFEATED(LogLevel.INFO, "Actor ''{0}'' has been defeated by ''{1}''."),
    ATTACK_PERFORMED(LogLevel.DEBUG, "Actor ''{0}'' attacks ''{1}'' for {2} damage.");



    /**
     * Defines the severity level of a log event, mapping directly to SLF4J levels.
     */
    private enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }

    private final LogLevel level;
    private final String messageTemplate;

    LogEvent(LogLevel level, String messageTemplate) {
        this.level = level;
        this.messageTemplate = messageTemplate;
    }

    /**
     * Writes this event to the provided logger if the corresponding log level is enabled.
     * <p>
     * This method first performs a cheap "guard clause" check to see if the logger is
     * configured to accept messages of this event's level. The expensive work of
     * formatting the message string is only performed if the check passes.
     *
     * @param logger The SLF4J logger instance from the calling class.
     * @param params The contextual data to be inserted into the message template.
     */
    public void log(Logger logger, Object... params) {
        // Here a classic switch is perfect because we need to check clauses top to bottom, not contemplate a whole ass expression
        // This switch is the "guard clause" that ensures we do no work for disabled log levels.
        switch (this.level) {
            case DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(MessageFormat.format(this.messageTemplate, params));
                }
                break;
            case INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(MessageFormat.format(this.messageTemplate, params));
                }
                break;
            case WARN:
                if (logger.isWarnEnabled()) {
                    logger.warn(MessageFormat.format(this.messageTemplate, params));
                }
                break;
            case ERROR:
                if (logger.isErrorEnabled()) {
                    logger.error(MessageFormat.format(this.messageTemplate, params));
                }
                break;
        }
    }
}
