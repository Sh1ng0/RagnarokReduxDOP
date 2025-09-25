package com.ragnarok.engine.item.inventory.service;


import com.ragnarok.engine.actor.ActorProfile;
import com.ragnarok.engine.item.ItemCategory;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.instance.ItemInstance;
import com.ragnarok.engine.item.instance.ItemStack;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.inventory.model.Inventory;
import com.ragnarok.engine.repository.ItemTemplateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A stateless service responsible for all inventory management logic.
 * <p>
 * Following the principles of Data-Oriented Programming, this service is completely
 * stateless. It operates on immutable state objects (ActorProfile, CharacterInventories)
 * and returns new, updated state objects encapsulated in result records.
 * The original state is never modified.
 */

public class InventoryService {

    private final ItemTemplateRepository itemTemplateRepository;

    /**
     * Constructs the service with its required dependencies.
     * @param itemTemplateRepository The repository for looking up item template information.
     */
    public InventoryService(ItemTemplateRepository itemTemplateRepository) {
        this.itemTemplateRepository = itemTemplateRepository;
    }

    /**
     * The main public method to add an item to the inventories.
     * It determines the item's nature and delegates to the appropriate handler.
     */
    public InventoryUpdateResult addItem(CharacterInventories currentInventories, ItemInstance itemToAdd) {
        return switch (itemToAdd) {
            case EquipInstance equip -> handleEquipInstanceAddition(currentInventories, equip);
            case ItemStack stack -> {
                ItemCategory category = itemTemplateRepository.findCategoryById(stack.getTemplateId());
                yield handleItemStackAddition(currentInventories, stack, category);
            }
        };
    }

    /**
     * Handles adding a stackable item by routing it to the correct inventory
     * based on its category, using a modern switch expression.
     */
    private InventoryUpdateResult handleItemStackAddition(CharacterInventories inventories, ItemStack stackToAdd, ItemCategory category) {
        System.out.println("LOG: Adding ItemStack of category " + category);

        // This modern switch expression is exhaustive and returns the final, updated state.
        CharacterInventories updatedInventories = switch (category) {
            case CONSUMABLE ->
                    addToStackableInventory(inventories, stackToAdd,
                            CharacterInventories::consumables, CharacterInventories::withConsumables);

            case CARD ->
                    addToStackableInventory(inventories, stackToAdd,
                            CharacterInventories::cards, CharacterInventories::withCards);

            case MISCELLANEOUS ->
                    addToStackableInventory(inventories, stackToAdd,
                            CharacterInventories::miscellaneous, CharacterInventories::withMiscellaneous);

            case EQUIPMENT -> {
                // This case should not happen if an item is an ItemStack, but the switch must be exhaustive.
                System.out.println("WARN: Tried to add an EQUIPMENT item as an ItemStack.");
                yield inventories; // Return original state
            }
        };

        return new InventoryUpdateResult(updatedInventories);
    }

    /**
     * A generic helper method to add an ItemStack to a specific inventory.
     * It handles both stacking with existing items and adding new items.
     * This avoids duplicating the same logic for consumables, cards, and misc items.
     */
    private <T extends Inventory<ItemStack>> CharacterInventories addToStackableInventory(
            CharacterInventories inventories,
            ItemStack stackToAdd,
            Function<CharacterInventories, T> inventoryGetter,
            BiFunction<CharacterInventories, T, CharacterInventories> inventoryWither) {

        T targetInventory = inventoryGetter.apply(inventories);
        List<ItemStack> items = new ArrayList<>(targetInventory.items());

        // Try to find an existing stack to merge with
        Optional<ItemStack> existingStackOpt = items.stream()
                .filter(it -> it.getTemplateId() == stackToAdd.getTemplateId())
                .findFirst();

        if (existingStackOpt.isPresent()) {
            // Stack exists: remove the old one, add a new one with the combined quantity
            ItemStack existingStack = existingStackOpt.get();
            items.remove(existingStack);
            items.add(existingStack.withQuantityChangedBy(stackToAdd.quantity()));
        } else {
            // No existing stack: add the new stack if there is capacity
            if (items.size() >= targetInventory.capacity()) {
                System.out.println("LOG: Inventory is full. Cannot add new item stack.");
                return inventories; // Return original state
            }
            items.add(stackToAdd);
        }

        // Create the new inventory with the updated list of items
        T newInventory = (T) new Inventory<>(items, targetInventory.capacity());

        // Use the "wither" function to create the new, final set of inventories
        return inventoryWither.apply(inventories, newInventory);
    }

    // --- handleEquipInstanceAddition and other methods ---
    private InventoryUpdateResult handleEquipInstanceAddition(CharacterInventories inventories, EquipInstance equipToAdd) {
        // ... (implementation from previous discussion)
        return new InventoryUpdateResult(inventories); // Placeholder
    }
}