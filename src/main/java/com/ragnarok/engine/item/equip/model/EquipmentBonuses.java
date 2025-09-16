package com.ragnarok.engine.item.equip;

import com.ragnarok.engine.enums.StatusEffect;
import com.ragnarok.engine.stat.StatBlock;

import java.util.Map;

/**
 * Agrupa todos los posibles bonus que un objeto de equipo puede otorgar.
 * Reutiliza StatBlock para los stats primarios.
 */
public record EquipmentBonuses(
        // Bonus a stats primarios y secundarios
        StatBlock primaryStats,
        int maxHp, int maxSp, int attack, int defense,
        int magicAttack, int magicDefense, int criticalRate, int flee,

        // PLACEHOLDERS
        /**
         * Mapa de estados que puede infligir el arma y su probabilidad (0.0 a 1.0).
         * Ej: Map.of(StatusEffect.STUN, 0.05) -> 5% de probabilidad de aturdir.
         */
        Map<StatusEffect, Double> inflictStatusOnHit,

        /**
         * Mapa de skills que el objeto permite lanzar y a qué nivel.
         * Ej: Map.of("HEAL", 3) -> Permite lanzar Heal a nivel 3.
         */
        Map<String, Integer> grantSkills
) {
    // ... constructores ...
}