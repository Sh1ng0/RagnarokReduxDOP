package com.ragnarok.engine.stat;


/**
 * Representa la defensa desglosada de un actor, separando la reducción
 * porcentual (generalmente del equipo) de la reducción plana
 * (generalmente de la VIT).
 */
public record Defense(
        int percentageReduction, // Ej: 30 para un 30%
        int flatReduction        // Ej: 80 para restar 80 de daño
) {}
