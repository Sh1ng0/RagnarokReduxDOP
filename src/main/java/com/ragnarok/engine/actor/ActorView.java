package com.ragnarok.engine.actor;

import java.util.UUID;


/**
 * An immutable record representing the dynamic, "live" state of an actor in the game world.
 * <p>
 * This object is lightweight and designed to be updated frequently, potentially on every
 * game tick. It contains data that changes constantly, such as position, current HP/SP,
 * and action cooldowns.
 * <p>
 * Crucially, it holds a reference to an {@link ActorProfile} (which will be renamed to ActorProfile),
 * containing the actor's stable, calculated combat statistics. This separates the frequently
 * changing "live" data from the infrequently changing "character sheet" data, leading
 * to a more efficient design.
 *
 * @param uniqueId       A unique identifier for this specific actor instance in the world.
 * @param position       The current (x, y) coordinates of the actor on the map.
 * @param currentHp      The actor's current health points.
 * @param currentSp      The actor's current spirit points.
 * @param nextActionTick The game tick on which the actor is allowed to perform its next action (e.g., attack).
 * @param profile        A reference to the actor's profile, containing all stable combat stats.
 */
public record ActorView(
        UUID uniqueId,
        Position position,
        int currentHp,
        int currentSp,
        long nextActionTick,
        ActorProfile profile
) {}