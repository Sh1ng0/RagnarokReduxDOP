package com.ragnarok.engine.character;

import com.ragnarok.engine.enums.EquipmentSlot;
import com.ragnarok.engine.item.equip.model.Equipment;

/**
 * Un record inmutable que representa los slots de equipo de un personaje
 * y qué item (si lo hay) está equipado en cada uno.
 * Un valor 'null' en un campo significa que el slot está vacío.
 */
public record CharacterEquipment(

        Equipment rightHand,
        Equipment leftHand,
        Equipment headTop,
        Equipment headMid,
        Equipment headLow,
        Equipment armor,
        Equipment garment,
        Equipment footgear,
        Equipment accessory1,
        Equipment accessory2
) {

    /**
     * Una instancia estática que representa a un personaje sin ningún equipo.
     * Útil para inicializaciones.
     */
    public static final CharacterEquipment UNEQUIPPED = new CharacterEquipment(
            null, null, null, null, null, null, null, null, null, null
    );

    /**
     * Devuelve el objeto equipado en un slot específico.
     */
    public Equipment get(EquipmentSlot slot) {
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
     * Devuelve una NUEVA instancia de CharacterEquipment con el slot especificado actualizado.
     */
    public CharacterEquipment with(EquipmentSlot slot, Equipment item) {
        return switch (slot) {
            case RIGHT_HAND -> new CharacterEquipment(item, leftHand, headTop, headMid, headLow, armor, garment, footgear, accessory1, accessory2);
            case LEFT_HAND -> new CharacterEquipment(rightHand, item, headTop, headMid, headLow, armor, garment, footgear, accessory1, accessory2);
            case ARMOR -> new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, item, garment, footgear, accessory1, accessory2);
            case HEAD_TOP -> new CharacterEquipment(rightHand, leftHand, item, headMid, headLow, armor, garment, footgear, accessory1, accessory2);
            case HEAD_MID -> new CharacterEquipment(rightHand, leftHand, headTop, item, headLow, armor, garment, footgear, accessory1, accessory2);
            case HEAD_LOW -> new CharacterEquipment(rightHand, leftHand, headTop, headMid, item, armor, garment, footgear, accessory1, accessory2);
            case GARMENT -> new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, armor, item, footgear, accessory1, accessory2);
            case FOOTGEAR -> new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, armor, garment, item, accessory1, accessory2);
            case ACCESSORY_1 -> new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, armor, garment, footgear, item, accessory2);
            case ACCESSORY_2 -> new CharacterEquipment(rightHand, leftHand, headTop, headMid, headLow, armor, garment, footgear, accessory1, item);
        };
    }
}