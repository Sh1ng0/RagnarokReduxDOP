package com.ragnarok.engine.item.equip.model;

import com.ragnarok.engine.enums.Element;

import java.util.Collections;
import java.util.List;

/**
 * Representa una pieza de equipo con todas sus propiedades base.
 * Es un objeto de datos inmutable.
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
         * Lista de EquipmentType con los que este item es compatible para dual wield.
         * Si un item está en la mano principal, el item de la mano secundaria
         * debe ser de un tipo contenido en esta lista.
         */
        List<EquipmentType> compatibleOffHandTypes// <---- Alright a shield is an armor, so this solves the fact that a shield IS armor and CAN be wielded.

) implements EquipmentTemplate {
    // Constructor compacto para simplificar la creación de items que no son dual-wieldables.
    public WeaponTemplate(long id, String name, Element element, WeaponType type, EquipmentBonuses bonuses,
                          int requiredLevel, List<String> equippableJobs, int cardSlots) {
        this(id, name, element, type, bonuses, requiredLevel, equippableJobs, cardSlots, Collections.emptyList());
    }

}
