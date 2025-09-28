package com.ragnarok.engine.item.consumable.model;

import com.ragnarok.engine.item.consumable.enums.Stat;

/**
 * An effect that restores (or damages) a specific stat by a fixed amount.
 * This single record can be used to define any potion that heals HP or SP.
 * <p>
 * Example: {@code new HealEffect(Stat.HP, 175)} for a Yellow Potion.
 *
 * @param stat The stat to be modified (e.g., HP, SP).
 * @param amount The amount to restore. Can be negative for damaging effects.
 */
public record HealEffect(Stat stat, int amount) implements ConsumableEffect {
}