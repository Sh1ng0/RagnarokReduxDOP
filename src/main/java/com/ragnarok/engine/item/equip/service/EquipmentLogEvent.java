package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.logger.Loggable;

public enum EquipmentLogEvent implements Loggable {

    // --- Equipment Events ---
    EQUIP_SUCCESS(LogLevel.INFO, "Item ''{0}'' equipped in slot {1}. Items returned: {2}."),
    EQUIP_FAIL_LEVEL(LogLevel.WARN, "Level requirement not met for item ''{0}''. Required: {1}, Actor has: {2}."),
    EQUIP_FAIL_JOB(LogLevel.WARN, "Job requirement not met for item ''{0}''. Actor job: {1}, Required jobs: {2}."),
    EQUIP_FAIL_SLOT(LogLevel.WARN, "Item ''{0}'' is not compatible with slot {1}."),
    EQUIP_FAIL_DUAL_WIELD(LogLevel.WARN, "Dual wield incompatible. Main hand ''{0}'' conflicts with off-hand ''{1}''."),
    UNEQUIP_SUCCESS(Loggable.LogLevel.INFO, "Item ''{0}'' unequipped from slot {1}."),

    // --- Data Integrity Warnings related to Equipment/Jobs ---
    ACTOR_HAS_NO_EQUIPMENT_COMPONENT(LogLevel.WARN, "Attempted to equip/unequip on an actor with no equipment component."),
    JOB_NOT_FOUND_IN_REPOSITORY(LogLevel.ERROR, "CRITICAL DATA ERROR: Job with ID ''{0}'' not found in JobRepository."),
    JOB_HIERARCHY_BROKEN(LogLevel.WARN, "Data integrity issue: Parent job with ID ''{0}'' not found in repository during hierarchy check.");


    private final LogLevel level;
    private final String messageTemplate;

    EquipmentLogEvent(LogLevel level, String messageTemplate) {
        this.level = level;
        this.messageTemplate = messageTemplate;
    }

    // Paso 3: Implementamos los métodos requeridos por la interfaz
    @Override
    public LogLevel getLevel() {
        return this.level;
    }

    @Override
    public String getMessageTemplate() {
        return this.messageTemplate;
    }
}