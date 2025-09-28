package com.ragnarok.engine.item.template;

import com.ragnarok.engine.item.card.model.CardEffect;

import java.util.List;

/**
 * The immutable blueprint for a card.
 * It holds the card's base data and a list of CardEffects
 * that describe the bonuses it gives.
 */
public record CardTemplate(
        long id,
        String name,
        List<CardEffect> effects
) implements ItemTemplate {
}