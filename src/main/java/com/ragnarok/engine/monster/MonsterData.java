package com.ragnarok.engine.monster;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;

public record MonsterData(

        long id,
        String name,
        int level,
        int maxHp,
        int maxSp,

        // Stats primarios
        int str, int agi, int vit, int intel, int dex, int luk,

        // Propiedades
        Race race,
        Size size,
        Element attackElement,
        Element defenseElement,

        // Stats secundarios de combate
        int baseAttack,
        int hitRate,
        int attackDelayInTicks,
        int criticalRate,
        int magicAttack,
        int defense,
        int magicDefense,
        int fleeRate
) {}