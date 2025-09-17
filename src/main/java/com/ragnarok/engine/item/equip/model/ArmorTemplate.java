package com.ragnarok.engine.item.equip.model;

import com.ragnarok.engine.enums.Element;

import java.util.List;

public record ArmorTemplate(
        long id,
        String name,
        Element element,
        ArmorType type, // Específico para armaduras
        EquipmentBonuses bonuses,
        int requiredLevel,
        List<String> equippableJobs,
        int cardSlots
) implements EquipmentTemplate {

}