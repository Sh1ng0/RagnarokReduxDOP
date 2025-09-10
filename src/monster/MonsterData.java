package monster;

import enums.Element;
import enums.Race;
import enums.Size;

public record MonsterData(

        long id,
        String name,
        int maxHp,
        int maxSp,

        // Stats primarios
        int str, int agi, int vit, int intel, int dex, int luk,

        // Propiedades
        Race race,
        Size size,
        Element element,

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