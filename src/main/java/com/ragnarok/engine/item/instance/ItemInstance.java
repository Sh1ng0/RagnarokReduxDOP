package com.ragnarok.engine.item.instance;



/**
 * A sealed interface representing anything that can exist in an inventory.
 * It permits unique, stateful entities (EquipInstance) and immutable,
 * stackable value objects (ItemStack).
 */
public sealed interface ItemInstance permits EquipInstance, ItemStack {

    // Sealed interfaces must live inside the same package as their hierarchical buddies
    /**
     * Gets the template ID of the item's archetype.
     * This serves as a common bridge between unique entities and value objects.
     * @return The template ID.
     */
    long getTemplateId();
}