package com.ragnarok.engine.item.template;

/**
 * The root sealed interface for all item blueprints in the game.
 * <p>
 * By being a {@code sealed interface}, it provides two key benefits:
 * <ol>
 * <li><b>Polymorphism:</b> Allows different item templates to be treated uniformly.</li>
 * <li><b>Type Safety:</b> Enables exhaustive pattern matching in {@code switch}
 * expressions, ensuring all template types are handled.</li>
 * </ol>
 * It enforces a common contract that every item template must have an ID and a name.
 */
public sealed interface ItemTemplate permits EquipmentTemplate, ConsumableTemplate, MiscTemplate, CardTemplate {
    long id();
    String name();
}
