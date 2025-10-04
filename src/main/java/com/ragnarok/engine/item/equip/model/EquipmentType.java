package com.ragnarok.engine.item.equip.model;


import com.ragnarok.engine.item.card.model.CardSocketType;

// This saves defaults
public sealed interface EquipmentType permits WeaponType, ArmorType {

    CardSocketType getCardSocketType();
}
