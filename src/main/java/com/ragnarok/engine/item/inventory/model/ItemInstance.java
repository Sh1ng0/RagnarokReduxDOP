package com.ragnarok.engine.item.inventory.model;

import com.ragnarok.engine.item.instance.EquipInstance;

/**
 * A sealed interface representing a unique instance of an item in the game world.
 * <p>
 * This serves as the common supertype for all items that can be held in an
 * inventory, ensuring type safety and enabling exhaustive pattern matching.
 * Each instance is expected to have a unique identifier.
 */
//public sealed interface ItemInstance permits EquipInstance, ConsumableInstance, CardInstance, JunkInstance {
//    // Podríamos añadir métodos comunes aquí si fuera necesario,
//    // como por ejemplo, un método que devuelva el ID de su template.
//    long getTemplateId();
//}
