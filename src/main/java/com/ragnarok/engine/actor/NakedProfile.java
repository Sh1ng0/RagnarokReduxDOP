package com.ragnarok.engine.actor;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.stat.*;

import java.util.Map;

public record NakedProfile(
        long id,
        String name,
        int baseLevel,
        String jobId,
        int maxHp,
        int maxSp,
        StatBlock totalStats,
        Race race,
        Size size,
        Element attackElement,
        Element defenseElement,
        Attack attack,
        int hitRate,
        int attackDelayInTicks,
        int criticalRate,
        MagicAttack magicAttack,
        Defense defense,
        MagicDefense magicDefense,
        Flee flee,
        Map<String, Integer> availableSkills
) implements BaseProfile {
}