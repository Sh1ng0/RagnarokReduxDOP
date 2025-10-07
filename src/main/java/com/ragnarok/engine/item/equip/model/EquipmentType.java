package com.ragnarok.engine.item.equip.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.ragnarok.engine.item.card.model.CardSocketType;

// This saves defaults
public sealed interface EquipmentType permits WeaponType, ArmorType {

    CardSocketType getCardSocketType();

    /**
     * A factory method annotated with @JsonCreator to instruct Jackson on how to
     * deserialize a simple string into a concrete EquipmentType.
     * It tries to parse the string as a WeaponType first, and if that fails,
     * it tries to parse it as an ArmorType.
     *
     * @param type The string from the JSON (e.g., "DAGGER", "SHIELD").
     * @return The corresponding enum constant that implements EquipmentType.
     */
    @JsonCreator
    static EquipmentType fromString(String type) {
        try {
            // First, try to see if it's a known WeaponType
            return WeaponType.valueOf(type);
        } catch (IllegalArgumentException e) {
            // If not, it must be an ArmorType
            return ArmorType.valueOf(type);
        }
    }
}
