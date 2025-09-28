package com.ragnarok.engine.item.template;


import com.ragnarok.engine.item.consumable.model.ConsumableEffect;

import java.util.List;

public record ConsumableTemplate(
        long id,
        String name,
        List<ConsumableEffect> effects
) implements ItemTemplate {

}