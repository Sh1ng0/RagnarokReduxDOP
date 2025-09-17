package com.ragnarok.engine.item.equip.model;

import com.ragnarok.engine.enums.Element;

import java.util.Collections;
import java.util.List;

/**
 * Represents a piece of equipment with all its base properties.
 * It is an immutable data object.
 */
public record WeaponTemplate(

        long id,
        String name,
        Element element,
        WeaponType type,
        EquipmentBonuses bonuses,
        int requiredLevel,
        List<String> equippableJobs,
        int cardSlots,

        /**
         * A list of EquipmentTypes with which this item is compatible for dual wielding.
         * If an item is in the main hand, the off-hand item
         * must be of a type contained in this list.
         */
        List<EquipmentType> compatibleOffHandTypes// <---- Alright a shield is an armor, so this solves the fact that a shield IS armor and CAN be wielded.

) implements EquipmentTemplate {
    // Constructor compacto para simplificar la creación de items que no son dual-wieldables.
    public WeaponTemplate(long id, String name, Element element, WeaponType type, EquipmentBonuses bonuses,
                          int requiredLevel, List<String> equippableJobs, int cardSlots) {
        this(id, name, element, type, bonuses, requiredLevel, equippableJobs, cardSlots, Collections.emptyList());
    }

}
