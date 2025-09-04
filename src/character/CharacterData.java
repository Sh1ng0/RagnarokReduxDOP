package character;

public record CharacterData(
        long id,
        String name,
        int baseLevel,
        int jobLevel,
        String jobId, // "SWORDSMAN", "NOVICE", etc.
        int baseStr,
        int baseAgi,
        int baseVit,
        int baseInt,
        int baseDex,
        int baseLuk


        // List<Long> equippedItemIds, etc.
) {
}
