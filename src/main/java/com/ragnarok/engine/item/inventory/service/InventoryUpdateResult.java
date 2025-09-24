package com.ragnarok.engine.item.inventory.service;

import com.ragnarok.engine.item.inventory.model.CharacterInventories;

/**
 * Represents the result of an operation that modifies an actor's inventories.
 * @param updatedInventories The new, immutable state of the character's inventories.
 */
public record InventoryUpdateResult(CharacterInventories updatedInventories) {}