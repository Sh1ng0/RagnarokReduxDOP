package com.ragnarok.engine.item.inventory.service;

import com.ragnarok.engine.actor.PlayerProfile;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;

/**
 * Represents the result of using an item, which can affect both the actor's profile and their inventories.
 * @param updatedProfile The new, immutable state of the actor's profile.
 * @param updatedInventories The new, immutable state of the character's inventories.
 */
record UseItemResult(PlayerProfile updatedProfile, CharacterInventories updatedInventories) {}