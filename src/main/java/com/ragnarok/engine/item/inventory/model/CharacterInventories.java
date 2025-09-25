package com.ragnarok.engine.item.inventory.model;

import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.instance.ItemStack;

import java.util.Collections;

/**
 * Un 'record' inmutable que actúa como contenedor para todos los inventarios especializados de un personaje.
 * Esta estructura proporciona una separación clara y segura por tipos de las diferentes categorías de ítems,
 * haciendo que el estado general sea más fácil de gestionar y razonar.
 */
public record CharacterInventories(
        Inventory<EquipInstance> equipment,
        Inventory<ItemStack> consumables,
        Inventory<ItemStack> cards,
        Inventory<ItemStack> miscellaneous
) {
    /**
     * A static constant representing a completely empty set of inventories with default capacity.
     */
    public static final CharacterInventories EMPTY = new CharacterInventories(
            new Inventory<>(Collections.emptyList(), 100), // Default capacity 100
            new Inventory<>(Collections.emptyList(), 100),
            new Inventory<>(Collections.emptyList(), 100),
            new Inventory<>(Collections.emptyList(), 100)
    );

    /**
     * Returns a new CharacterInventories instance with an updated equipment inventory.
     */
    public CharacterInventories withEquipment(Inventory<EquipInstance> newEquipment) {
        return new CharacterInventories(newEquipment, this.consumables, this.cards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated consumables inventory.
     */
    public CharacterInventories withConsumables(Inventory<ItemStack> newConsumables) {
        return new CharacterInventories(this.equipment, newConsumables, this.cards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated cards inventory.
     * @param newCards The new cards inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withCards(Inventory<ItemStack> newCards) {
        return new CharacterInventories(this.equipment, this.consumables, newCards, this.miscellaneous);
    }

    /**
     * Returns a new CharacterInventories instance with an updated miscellaneous inventory.
     * @param newMiscellaneous The new miscellaneous inventory.
     * @return A new, updated CharacterInventories record.
     */
    public CharacterInventories withMiscellaneous(Inventory<ItemStack> newMiscellaneous) {
        return new CharacterInventories(this.equipment, this.consumables, this.cards, newMiscellaneous);
    }
}



