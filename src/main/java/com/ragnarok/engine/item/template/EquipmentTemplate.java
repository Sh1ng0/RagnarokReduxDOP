package com.ragnarok.engine.item.template;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.item.equip.model.EquipmentBonuses;
import com.ragnarok.engine.item.equip.model.EquipmentType;

import java.util.List;


public sealed interface EquipmentTemplate extends ItemTemplate permits WeaponTemplate, ArmorTemplate {
    long id();
    String name();
    Element element();
    EquipmentBonuses bonuses();
    int requiredLevel();
    List<String> equippableJobs();
    int cardSlots();
    EquipmentType type(); // So that we can get th general category of any item without weird casting or pattern matching
}