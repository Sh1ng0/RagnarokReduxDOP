package com.ragnarok.engine.item.defaults;



import com.ragnarok.engine.item.equip.EquipmentType;

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

    /**
     * Compatibilidad para la mayoría de dagas (pueden ir con otra daga o un escudo).
     */
    public static final List<EquipmentType> STANDARD_DAGGER_COMPATIBILITY = List.of(EquipmentType.DAGGER, EquipmentType.SHIELD);

    /**
     * Compatibilidad para pistolas de una mano (solo con otra pistola).
     */
    public static final List<EquipmentType> STANDARD_PISTOL_COMPATIBILITY = List.of(EquipmentType.GUN);

    /**
     * Compatibilidad para espadas de una mano.
     */
    public static final List<EquipmentType> STANDARD_1H_SWORD_COMPATIBILITY = List.of(EquipmentType.ONE_HANDED_SWORD, EquipmentType.SHIELD);


    /**
     * Constante para items no compatibles (armas a dos manos, armaduras, etc.).
     */
    public static final List<EquipmentType> NON_COMPATIBLE = Collections.emptyList();
}