package com.ragnarok.engine.character;


import java.util.List;

/**
 * Represents a character's raw, persistent data,
 * as it would be loaded from a database.
 * It's a pure DTO (Data Transfer Object): it only contains data, no logic.
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