package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.actor.ActorState;
import com.ragnarok.engine.item.equip.model.Equipment;

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