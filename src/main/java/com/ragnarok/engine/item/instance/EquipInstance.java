package com.ragnarok.engine.item.instance;

import com.ragnarok.engine.item.template.CardTemplate;
import com.ragnarok.engine.item.template.EquipmentTemplate;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique instance of an item that exists in the game world.
 * <p>
 * This class wraps an immutable {@link EquipmentTemplate} template and adds mutable,
 * instance-specific properties like a unique ID, refine level, and embedded cards.
 * Two instances are considered equal if and only if their unique IDs match.
 */

// Class must be final for the sealed shenaigans to unfold
public final class EquipInstance implements ItemInstance {

    private final UUID uniqueId;
    private final EquipmentTemplate itemTemplate;

    public List<CardTemplate> getSocketedCards() {
        return socketedCards;
    }

    private final List<CardTemplate> socketedCards;

    private int refineLevel;
    // Placeholder for future card system

    /**
     * Creates a new, unique item instance from an equipment template.
     * It starts with a refine level of 0 and no cards.
     *
     * @param itemTemplate The base {@link EquipmentTemplate} blueprint from the repository.
     */
    public EquipInstance(EquipmentTemplate itemTemplate) {
        this.uniqueId = UUID.randomUUID();
        this.itemTemplate = Objects.requireNonNull(itemTemplate);
        this.refineLevel = 0;
        this.socketedCards = new ArrayList<>();
    }

    // --- Getters for Template Properties (Delegation) ---

    public String getName() {
        return itemTemplate.name();
    }

    /**
     * Gets the template ID of this item's archetype.
     * This method fulfills the contract of the {@link ItemInstance} interface
     * by delegating the call to the wrapped template.
     *
     * @return The template ID of the base item.
     */
    public long getTemplateId() {
        return itemTemplate.id();
    }

    public EquipmentTemplate getItemTemplate() {
        return itemTemplate;
    }

    // --- Getters and Setters for Instance Properties ---

    public UUID getUniqueId() {
        return uniqueId;
    }

    public int getRefineLevel() {
        return refineLevel;
    }

    public void setRefineLevel(int refineLevel) {
        // Here you could add validation, e.g., refine level cannot be > 10 or < 0
        this.refineLevel = refineLevel;
    }

    // --- Overridden Methods for Identity ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipInstance that = (EquipInstance) o;
        return uniqueId.equals(that.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }

    @Override
    public String toString() {
        String refineString = refineLevel > 0 ? "+" + refineLevel + " " : "";
        return refineString + getName() + " [" + uniqueId.toString().substring(0, 8) + "]";
    }


    public void addCard(CardTemplate card) {
        // Aquí iría la lógica para comprobar si hay slots disponibles
        this.socketedCards.add(card);
    }
}