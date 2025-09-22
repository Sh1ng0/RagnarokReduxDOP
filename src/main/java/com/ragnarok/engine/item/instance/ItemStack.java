package com.ragnarok.engine.item.instance;


/**
 * An immutable record representing a stack of identical items (e.g., potions, cards, materials).
 * This is a Value Object where identity is defined by its contents, not a unique ID.
 */
public record ItemStack(long templateId, int quantity) implements ItemInstance {
    @Override
    public long getTemplateId() {
        return templateId;
    }


    /**
     * Devuelve una NUEVA instancia de ItemStack con la cantidad modificada.
     * Esto asegura que el objeto permanezca inmutable.
     *
     * @param amount La cantidad a añadir (puede ser negativa para reducir).
     * @return Un nuevo ItemStack actualizado.
     */
    public ItemStack withQuantityChangedBy(int amount) {
        return new ItemStack(this.templateId, this.quantity + amount);
    }
}