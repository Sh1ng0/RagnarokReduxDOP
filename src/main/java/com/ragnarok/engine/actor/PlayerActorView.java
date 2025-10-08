package com.ragnarok.engine.actor;

import java.util.UUID;

/**
 * An immutable record representing the dynamic, "live" state of a player in the game world.
 * <p>
 * It is a direct implementation of the {@link ActorView} interface, holding the
 * common "hot data" for a player character.
 */
public record PlayerActorView(
        UUID uniqueId,
        Position position,
        int currentHp,
        int currentSp,
        long nextActionTick,
        PlayerProfile profile // Note: We use the specific PlayerProfile here for type safety
) implements ActorView {}