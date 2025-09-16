package com.ragnarok.engine.item.equip;

import com.ragnarok.engine.actor.ActorState;

import java.util.Optional;

/**
 * Represents the result of an unequip operation.
 *
 * @param updatedState    The new state of the actor after the item has been unequipped.
 * @param unequippedItem  An Optional containing the item that was removed, or empty if the slot was already empty.
 */
public record UnequipResult(ActorState updatedState, Optional<Equipment> unequippedItem) {}