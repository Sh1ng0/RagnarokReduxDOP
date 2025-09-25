package com.ragnarok.engine.repository;

import com.ragnarok.engine.item.ItemCategory;

/**
 * An in-memory repository implemented as a Singleton using an enum.
 * This ensures there is only one instance of the item catalog in the entire application.
 */
public enum ItemTemplateRepository {
    INSTANCE; // The one and only instance of this repository.

    // You can add fields and a constructor if needed for initialization.
    // private final Map<Long, ItemTemplate> templates;
    //
    // ItemTemplateRepository() {
    //     // Load all templates into the map here.
    // }

    public ItemCategory findCategoryById(long templateId) {
        // --- Placeholder Logic ---
        if (templateId >= 1000 && templateId < 2000) return ItemCategory.CONSUMABLE;
        if (templateId >= 2000 && templateId < 3000) return ItemCategory.CARD;
        return ItemCategory.MISCELLANEOUS;
    }
}