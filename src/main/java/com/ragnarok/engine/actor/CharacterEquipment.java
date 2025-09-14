package com.ragnarok.engine.actor;

import com.ragnarok.engine.item.equip.Equipment;

/**
 * Un record inmutable que representa los slots de equipo de un personaje
 * y qué item (si lo hay) está equipado en cada uno.
 * Un valor 'null' en un campo significa que el slot está vacío.
 */
public record CharacterEquipment(

        Equipment rightHand,
        Equipment leftHand,

        Equipment headTop,
        Equipment headMid,
        Equipment headLow,
        Equipment armor,
        Equipment garment,
        Equipment footgear,
        Equipment accessory1,
        Equipment accessory2
) {

    /**
     * Una instancia estática que representa a un personaje sin ningún equipo.
     * Útil para inicializaciones.
     */
    public static final CharacterEquipment UNEQUIPPED = new CharacterEquipment(
            null, null, null, null, null, null, null, null, null, null
    );
}