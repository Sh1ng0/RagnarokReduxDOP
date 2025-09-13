package com.ragnarok.engine.character;


import java.util.List;

/**
 * Representa los datos crudos y persistentes de un personaje,
 * tal como se cargarían desde una base de datos.
 * Es un DTO (Data Transfer Object) puro: solo contiene datos, no lógica.
 */
public record CharacterData(
        // Identificadores
        long id,
        String name,

        // Progreso
        int baseLevel,
        int jobLevel,
        String jobId,

        // Stats Base
        int baseStr,
        int baseAgi,
        int baseVit,
        int baseInt,
        int baseDex,
        int baseLuk,

        // Equipo (IDs de items)
        List<Long> equippedItemIds

        // SKILLS?
) {
}