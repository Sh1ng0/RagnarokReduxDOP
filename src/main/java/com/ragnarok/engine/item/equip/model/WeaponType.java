package com.ragnarok.engine.item.equip.model;

public enum WeaponType implements EquipmentType {


    // ONE-HANDED
    ONE_HANDED_SWORD(false),
    DAGGER(false),
    GUN(false),
    KNUCKLE(false),
    ONE_HANDED_ROD(false),
    KATAR(false),
    SPEAR(false),
    ONE_HANDED_AXE(false),



    // TWO-HANDED
    TWO_HANDED_SWORD(true),
    SHURIKEN(true),
    BOW(true),
    INSTRUMENT(true),
    BOOK(true),
    TWO_HANDED_ROD(true);



    private final boolean isTwoHanded;

    WeaponType(boolean isTwoHanded) {
        this.isTwoHanded = isTwoHanded;
    }

    public boolean isTwoHanded() {
        return isTwoHanded;
    }
}