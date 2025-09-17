package com.ragnarok.engine.item.equip.model;

import com.ragnarok.engine.enums.Element;

import java.util.List;

// Le decimos que solo WeaponItem y ArmorItem pueden ser "Equipment"
public sealed interface EquipmentTemplate permits WeaponTemplate, ArmorTemplate {
    long id();
    String name();
    Element element();
    EquipmentBonuses bonuses();
    int requiredLevel();
    List<String> equippableJobs();
    int cardSlots();
    EquipmentType type(); // So that we can get th general category of any item without weird casting or pattern matching
}