package com.ragnarok.engine.item.inventory.model;

import com.ragnarok.engine.item.ItemCategory;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.instance.ItemInstance;
import com.ragnarok.engine.item.instance.ItemStack;

import java.util.Collections;
import java.util.Optional;
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
     *
     * @param newEquipment The new equipment inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withEquipment(Inventory<UUID, EquipInstance> newEquipment) {
        return new CharacterInventories(newEquipment, this.consumables, this.cards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated consumables inventory.
     *
     * @param newConsumables The new consumables inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withConsumables(Inventory<Long, ItemStack> newConsumables) {
        return new CharacterInventories(this.equipment, newConsumables, this.cards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated cards inventory.
     *
     * @param newCards The new cards inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withCards(Inventory<Long, ItemStack> newCards) {
        return new CharacterInventories(this.equipment, this.consumables, newCards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated miscellaneous inventory.
     *
     * @param newMiscellaneous The new miscellaneous inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withMiscellaneous(Inventory<Long, ItemStack> newMiscellaneous) {
        return new CharacterInventories(this.equipment, this.consumables, this.cards, newMiscellaneous);
    }


    // UI STUFF

    /**
     * Finds an item in a specific inventory category by its visual slot index.
     * Acts as a "translator" between the UI's grid-based view and the underlying map-based storage.
     *
     * @param category  The category of the inventory to search in (e.g., CONSUMABLE, EQUIPMENT).
     * @param slotIndex The zero-based index of the slot clicked by the user.
     * @return An {@link Optional} containing the {@link ItemInstance} if found, or empty.
     */
    public Optional<ItemInstance> getItemFromSlot(ItemCategory category, int slotIndex) {
        return switch (category) {
            case EQUIPMENT -> equipment.getFromSlot(slotIndex).map(item -> item); // .map() is for type casting
            case CONSUMABLE -> consumables.getFromSlot(slotIndex).map(item -> item);
            case CARD -> cards.getFromSlot(slotIndex).map(item -> item);
            case MISCELLANEOUS -> miscellaneous.getFromSlot(slotIndex).map(item -> item);
        };
    }

}
