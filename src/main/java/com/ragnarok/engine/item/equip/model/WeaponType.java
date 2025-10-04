package com.ragnarok.engine.item.equip.model;

import com.ragnarok.engine.item.card.model.CardSocketType;

public enum WeaponType implements EquipmentType {


    // ONE-HANDED
    ONE_HANDED_SWORD(false, false),
    DAGGER(false, false),
    GUN(false, true),
    KNUCKLE(false, false),
    ONE_HANDED_ROD(false, false),
    KATAR(false,false),
    SPEAR(false, false),
    ONE_HANDED_AXE(false, false),
    BOOK(false, false),



    // TWO-HANDED
    TWO_HANDED_SWORD(true, false),
    HUUMA_SHURIKEN(true, false),
    BOW(true, true),
    INSTRUMENT(true, false),
    TWO_HANDED_ROD(true, false);



    private final boolean isTwoHanded;
    private final boolean isRanged;

    WeaponType(boolean isTwoHanded, boolean isRanged) {
        this.isTwoHanded = isTwoHanded;
        this.isRanged = isRanged;
    }

    public boolean isTwoHanded() {
        return isTwoHanded;
    }

    public boolean isRanged() {
        return isRanged;
    }

    // Socket stuff


    @Override
    public CardSocketType getCardSocketType() {
        return CardSocketType.WEAPON;
    }
}