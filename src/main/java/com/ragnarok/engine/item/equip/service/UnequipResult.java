package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.actor.PlayerProfile;
import com.ragnarok.engine.item.instance.EquipInstance;

import java.util.Optional;

/**
 * Represents the result of an unequip operation.
 *
 * @param updatedState    The new state of the actor after the item has been unequipped.
 * @param unequippedItem  An Optional containing the item that was removed, or empty if the slot was already empty.
 */
public record UnequipResult(PlayerProfile updatedState, Optional<EquipInstance> unequippedItem) {}