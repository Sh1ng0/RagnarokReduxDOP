package com.ragnarok.engine.item.defaults;



import com.ragnarok.engine.item.equip.ArmorType;
import com.ragnarok.engine.item.equip.EquipmentType;
import com.ragnarok.engine.item.equip.WeaponType;

import java.util.Collections;
import java.util.List;

/**
 * Clase de utilidad que contiene valores por defecto y constantes
 * para la creación de equipo estándar.
 */
public final class EquipmentDefaults {

    /**
     * Constructor privado para prevenir la instanciación de esta clase de utilidad.
     */
    private EquipmentDefaults() {}

    // --- Listas de Compatibilidad Estándar ---

    public static final List<EquipmentType> STANDARD_DAGGER_COMPATIBILITY =
            List.of(WeaponType.DAGGER, ArmorType.SHIELD); // Ahora es válido y seguro

    public static final List<EquipmentType> STANDARD_PISTOL_COMPATIBILITY =
            List.of(WeaponType.GUN);

    public static final List<EquipmentType> STANDARD_1H_SWORD_COMPATIBILITY =
            List.of(WeaponType.ONE_HANDED_SWORD, ArmorType.SHIELD); // También válido

    public static final List<EquipmentType> NON_COMPATIBLE = Collections.emptyList();
}