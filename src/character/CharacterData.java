package character;


// This is the thingy that comes from the supposed DB (We will use a mock record)
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
