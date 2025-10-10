package com.ragnarok.engine.item.equip.model;

import com.ragnarok.engine.mechanics.AutocastData;
import com.ragnarok.engine.stat.StatBlock;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A comprehensive, immutable record that groups all possible bonuses an item can grant.
 * This single structure is used for weapons, armor, and accessories to maintain simplicity and flexibility.
 * It also serves as the container for bonuses granted by cards.
 */
public record EquipmentBonuses(
        // Bonus a stats primarios (1)
        StatBlock primaryStats,

        // Bonus planos (flat) a stats secundarios (9)
        int maxHp, int maxSp, int attack, int defense,
        int magicAttack, int magicDefense, int criticalRate, int flee, int hit,

        // Bonus porcentuales a stats secundarios (3)
        double maxHpPercent, double maxSpPercent, double attackPercent,

        // Efectos especiales y habilidades (3)
        Map<String, Integer> grantSkills,
        List<AutocastData> autocastEffects

) {
    /**
     * A constant representing zero bonuses across the board.
     */
    public static final EquipmentBonuses ZERO = new EquipmentBonuses(
            StatBlock.ZERO, // primaryStats
            0, 0, 0, 0, 0, 0, 0, 0, 0, // 9 flat bonuses
            0.0, 0.0, 0.0,             // 3 percent bonuses
                Collections.emptyMap(), Collections.emptyList() // 3 special effects
    );

    /**
     * Combines this EquipmentBonuses with another, summing their values.
     *
     * @param other The other EquipmentBonuses to add.
     * @return A new EquipmentBonuses instance with the combined bonuses.
     */
    public EquipmentBonuses add(EquipmentBonuses other) {
        if (other == null) {
            return this;
        }
        return new EquipmentBonuses(
                this.primaryStats.add(other.primaryStats),
                this.maxHp + other.maxHp,
                this.maxSp + other.maxSp,
                this.attack + other.attack,
                this.defense + other.defense,
                this.magicAttack + other.magicAttack,
                this.magicDefense + other.magicDefense,
                this.criticalRate + other.criticalRate,
                this.flee + other.flee,
                this.hit + other.hit,
                this.maxHpPercent + other.maxHpPercent,
                this.maxSpPercent + other.maxSpPercent,
                this.attackPercent + other.attackPercent,
                // NOTA: La lógica para fusionar mapas y listas se puede añadir después.
                // De momento nos centramos en los stats numéricos.
                this.grantSkills,
                this.autocastEffects
        );
    }

    // --- Wither Methods for Convenience ---

    public EquipmentBonuses withPrimaryStats(StatBlock primaryStats) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withMaxHp(int maxHp) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withMaxSp(int maxSp) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withAttack(int attack) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withDefense(int defense) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withMaxHpPercent(double maxHpPercent) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withMagicAttack(int magicAttack) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withMagicDefense(int magicDefense) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withCriticalRate(int criticalRate) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withFlee(int flee) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withHit(int hit) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }


    public EquipmentBonuses withMaxSpPercent(double maxSpPercent) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }

    public EquipmentBonuses withAttackPercent(double attackPercent) {
        return new EquipmentBonuses(primaryStats, maxHp, maxSp, attack, defense, magicAttack, magicDefense, criticalRate, flee, hit, maxHpPercent, maxSpPercent, attackPercent, grantSkills, autocastEffects);
    }
}