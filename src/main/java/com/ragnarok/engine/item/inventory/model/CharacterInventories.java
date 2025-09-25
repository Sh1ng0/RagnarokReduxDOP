package com.ragnarok.engine.item.inventory.model;

import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.instance.ItemStack;

import java.util.Collections;
import java.util.UUID;

/**
 * An immutable record that acts as a container for all of a character's specialized inventories.
 */
public record CharacterInventories(
        Inventory<UUID, EquipInstance> equipment,
        Inventory<Long, ItemStack> consumables,
        Inventory<Long, ItemStack> cards,
        Inventory<Long, ItemStack> miscellaneous
) {
    /**
     * A static constant representing a completely empty set of inventories with default capacity.
     */
    public static final CharacterInventories EMPTY = new CharacterInventories(
            new Inventory<>(Collections.emptyMap(), 100), // Default capacity 100
            new Inventory<>(Collections.emptyMap(), 100),
            new Inventory<>(Collections.emptyMap(), 100),
            new Inventory<>(Collections.emptyMap(), 100)
    );

    /**
     * Returns a new CharacterInventories instance with an updated equipment inventory.
     * @param newEquipment The new equipment inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withEquipment(Inventory<UUID, EquipInstance> newEquipment) {
        return new CharacterInventories(newEquipment, this.consumables, this.cards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated consumables inventory.
     * @param newConsumables The new consumables inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withConsumables(Inventory<Long, ItemStack> newConsumables) {
        return new CharacterInventories(this.equipment, newConsumables, this.cards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated cards inventory.
     * @param newCards The new cards inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withCards(Inventory<Long, ItemStack> newCards) {
        return new CharacterInventories(this.equipment, this.consumables, newCards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated miscellaneous inventory.
     * @param newMiscellaneous The new miscellaneous inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withMiscellaneous(Inventory<Long, ItemStack> newMiscellaneous) {
        return new CharacterInventories(this.equipment, this.consumables, this.cards, newMiscellaneous);
    }
}
