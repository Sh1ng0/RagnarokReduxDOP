package com.ragnarok.engine.stat;

import com.ragnarok.engine.logger.Loggable;

public enum StatLogEvent implements Loggable {


    // --- StatCalculator (Naked Stats) ---
    NAKED_CALC_START(LogLevel.DEBUG, "Starting naked stat calculation for {} (ID: {})."),
    JOB_BONUS_APPLIED(LogLevel.DEBUG, "  -> Job bonuses applied for '{}': {}"),
    NAKED_PROFILE_CREATED(LogLevel.INFO, "Naked profile created for '{}'. Profile: {}"),

    // --- Monster Profile (in StatCalculator) ---
    MONSTER_CALC_START(LogLevel.DEBUG, "Starting profile calculation for monster {} (ID: {})."),
    MONSTER_PROFILE_CREATED(LogLevel.INFO, "Profile created for monster '{}'. Profile: {}"),

    // --- EquipmentStatCalculator (Equipped Stats) ---
    EQUIP_CALC_START(LogLevel.DEBUG, "Starting equipment stat calculation for naked profile of '{}'."),
    BONUS_ACCUMULATION_COMPLETE(LogLevel.DEBUG, "  -> Final equipment bonuses accumulated. Primary: {}, Secondary: {}"),
    SPECIAL_EFFECTS_COLLECTED(LogLevel.DEBUG, "  -> Collected {} special card effects."),
    ELEMENTS_DETERMINED(LogLevel.DEBUG, "  -> Final elements determined. Attack: {}, Defense: {}"),
    FINAL_PROFILE_ASSEMBLED(LogLevel.INFO, "Final player profile assembled for '{}'. Final Profile: {}");


    private final LogLevel level;
    private final String messageTemplate;

    StatLogEvent(LogLevel level, String messageTemplate) {
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
