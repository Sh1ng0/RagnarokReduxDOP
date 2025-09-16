package com.ragnarok.engine.item.equip;

import com.ragnarok.engine.actor.ActorState;

import java.util.List;

/**
 * Representa el resultado de una operación de equipamiento.
 * Contiene el nuevo estado del actor y, opcionalmente, el item que fue
 * reemplazado y debe ser devuelto al inventario.
 */
public record EquipResult(
        ActorState newState,
        List<Equipment> returnedItems
) {}