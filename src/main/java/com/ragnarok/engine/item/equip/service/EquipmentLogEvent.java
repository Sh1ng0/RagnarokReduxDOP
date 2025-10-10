package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.logger.Loggable;

public enum EquipmentLogEvent implements Loggable {

    // --- Equipment Events ---
    EQUIP_SUCCESS(LogLevel.INFO, "Item '{}' equipped in slot {}. Items returned: {}."),
    EQUIP_FAIL_LEVEL(LogLevel.WARN, "Level requirement not met for item '{}'. Required: {}, Actor has: {}."),
    EQUIP_FAIL_JOB(LogLevel.WARN, "Job requirement not met for item '{}'. Actor job: {}, Required jobs: {}."),
    EQUIP_FAIL_SLOT(LogLevel.WARN, "Item '{}' is not compatible with slot {}."),
    EQUIP_FAIL_DUAL_WIELD(LogLevel.WARN, "Dual wield incompatible. Main hand '{}' conflicts with off-hand '{}'."),
    UNEQUIP_SUCCESS(LogLevel.INFO, "Item '{}' unequipped from slot {}."),

    // --- Data Integrity Warnings related to Equipment/Jobs ---
    ACTOR_HAS_NO_EQUIPMENT_COMPONENT(LogLevel.WARN, "Attempted to equip/unequip on an actor with no equipment component."),
    JOB_NOT_FOUND_IN_REPOSITORY(LogLevel.ERROR, "CRITICAL DATA ERROR: Job with ID '{}' not found in JobRepository."),
    JOB_HIERARCHY_BROKEN(LogLevel.WARN, "Data integrity issue: Parent job with ID '{}' not found in repository during hierarchy check.");


    private final LogLevel level;
    private final String messageTemplate;

    EquipmentLogEvent(LogLevel level, String messageTemplate) {
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