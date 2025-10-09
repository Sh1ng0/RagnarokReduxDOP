package com.ragnarok.engine.enums;

public enum Attribute {
    // Primary Stats
    STR, AGI, VIT, INT, DEX, LUK,

    // Secondary Stats
    MAX_HP, MAX_SP,
    ATK, DEF,
    MATK, MDEF,
    HIT, FLEE, CRIT,

    // Percent Stats (si queremos distinguirlos)
    MAX_HP_PERCENT,
    MAX_SP_PERCENT,
    ATK_PERCENT
}