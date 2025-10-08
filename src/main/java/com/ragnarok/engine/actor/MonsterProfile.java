package com.ragnarok.engine.actor;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.stat.*;

import java.util.Map;

/**
 * An immutable record representing a monster's "stat sheet".
 * <p>
 * It is a direct and simple implementation of the {@link BaseProfile},
 * containing only the shared combat statistics.
 */
public record MonsterProfile(
        // Basic Identifiers
        long id,
        String name,
        int baseLevel,
        String jobId,

        // Combat Resources
        int maxHp,
        int maxSp,

        // Stats and Properties
        StatBlock totalStats,
        Race race,
        Size size,
        Element element,

        // Calculated Combat Attributes
        Attack attack,
        int hitRate,
        int attackDelayInTicks,
        int criticalRate,
        MagicAttack magicAttack,
        Defense defense,
        MagicDefense magicDefense,
        Flee flee,

        // Skills (Placeholder)
        Map<String, Integer> availableSkills
) implements BaseProfile {}