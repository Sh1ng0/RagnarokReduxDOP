package com.ragnarok.engine.item.inventory.service;

import com.ragnarok.engine.logger.Loggable;

public enum InventoryLogEvent implements Loggable {

    // --- Inventory Events ---
    INVENTORY_FULL(LogLevel.WARN, "Inventory is full. Cannot add item with templateId {}."),
    ITEM_STACKED(LogLevel.DEBUG, "Stacked {} of item {}. New quantity: {}."),
    ITEM_ADDED(LogLevel.DEBUG, "Added new stack for item with templateId {}."),
    EQUIPMENT_ITEM_ADDED(LogLevel.DEBUG, "Added unique equipment '{}' (UUID: {}) to inventory."),

    // --- Events for removeItem functionality ---
    ITEM_REMOVED_FULLY(LogLevel.DEBUG, "Stack of item {} removed completely from inventory."),
    ITEM_QUANTITY_REDUCED(LogLevel.DEBUG, "Reduced quantity of item {} by {}. New quantity: {}."),
    UNIQUE_ITEM_NOT_FOUND_FOR_REMOVAL(LogLevel.WARN, "Attempted to remove unique item '{}' (UUID: {}), but it was not found."),
    STACKABLE_ITEM_NOT_FOUND_FOR_REMOVAL(LogLevel.WARN, "Attempted to remove item with templateId {}, but it was not found."),
    EQUIPMENT_ITEM_REMOVED(LogLevel.DEBUG, "Removed unique equipment '{}' (UUID: {}) from inventory."),

    // --- Data Integrity Warnings ---
    INVALID_ITEM_CATEGORY(LogLevel.ERROR, "CRITICAL DATA ERROR: An ItemStack with templateId {} was categorized as EQUIPMENT!");


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