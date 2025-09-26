// Ubicación: src/main/java/com/ragnarok/engine/item/inventory/service/InventoryService.java
package com.ragnarok.engine.item.inventory.service;

import com.ragnarok.engine.item.ItemCategory;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.inventory.model.Inventory;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.instance.ItemInstance;
import com.ragnarok.engine.item.instance.ItemStack;
import com.ragnarok.engine.logger.LogEvent;
import com.ragnarok.engine.repository.ItemTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;


/**
 * A stateless service responsible for all inventory management logic.
 * <p>
 * Following the principles of Data-Oriented Programming (DOP), this service is
 * completely <strong>stateless</strong>. It does not hold any information between
 * calls; each operation is a pure function that takes the current state
 * (e.g., {@link com.ragnarok.engine.item.inventory.model.CharacterInventories})
 * as input and returns a new, updated state encapsulated within a result record
 * (e.g., {@code InventoryUpdateResult}). The original state is never modified.
 * <p>
 * It orchestrates all inventory operations, such as adding dropped loot from monsters,
 * removing items, and handling item usage. It uses a pattern-matching {@code switch}
 * to delegate logic based on the item's fundamental type and category. This service
 * relies on injected dependencies, such as the {@link com.ragnarok.engine.repository.ItemTemplateRepository},
 * to function.
 */
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final ItemTemplateRepository itemTemplateRepository;

    public InventoryService(ItemTemplateRepository itemTemplateRepository) {
        this.itemTemplateRepository = itemTemplateRepository;
    }


    /**
     * The main public method to add an item to the inventories.
     * It uses a modern switch expression to determine the item's nature and
     * delegate the addition logic to the appropriate private handler.
     *
     * @param currentInventories The current state of the inventories.
     * @param itemToAdd The item to be added.
     * @return An {@link InventoryUpdateResult} with the new state of the inventories.
     */
    public InventoryUpdateResult addItem(CharacterInventories currentInventories, ItemInstance itemToAdd) {
        // This is the main "postman" switch. It's the entry point for all item additions.
        return switch (itemToAdd) {
            case EquipInstance equip ->

                    handleEquipInstanceAddition(currentInventories, equip);

            case ItemStack stack -> {

                ItemCategory category = itemTemplateRepository.findCategoryById(stack.getTemplateId());

                // This nested switch routes the stackable item to the correct inventory.
                CharacterInventories updatedInventories = switch (category) {
                    case CONSUMABLE ->
                            addToStackableInventory(currentInventories, stack,
                                    CharacterInventories::consumables, CharacterInventories::withConsumables);
                    case CARD ->
                            addToStackableInventory(currentInventories, stack,
                                    CharacterInventories::cards, CharacterInventories::withCards);
                    case MISCELLANEOUS ->
                            addToStackableInventory(currentInventories, stack,
                                    CharacterInventories::miscellaneous, CharacterInventories::withMiscellaneous);
                    case EQUIPMENT -> {

                        LogEvent.INVALID_ITEM_CATEGORY.log(logger, stack.getTemplateId());
                        yield currentInventories; // Return original state
                    }
                };

                yield new InventoryUpdateResult(updatedInventories);
            }
        };
    }


    /**
     * Handles adding a unique, non-stackable equipment item to the equipment inventory.
     *
     * @param inventories The current state of all inventories.
     * @param equipToAdd The unique EquipInstance to be added.
     * @return An {@link InventoryUpdateResult} with the new state of the inventories.
     */
    private InventoryUpdateResult handleEquipInstanceAddition(CharacterInventories inventories, EquipInstance equipToAdd) {

        Inventory<UUID, EquipInstance> equipmentInventory = inventories.equipment();


        if (equipmentInventory.items().size() >= equipmentInventory.capacity()) {
            LogEvent.INVENTORY_FULL.log(logger, equipToAdd.getTemplateId());
            return new InventoryUpdateResult(inventories); // Return original, unmodified state.
        }


        // We use LinkedHashMap to maintain insertion order.
        Map<UUID, EquipInstance> newItemsMap = new LinkedHashMap<>(equipmentInventory.items());


        newItemsMap.put(equipToAdd.getUniqueId(), equipToAdd);
        LogEvent.EQUIPMENT_ITEM_ADDED.log(logger, equipToAdd.getName(), equipToAdd.getUniqueId());


        Inventory<UUID, EquipInstance> newEquipmentInventory = new Inventory<>(newItemsMap, equipmentInventory.capacity());


        CharacterInventories updatedInventories = inventories.withEquipment(newEquipmentInventory);


        return new InventoryUpdateResult(updatedInventories);
    }




    /**
     * A generic helper method to immutably add an ItemStack to a specific stackable inventory.
     * <p>
     * This method contains the core logic for adding items to any of the map-based,
     * stackable inventories (consumables, cards, etc.). It uses a functional approach by
     * accepting a "getter" and a "wither" function as parameters. This allows the same
     * logic to be reused for different inventories, adhering to the DRY (Don't Repeat Yourself) principle.
     * <p>
     * The method handles both merging with an existing stack (if an item of the same
     * template ID is already present) and adding a new stack if there is capacity.
     * It operates immutably by creating a copy of the inventory map.
     *
     * @param inventories       The current, immutable state of all character inventories.
     * @param stackToAdd        The item stack to be added or merged.
     * @param inventoryGetter   A method reference (e.g., {@code CharacterInventories::cards})
     * that extracts the target inventory to be modified.
     * @param inventoryWither   A method reference (e.g., {@code CharacterInventories::withCards})
     * that creates the new CharacterInventories state with the
     * updated target inventory.
     * @return A new {@code CharacterInventories} instance with the item added, or the
     * original instance if the target inventory was full.
     */
    private CharacterInventories addToStackableInventory(
            CharacterInventories inventories,
            ItemStack stackToAdd,
            Function<CharacterInventories, Inventory<Long, ItemStack>> inventoryGetter,
            BiFunction<CharacterInventories, Inventory<Long, ItemStack>, CharacterInventories> inventoryWither) {

        Inventory<Long, ItemStack> targetInventory = inventoryGetter.apply(inventories);
        Map<Long, ItemStack> itemsMap = targetInventory.items();
        long templateId = stackToAdd.getTemplateId();

        // Create a mutable copy to work with, preserving order by using LinkedHashMap.
        Map<Long, ItemStack> newItemsMap = new LinkedHashMap<>(itemsMap);

        // --- CORE LOGIC (O(1) Map operations) ---
        if (newItemsMap.containsKey(templateId)) {
            // Case 1: Stack already exists. Merge it.
            ItemStack existingStack = newItemsMap.get(templateId);
            ItemStack mergedStack = existingStack.withQuantityChangedBy(stackToAdd.quantity());
            newItemsMap.put(templateId, mergedStack); // Replace the old stack with the new merged one.
            LogEvent.ITEM_STACKED.log(logger, stackToAdd.quantity(), templateId, mergedStack.quantity());

        } else {
            // Case 2: No existing stack. Add a new one if there is capacity.
            if (newItemsMap.size() >= targetInventory.capacity()) {
                LogEvent.INVENTORY_FULL.log(logger, templateId);
                return inventories; // Return original, unmodified state.
            }
            newItemsMap.put(templateId, stackToAdd);
            LogEvent.ITEM_ADDED.log(logger, templateId);
        }
        // --- END OF CORE LOGIC ---

        // Create the new inventory record with the updated map.
        Inventory<Long, ItemStack> newInventory = new Inventory<>(newItemsMap, targetInventory.capacity());

        // Use the "wither" function reference to create the final, updated CharacterInventories state.
        return inventoryWither.apply(inventories, newInventory);
    }

    // REMOVE ITEM




    // USE ITEM
}