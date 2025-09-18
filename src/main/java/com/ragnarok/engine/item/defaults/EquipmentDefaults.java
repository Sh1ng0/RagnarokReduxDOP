package com.ragnarok.engine.item.defaults;



import com.ragnarok.engine.item.equip.model.ArmorType;
import com.ragnarok.engine.item.equip.model.EquipmentType;
import com.ragnarok.engine.item.equip.model.WeaponType;

import java.util.Collections;
import java.util.List;

/**
 * A utility class that contains default values and constants
 * for creating standard equipment.
 */
public final class EquipmentDefaults {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private EquipmentDefaults() {}

    // --- Standard Compatibility Lists ---

    public static final List<EquipmentType> STANDARD_DAGGER_COMPATIBILITY =
            List.of(WeaponType.DAGGER, ArmorType.SHIELD); // Ahora es válido y seguro

    public static final List<EquipmentType> STANDARD_PISTOL_COMPATIBILITY =
            List.of(WeaponType.GUN);

    public static final List<EquipmentType> STANDARD_1H_SWORD_COMPATIBILITY =
            List.of(WeaponType.ONE_HANDED_SWORD, WeaponType.DAGGER, ArmorType.SHIELD); // También válido

    public static final List<EquipmentType> NON_COMPATIBLE = Collections.emptyList();
}