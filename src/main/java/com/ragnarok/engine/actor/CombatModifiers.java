package com.ragnarok.engine.actor;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;

import java.util.Map;
import java.util.Objects;

/**
 * Groups all relational and conditional damage modifiers.
 * <p>
 * This immutable data structure contains maps that define damage bonuses dealt to,
 * or damage reductions received from, targets with specific characteristics
 * (race, size, element).
 * The values are Doubles representing the percentage (e.g., 0.20 for 20%).
 */
public record CombatModifiers(
        // --- Modificadores Ofensivos (Daño Infligido) ---
        Map<Race, Double> damageBonusVsRace,
        Map<Size, Double> damageBonusVsSize,
        Map<Element, Double> damageBonusVsElement,

        // --- Modificadores Defensivos (Daño Recibido) ---
        Map<Race, Double> damageReductionFromRace,
        Map<Size, Double> damageReductionFromSize,
        Map<Element, Double> damageReductionFromElement
) {

    /**
     * A static, empty instance for actors with no modifiers.
     * This prevents NullPointerExceptions and provides a safe default value.
     */
    public static final CombatModifiers EMPTY = new CombatModifiers(
            Map.of(), Map.of(), Map.of(),
            Map.of(), Map.of(), Map.of()
    );

    /**
     * Compact constructor that ensures no map is null.
     * If a null map is passed, it is replaced with an empty, immutable map.
     */
    public CombatModifiers {
        damageBonusVsRace = Objects.requireNonNullElse(damageBonusVsRace, Map.of());
        damageBonusVsSize = Objects.requireNonNullElse(damageBonusVsSize, Map.of());
        damageBonusVsElement = Objects.requireNonNullElse(damageBonusVsElement, Map.of());
        damageReductionFromRace = Objects.requireNonNullElse(damageReductionFromRace, Map.of());
        damageReductionFromSize = Objects.requireNonNullElse(damageReductionFromSize, Map.of());
        damageReductionFromElement = Objects.requireNonNullElse(damageReductionFromElement, Map.of());
    }
}