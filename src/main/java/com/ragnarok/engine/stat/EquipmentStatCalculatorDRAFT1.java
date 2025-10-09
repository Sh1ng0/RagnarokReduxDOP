package com.ragnarok.engine.stat;

import com.ragnarok.engine.actor.BaseProfile;
import com.ragnarok.engine.actor.NakedProfile;
import com.ragnarok.engine.actor.PlayerProfile;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.Attribute;
import com.ragnarok.engine.item.card.model.CardEffect;
import com.ragnarok.engine.item.equip.model.EquipmentBonuses;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.template.CardTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


/**
 * A stateless service that calculates the final stats of a player
 * by applying bonuses from their equipped items.
 * <p>
 * It takes a "naked" BaseProfile and the character's equipment as input,
 * and produces a final, fully-equipped PlayerProfile as output.
 */


public class EquipmentStatCalculatorDRAFT1 {

        public PlayerProfile calculate(BaseProfile nakedProfile, CharacterEquipment equipment) {

            // --- Paso 1: Acumular bonos y efectos ---
            List<EquipInstance> equippedItems = Stream.of(
                    equipment.rightHand(), equipment.leftHand(), equipment.headTop(), equipment.headMid(),
                    equipment.headLow(), equipment.armor(), equipment.garment(), equipment.footgear(),
                    equipment.accessory1(), equipment.accessory2()
            ).filter(Objects::nonNull).toList();

            EquipmentBonuses totalBonuses = EquipmentBonuses.ZERO;
            List<CardEffect> specialEffects = new ArrayList<>();

            for (EquipInstance item : equippedItems) {
                // 1. Añadimos los bonos base del propio equipo
                totalBonuses = totalBonuses.add(item.getItemTemplate().bonuses());

                // 2. Añadimos los bonos y efectos de las cartas del item
                // ASUMIMOS que EquipInstance tiene un método getSocketedCards()
                if (item.getSocketedCards() != null) {
                    for (CardTemplate card : item.getSocketedCards()) { // <-- AJUSTE 1: Iteramos sobre CardTemplate
                        for (CardEffect effect : card.effects()) { // <-- AJUSTE 2: Accedemos a los efectos directamente
                            switch (effect) {
                                case CardEffect.StatBonus sb -> {
                                    EquipmentBonuses cardStatBonus = convertStatBonusToEquipmentBonuses(sb);
                                    totalBonuses = totalBonuses.add(cardStatBonus);
                                }
                                default -> specialEffects.add(effect);
                            }
                        }
                    }
                }
            }

            // --- Paso 2: Aplicar bonos numéricos (SIN CAMBIOS) ---
            StatBlock finalStats = nakedProfile.totalStats().add(totalBonuses.primaryStats());
            // ... (resto de cálculos de stats sin cambios)
            int finalMaxHp = (int) (nakedProfile.maxHp() * (1 + totalBonuses.maxHpPercent())) + totalBonuses.maxHp();
            int finalMaxSp = (int) (nakedProfile.maxSp() * (1 + totalBonuses.maxSpPercent())) + totalBonuses.maxSp();
            Attack finalAttack = new Attack(nakedProfile.attack().statAttack(), totalBonuses.attack());
            Defense finalDefense = new Defense(totalBonuses.defense(), nakedProfile.defense().flatReduction());
            MagicDefense finalMagicDefense = new MagicDefense(totalBonuses.magicDefense(), nakedProfile.magicDefense().flatReduction());
            int finalHit = nakedProfile.hitRate() + totalBonuses.hit();
            int finalCrit = nakedProfile.criticalRate() + totalBonuses.criticalRate();
            Flee finalFlee = new Flee(nakedProfile.flee().normalFlee() + totalBonuses.flee(), nakedProfile.flee().luckyDodge());

            BaseProfile finalProfile = new NakedProfile(
                    nakedProfile.id(), nakedProfile.name(), nakedProfile.baseLevel(), nakedProfile.jobId(),
                    finalMaxHp, finalMaxSp, finalStats, nakedProfile.race(), nakedProfile.size(), nakedProfile.element(),
                    finalAttack, finalHit, nakedProfile.attackDelayInTicks(), finalCrit,
                    nakedProfile.magicAttack(), finalDefense, finalMagicDefense, finalFlee,
                    nakedProfile.availableSkills()
            );

            // --- Paso 3: Construir el PlayerProfile final (SIN CAMBIOS) ---
            CharacterInventories inventories = (nakedProfile instanceof PlayerProfile p) ? p.inventories() : CharacterInventories.EMPTY;
            return new PlayerProfile(finalProfile, equipment, inventories, specialEffects);
        }

        /**
         * Helper method to translate a CardEffect.StatBonus into an EquipmentBonuses record. (SIN CAMBIOS)
         */
        private EquipmentBonuses convertStatBonusToEquipmentBonuses(CardEffect.StatBonus bonus) {
            Attribute attribute = bonus.attribute(); // Usamos el nuevo enum
            int value = bonus.value();

            return switch (attribute) {
                case STR -> EquipmentBonuses.ZERO.withPrimaryStats(new StatBlock(value, 0, 0, 0, 0, 0));
                case AGI -> EquipmentBonuses.ZERO.withPrimaryStats(new StatBlock(0, value, 0, 0, 0, 0));
                // ... etc para stats primarios

                case MAX_HP -> EquipmentBonuses.ZERO.withMaxHp(value);
                case ATK -> EquipmentBonuses.ZERO.withAttack(value);
                case DEF -> EquipmentBonuses.ZERO.withDefense(value);
                // ... etc para stats secundarios

                // Y para los porcentuales
                case MAX_HP_PERCENT -> EquipmentBonuses.ZERO.withMaxHpPercent(value / 100.0);

                default -> EquipmentBonuses.ZERO;
            };
        }
}