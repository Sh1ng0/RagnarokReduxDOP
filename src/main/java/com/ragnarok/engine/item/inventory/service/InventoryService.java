package com.ragnarok.engine.item.inventory.service;


import com.ragnarok.engine.actor.ActorProfile;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.instance.ItemInstance;
import com.ragnarok.engine.item.instance.ItemStack;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.inventory.model.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A stateless service responsible for all inventory management logic.
 * <p>
 * Following the principles of Data-Oriented Programming, this service is completely
 * stateless. It operates on immutable state objects (ActorProfile, CharacterInventories)
 * and returns new, updated state objects encapsulated in result records.
 * The original state is never modified.
 */

public class InventoryService {


    /**
     * Attempts to add an item to the appropriate inventory.
     *
     * @param currentInventories The current state of the inventories before the operation.
     * @param itemToAdd The item instance to be added.
     * @return An {@link InventoryUpdateResult} containing the new state of the inventories.
     */
    public InventoryUpdateResult addItem(CharacterInventories currentInventories, ItemInstance itemToAdd) {


        return switch (itemToAdd) {
            case EquipInstance equip -> handleEquipInstanceAddition(currentInventories, equip);
            case ItemStack stack -> handleItemStackAddition(currentInventories, stack);
        };
    }



    /**
     * Handles adding a unique, non-stackable equipment item.
     */
    private InventoryUpdateResult handleEquipInstanceAddition(CharacterInventories inventories, EquipInstance equipToAdd) {
        Inventory<EquipInstance> equipInventory = inventories.equipment();

        // 1. Check for capacity
        if (equipInventory.items().size() >= equipInventory.capacity()) {
            System.out.println("LOG: Equipment inventory is full. Cannot add " + equipToAdd.getName());
            return new InventoryUpdateResult(inventories); // Return original state
        }

        // 2. Create the new list of items (immutable update)
        List<EquipInstance> newItems = new ArrayList<>(equipInventory.items());
        newItems.add(equipToAdd);

        // 3. Create the new inventory and then the new set of inventories
        Inventory<EquipInstance> newEquipInventory = new Inventory<>(newItems, equipInventory.capacity());
        CharacterInventories updatedInventories = inventories.withEquipment(newEquipInventory);

        System.out.println("LOG: Added " + equipToAdd.getName() + " to equipment inventory.");
        return new InventoryUpdateResult(updatedInventories);
    }


    /**
     * Handles adding a stackable item. This is more complex as it involves finding the
     * correct inventory and potentially merging with an existing stack.
     */
    private InventoryUpdateResult handleItemStackAddition(CharacterInventories inventories, ItemStack stackToAdd) {
        // --- ESTA PARTE ES UN ESBOZO QUE DEBERÁ SER COMPLETADO ---
        // La lógica final aquí dependerá de un "ItemTemplateRepository" que nos dirá
        // a qué categoría (consumable, card, etc.) pertenece el templateId del item.

        // 1. **(FUTURO)** Consultar un ItemTemplateRepository para obtener el tipo del item.
        // ItemTemplate template = ItemTemplateRepository.findById(stackToAdd.getTemplateId());
        // ItemCategory category = template.getCategory(); // e.g., CONSUMABLE, CARD, MISC

        ItemCategory category = ItemCategory.CONSUMABLE; // Hardcoded for this example

        // 2. Determinar el inventario de destino
        switch (category) {
            case CONSUMABLE:
                // TODO: Implementar la lógica de apilamiento para el inventario de consumibles.
                // a. Buscar si ya existe un ItemStack con el mismo templateId.
                // b. Si existe, crear un nuevo stack con la cantidad sumada.
                // c. Si no existe, añadir el nuevo stack si hay espacio.
                // d. Devolver el CharacterInventories actualizado.
                System.out.println("LOG: Placeholder for adding to CONSUMABLES inventory.");
                break;
            case CARD:
                System.out.println("LOG: Placeholder for adding to CARDS inventory.");
                break;
            case MISCELLANEOUS:
                System.out.println("LOG: Placeholder for adding to MISC inventory.");
                break;
        }

        return new InventoryUpdateResult(inventories); // Placeholder: Return original state for now
    }



    /**
     * Attempts to remove an item from the inventories.
     *
     * @param currentInventories The current state of the inventories before the operation.
     * @param uniqueItemId The unique ID of the item to remove (primarily for EquipInstance).
     * @param quantity The amount to remove (primarily for ItemStack).
     * @return An {@link InventoryUpdateResult} containing the new state of the inventories.
     */
    public InventoryUpdateResult removeItem(CharacterInventories currentInventories, UUID uniqueItemId, int quantity) {
        // TODO: Implement logic to find and remove an item.
        // This method signature might need refinement to better handle both unique and stackable items.
        System.out.println("Placeholder: Logic for removing item " + uniqueItemId);

        return null; // Placeholder
    }



    /**
     * Attempts to use an item from the inventory. This is the core "cartero" method.
     *
     * @param currentProfile The actor's profile before using the item.
     * @param uniqueItemId The unique ID of the item to be used.
     * @return A {@link UseItemResult} containing the potentially modified actor profile and inventories.
     */
    public UseItemResult useItem(ActorProfile currentProfile, UUID uniqueItemId) {
        // TODO:
        // 1. Find the item in the inventories based on its ID.
        // 2. Use a "switch" with pattern matching on the ItemInstance type.
        //    - case EquipInstance: Call EquipmentService.
        //    - case ItemStack: Check the template to see if it's a consumable, etc., and apply the effect.
        // 3. Return the new ActorProfile and CharacterInventories.
        System.out.println("Placeholder: Logic for using item " + uniqueItemId);

        return null; // Placeholder
    }






}
