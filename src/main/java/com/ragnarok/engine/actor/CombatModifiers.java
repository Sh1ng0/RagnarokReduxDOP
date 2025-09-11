package com.ragnarok.engine.actor;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;

import java.util.Map;
import java.util.Objects;

/**
 * Agrupa todos los modificadores de daño relacionales y condicionales.
 * <p>
 * Esta estructura de datos es inmutable y contiene mapas que definen bonificaciones
 * de daño infligido o reducciones de daño recibido contra/desde objetivos con
 * características específicas (raza, tamaño, elemento).
 * Los valores son Doubles que representan el porcentaje (ej: 0.20 para un 20%).
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
     * Una instancia estática y vacía para actores sin modificadores.
     * Esto evita NullPointerExceptions y proporciona un valor por defecto seguro.
     */
    public static final CombatModifiers EMPTY = new CombatModifiers(
            Map.of(), Map.of(), Map.of(),
            Map.of(), Map.of(), Map.of()
    );

    /**
     * Constructor compacto que asegura que ningún mapa sea nulo.
     * Si se pasa un mapa nulo, se reemplaza por un mapa vacío inmutable.
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