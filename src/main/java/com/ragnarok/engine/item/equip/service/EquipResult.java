package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.actor.ActorState;
import com.ragnarok.engine.item.instance.EquipInstance;

import java.util.List;

/**
 * An immutable record that encapsulates the result of an equipment change.
 * This object holds the updated state of the actor and a reference to the item
 * that was unequipped in the process, if any.
 */
public record EquipResult(
        ActorState updateState,
        List<EquipInstance> returnedItems
) {}