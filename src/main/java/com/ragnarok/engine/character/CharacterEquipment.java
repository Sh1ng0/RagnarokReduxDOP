package com.ragnarok.engine.character;

import com.ragnarok.engine.enums.EquipmentSlot;
import com.ragnarok.engine.item.instance.EquipInstance;

/**
 * An immutable record representing a character's equipped items.
 * Each slot holds a unique {@link EquipInstance} or null if the slot is empty.
 */
public record CharacterEquipment(
        EquipInstance rightHand,
        EquipInstance leftHand,
        EquipInstance headTop,
        EquipInstance headMid,
        EquipInstance headLow,
        EquipInstance armor,
        EquipInstance garment,
        EquipInstance footgear,
        EquipInstance accessory1,
        EquipInstance accessory2
) {

    /**
     * A static instance for an unequipped character.
     * Useful for default values and initialization.
     */
    public static final CharacterEquipment UNEQUIPPED = new CharacterEquipment(
            null, null, null, null, null, null, null, null, null, null
    );

    /**
     * Returns the unique item instance equipped in a specific slot.
     * @param slot The slot to check.
     * @return The {@link EquipInstance} in that slot, or null if it's empty.
     */
    public EquipInstance get(EquipmentSlot slot) {
        return switch (slot) {
            case RIGHT_HAND -> rightHand;
            case LEFT_HAND -> leftHand;
            case HEAD_TOP -> headTop;
            case HEAD_MID -> headMid;
            case HEAD_LOW -> headLow;
            case ARMOR -> armor;
            case GARMENT -> garment;
            case FOOTGEAR -> footgear;
            case ACCESSORY_1 -> accessory1;
            case ACCESSORY_2 -> accessory2;
        };
    }

    /**
     * Returns a NEW instance of CharacterEquipment with the specified slot updated.
     * This ensures the object remains immutable.
     *
     * @param slot     The slot to update.
     * @param instance The new {@link EquipInstance} to place in the slot (can be null).
     * @return A new, updated CharacterEquipment record.
     */
    public CharacterEquipment with(EquipmentSlot slot, EquipInstance instance) { // Note the parameter type change
        return switch (slot) {
            case RIGHT_HAND ->
                    new CharacterEquipment(instance, leftHand, headTop, headMid, headLow, armor, garment, footgear, accessory1, accessory2);
            case LEFT_HAND ->
                    new CharacterEquipment(rightHand, instance, headTop, headMid, headLow, armor, garment, footgear, accessory1, accessory2);
            case ARMOR ->
                    new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, instance, garment, footgear, accessory1, accessory2);
            case HEAD_TOP ->
                    new CharacterEquipment(rightHand, leftHand, instance, headMid, headLow, armor, garment, footgear, accessory1, accessory2);
            case HEAD_MID ->
                    new CharacterEquipment(rightHand, leftHand, headTop, instance, headLow, armor, garment, footgear, accessory1, accessory2);
            case HEAD_LOW ->
                    new CharacterEquipment(rightHand, leftHand, headTop, headMid, instance, armor, garment, footgear, accessory1, accessory2);
            case GARMENT ->
                    new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, armor, instance, footgear, accessory1, accessory2);
            case FOOTGEAR ->
                    new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, armor, garment, instance, accessory1, accessory2);
            case ACCESSORY_1 ->
                    new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, armor, garment, footgear, instance, accessory2);
            case ACCESSORY_2 ->
                    new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, armor, garment, footgear, accessory1, instance);
        };
    }
}