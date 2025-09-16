package com.ragnarok.engine.item.equip.model;

/**
 * Represents an auto-cast (proc) effect on a piece of equipment.
 *
 * @param skillId    The ID of the skill to be cast.
 * @param skillLevel The level at which the skill is cast.
 * @param chance     The probability of the effect triggering (e.g., 0.03 for 3%).
 */
public record AutocastBonus(String skillId, int skillLevel, double chance) {}