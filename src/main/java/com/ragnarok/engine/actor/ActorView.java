package com.ragnarok.engine.actor;

import java.util.UUID;

/**
 * A sealed interface representing the dynamic, "live" state of an actor in the game world.
 * <p>
 * This contract ensures that any actor's live data (position, current HP/SP, etc.)
 * can be processed by game systems in a uniform way. It holds a reference to a
 * {@link BaseProfile} for the actor's stable, calculated stats.
 */
public sealed interface ActorView permits PlayerActorView, MonsterActorView {
    UUID uniqueId();
    Position position();
    int currentHp();
    int currentSp();
    long nextActionTick();
    BaseProfile profile();
}