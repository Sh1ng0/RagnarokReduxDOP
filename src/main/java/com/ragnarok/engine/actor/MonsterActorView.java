package com.ragnarok.engine.actor;

import java.util.UUID;

/**
 * A simple data record to hold the current state of a monster's AI.
 *
 * @param targetId The uniqueId of the current target, if any.
 * @param currentBehavior A string representing the current behavior (e.g., "IDLE", "ATTACKING").
 * @param nextAiTick The game tick on which the AI should next re-evaluate its state.
 */
record AIState(
        UUID targetId,
        String currentBehavior,
        long nextAiTick
) {
    /** A constant for a default, idle AI state. */
    public static final AIState IDLE = new AIState(null, "IDLE", 0);
}


/**
 * An immutable record representing the dynamic, "live" state of a monster in the game world.
 * <p>
 * It implements the common {@link ActorView} interface and adds monster-specific state,
 * such as the {@link AIState}.
 */
public record MonsterActorView(
        UUID uniqueId,
        Position position,
        int currentHp,
        int currentSp,
        long nextActionTick,
        MonsterProfile profile, // Note: We use the specific MonsterProfile here for type safety
        AIState aiState
) implements ActorView {}