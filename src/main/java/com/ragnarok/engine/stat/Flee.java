package com.ragnarok.engine.stat;

/**
 * Representa la capacidad de evasión de un actor, desglosada en su esquiva
 * normal (basada en AGI) y su esquiva de la suerte (basada en LUK).
 */
public record Flee(
        int normalFlee,
        int luckyDodge
) {}