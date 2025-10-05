package com.ragnarok.engine.repository;

import com.ragnarok.engine.item.ItemCategory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An in-memory repository implemented as a Singleton using an enum.
 * This ensures there is only one instance of the item catalog in the entire application.
 */
public enum ItemTemplateRepository {
    INSTANCE; // The one and only instance of this repository.

    // Usamos un mapa para guardar las categorías de los ítems temporales.
    // ConcurrentHashMap es seguro para usar en entornos con múltiples hilos.
    private final Map<Long, ItemCategory> temporaryCategories;

    ItemTemplateRepository() {
        this.temporaryCategories = new ConcurrentHashMap<>();
    }

    /**
     * Finds the category of an item by its template ID.
     * It first checks for temporarily added templates (for testing) before falling
     * back to the placeholder logic.
     * @param templateId The ID of the item template.
     * @return The {@link ItemCategory}.
     */
    public ItemCategory findCategoryById(long templateId) {

        if (temporaryCategories.containsKey(templateId)) {
            return temporaryCategories.get(templateId);
        }


        if (templateId >= 1000 && templateId < 2000) return ItemCategory.CONSUMABLE;
        if (templateId >= 2000 && templateId < 3000) return ItemCategory.CARD;
        // Asumimos que el equipo tendrá IDs más altos, ajusta según sea necesario.
        if (templateId >= 5000) return ItemCategory.EQUIPMENT;

        return ItemCategory.MISCELLANEOUS;
    }

    /**
     * Adds a temporary template ID and its category to the repository.
     * This method is intended ONLY FOR TESTING PURPOSES to set up test data.
     * @param templateId The ID of the temporary item.
     * @param category The category of the temporary item.
     */
    public void addTemporaryTemplate(long templateId, ItemCategory category) {
        temporaryCategories.put(templateId, category);
    }


    /**
     * FOR DEBUGGING PURPOSES ONLY.
     * Returns a copy of the temporary categories map.
     */
    public Map<Long, ItemCategory> getTemporaryCategories() {
        return Map.copyOf(temporaryCategories);
    }
}