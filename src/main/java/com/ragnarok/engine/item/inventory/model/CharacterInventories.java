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
}



