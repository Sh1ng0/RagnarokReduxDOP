package monster;

import enums.Element;
import enums.Race;
import enums.Size;

public record MonsterData(
        long id,
        String name,
        int level,
        int maxHp,
        int str,
        int vit,
        int agi,
        int intel,
        int dex,
        int luk,
        Race race,
        Element element,
        Size size
        // ...y cualquier otro dato fijo del monstruo
) {}