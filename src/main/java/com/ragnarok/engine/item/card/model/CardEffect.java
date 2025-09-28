package com.ragnarok.engine.item.card.model;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.enums.StatusEffect;
import com.ragnarok.engine.item.consumable.enums.Stat;
import com.ragnarok.engine.mechanics.AutocastData;
import com.ragnarok.engine.mechanics.Trigger;

/**
 * A sealed interface for all possible card effects.
 * Each effect is a simple, immutable data record.
 */
public sealed interface CardEffect {

    /**
     * Effect that gives a bonus to a primary or secondary stat.
     * @param stat The stat to change (e.g., Stat.STR, Stat.MAX_HP).
     * @param value The value of the bonus.
     * @param isPercentage True if the bonus is a percentage.
     */
    record StatBonus(Stat stat, int value, boolean isPercentage) implements CardEffect {}

    /**
     * Effect that increases damage against a specific race.
     * @param race The target race.
     * @param percent The damage increase in percent (e.g., 20 for +20%).
     */
    record DamageVsRace(Race race, int percent) implements CardEffect {}

    /**
     * Effect that increases damage against a specific size.
     * @param size The target size.
     * @param percent The damage increase in percent.
     */
    record DamageVsSize(Size size, int percent) implements CardEffect {}

    /**
     * Effect that adds a chance to autocast a skill.
     * @param data The full autocast configuration.
     */
    record Autocast(AutocastData data) implements CardEffect {}

    /**
     * Effect that gives a chance to inflict a status on the enemy.
     * @param status The status to inflict (e.g., POISON, STUN).
     * @param chance The chance to inflict it (e.g., 0.05 for 5%).
     * @param on The trigger for the effect (e.g., ON_PHYSICAL_ATTACK).
     */
    record InflictStatus(StatusEffect status, double chance, Trigger on) implements CardEffect {}

    /**
     * Effect that makes the character immune to a status.
     * @param status The status to be immune to.
     */
    record ImmunityToStatus(StatusEffect status) implements CardEffect {}

    /**
     * Effect that changes the element of the armor.
     * @param element The new element for the armor.
     */
    record EndowArmorWithElement(Element element) implements CardEffect {}

    // ... we can add more effects here later, like `EndowWeaponWithElement`, `AddResistance`, etc.
}