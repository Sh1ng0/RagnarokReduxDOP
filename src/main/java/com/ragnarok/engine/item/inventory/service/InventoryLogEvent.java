package com.ragnarok.engine.item.inventory.service;

import com.ragnarok.engine.logger.Loggable;

public enum InventoryLogEvent implements Loggable {

    // --- Inventory Events from original LogEvent ---
    INVENTORY_FULL(Loggable.LogLevel.WARN, "Inventory is full. Cannot add item with templateId {0}."),
    ITEM_STACKED(LogLevel.DEBUG, "Stacked {0} of item {1}. New quantity: {2}."),
    ITEM_ADDED(LogLevel.DEBUG, "Added new stack for item with templateId {0}."),
    EQUIPMENT_ITEM_ADDED(LogLevel.DEBUG, "Added unique equipment ''{0}'' (UUID: {1}) to inventory."),

    // --- Events for removeItem functionality ---
    ITEM_REMOVED_FULLY(LogLevel.DEBUG, "Stack of item {0} removed completely from inventory."),
    ITEM_QUANTITY_REDUCED(LogLevel.DEBUG, "Reduced quantity of item {0} by {1}. New quantity: {2}."),
    UNIQUE_ITEM_NOT_FOUND_FOR_REMOVAL(LogLevel.WARN, "Attempted to remove unique item ''{0}'' (UUID: {1}), but it was not found."),
    STACKABLE_ITEM_NOT_FOUND_FOR_REMOVAL(LogLevel.WARN, "Attempted to remove item with templateId {0}, but it was not found."),
    EQUIPMENT_ITEM_REMOVED(LogLevel.DEBUG, "Removed unique equipment ''{0}'' (UUID: {1}) from inventory."),

    // --- Data Integrity Warnings ---
    INVALID_ITEM_CATEGORY(LogLevel.ERROR, "CRITICAL DATA ERROR: An ItemStack with templateId {0} was categorized as EQUIPMENT!");


    private final LogLevel level;
    private final String messageTemplate;

    InventoryLogEvent(LogLevel level, String messageTemplate) {
        this.level = level;
        this.messageTemplate = messageTemplate;
    }

    @Override
    public LogLevel getLevel() {
        return this.level;
    }

    @Override
    public String getMessageTemplate() {
        return this.messageTemplate;
    }
}