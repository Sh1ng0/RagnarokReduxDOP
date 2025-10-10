package com.ragnarok.engine.stat;

import com.ragnarok.engine.actor.BaseProfile;
import com.ragnarok.engine.actor.NakedProfile;
import com.ragnarok.engine.actor.PlayerProfile;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.Attribute;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.item.card.model.CardEffect;
import com.ragnarok.engine.item.equip.model.EquipmentBonuses;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.template.CardTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;




public class EquipmentStatCalculator {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentStatCalculator.class);

    /**
     * Calcula el perfil completo de un jugador, incluyendo equipo.
     *
     * @param nakedProfile El perfil base del jugador (sin equipo), proveniente de StatCalculator.
     * @param equipment    El equipo que lleva equipado el jugador.
     * @param inventories  Los inventarios del jugador.
     * @return Un PlayerProfile completo con todas las estadísticas finales y efectos.
     */
    public PlayerProfile calculate(BaseProfile nakedProfile, CharacterEquipment equipment, CharacterInventories inventories) {


        StatLogEvent.EQUIP_CALC_START.log(logger, nakedProfile.name());
        // --- Acumulador de Bonos ---
        record BonusAccumulator(
                StatBlock primaryBonuses,
                EquipmentBonuses secondaryBonuses,
                List<CardEffect> specialEffects
        ) {
            static final BonusAccumulator ZERO = new BonusAccumulator(StatBlock.ZERO, EquipmentBonuses.ZERO, new ArrayList<>());

            BonusAccumulator add(EquipmentBonuses templateBonuses) {
                return new BonusAccumulator(
                        this.primaryBonuses.add(templateBonuses.primaryStats()),
                        this.secondaryBonuses.add(templateBonuses),
                        this.specialEffects
                );
            }

            BonusAccumulator add(CardEffect effect) {
                return switch (effect) {
                    case CardEffect.StatBonus(var attribute, var value) -> applyStatBonus(attribute, value);
                    default -> {
                        var newEffects = new ArrayList<>(this.specialEffects);
                        newEffects.add(effect);
                        yield new BonusAccumulator(this.primaryBonuses, this.secondaryBonuses, newEffects);
                    }
                };
            }

            // --- MÉTODO COMPLETADO ---
            private BonusAccumulator applyStatBonus(Attribute attribute, int value) {
                return switch (attribute) {
                    // --- Stats Primarios ---
                    case STR -> new BonusAccumulator(primaryBonuses.add(new StatBlock(value, 0, 0, 0, 0, 0)), secondaryBonuses, specialEffects);
                    case AGI -> new BonusAccumulator(primaryBonuses.add(new StatBlock(0, value, 0, 0, 0, 0)), secondaryBonuses, specialEffects);
                    case VIT -> new BonusAccumulator(primaryBonuses.add(new StatBlock(0, 0, value, 0, 0, 0)), secondaryBonuses, specialEffects);
                    case INT -> new BonusAccumulator(primaryBonuses.add(new StatBlock(0, 0, 0, value, 0, 0)), secondaryBonuses, specialEffects);
                    case DEX -> new BonusAccumulator(primaryBonuses.add(new StatBlock(0, 0, 0, 0, value, 0)), secondaryBonuses, specialEffects);
                    case LUK -> new BonusAccumulator(primaryBonuses.add(new StatBlock(0, 0, 0, 0, 0, value)), secondaryBonuses, specialEffects);

                    // --- Stats Secundarios (Flats) ---
                    case MAX_HP -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withMaxHp(secondaryBonuses.maxHp() + value), specialEffects);
                    case MAX_SP -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withMaxSp(secondaryBonuses.maxSp() + value), specialEffects);
                    case ATK -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withAttack(secondaryBonuses.attack() + value), specialEffects);
                    case DEF -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withDefense(secondaryBonuses.defense() + value), specialEffects);
                    case MATK -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withMagicAttack(secondaryBonuses.magicAttack() + value), specialEffects);
                    case MDEF -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withMagicDefense(secondaryBonuses.magicDefense() + value), specialEffects);
                    case HIT -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withHit(secondaryBonuses.hit() + value), specialEffects);
                    case FLEE -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withFlee(secondaryBonuses.flee() + value), specialEffects);
                    case CRIT -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withCriticalRate(secondaryBonuses.criticalRate() + value), specialEffects);

                    // --- Stats Secundarios (Porcentuales) ---
                    case MAX_HP_PERCENT -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withMaxHpPercent(secondaryBonuses.maxHpPercent() + (double) value / 100), specialEffects);
                    case MAX_SP_PERCENT -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withMaxSpPercent(secondaryBonuses.maxSpPercent() + (double) value / 100), specialEffects);
                    case ATK_PERCENT -> new BonusAccumulator(primaryBonuses, secondaryBonuses.withAttackPercent(secondaryBonuses.attackPercent() + (double) value / 100), specialEffects);

                    default -> this;
                };
            }
        }

        // --- Paso 1: Recolectar Items Equipados ---
        List<EquipInstance> equippedItems = Stream.of(
                equipment.rightHand(), equipment.leftHand(), equipment.headTop(), equipment.headMid(),
                equipment.headLow(), equipment.armor(), equipment.garment(), equipment.footgear(),
                equipment.accessory1(), equipment.accessory2()
        ).filter(Objects::nonNull).toList();

        // --- Paso 2: Acumular Bonos ---
        var accumulator = BonusAccumulator.ZERO;
        for (EquipInstance item : equippedItems) {
            accumulator = accumulator.add(item.getItemTemplate().bonuses());
            if (item.getSocketedCards() != null) {
                for (CardTemplate card : item.getSocketedCards()) {
                    for (CardEffect effect : card.effects()) {
                        accumulator = accumulator.add(effect);
                    }
                }
            }
        }

        // --- Paso 3: Extraer resultados del Acumulador ---
        StatBlock totalPrimaryBonuses = accumulator.primaryBonuses();
        List<CardEffect> specialEffects = accumulator.specialEffects();
        EquipmentBonuses totalBonuses = accumulator.secondaryBonuses().withPrimaryStats(totalPrimaryBonuses);

        StatLogEvent.BONUS_ACCUMULATION_COMPLETE.log(logger, totalPrimaryBonuses, totalBonuses);
        StatLogEvent.SPECIAL_EFFECTS_COLLECTED.log(logger, specialEffects.size());

        Element finalAttackElement = nakedProfile.attackElement(); // Base: NEUTRAL
        if (equipment.rightHand() != null) {
            finalAttackElement = equipment.rightHand().getItemTemplate().element();
        }
        // Futuro: Aquí se podría añadir un override de una carta tipo "EndowWeapon"

        // 3.2: Cálculo del Elemento de Defensa (con cadena de prioridad)
        Element finalDefenseElement = nakedProfile.defenseElement(); // Base: NEUTRAL
        if (equipment.armor() != null && equipment.armor().getItemTemplate().element() != Element.NONE) {
            finalDefenseElement = equipment.armor().getItemTemplate().element(); // Prioridad 1: Armadura
        }
        for (CardEffect effect : specialEffects) {
            if (effect instanceof CardEffect.EndowArmorWithElement endowEffect) {
                finalDefenseElement = endowEffect.element(); // Prioridad 2: Carta
            }
        }

        StatLogEvent.ELEMENTS_DETERMINED.log(logger, finalAttackElement, finalDefenseElement);

        // --- Paso 4: Aplicar bonos numéricos al perfil base ---
        StatBlock finalStats = nakedProfile.totalStats().add(totalPrimaryBonuses);
        int finalMaxHp = (int) (nakedProfile.maxHp() * (1 + totalBonuses.maxHpPercent())) + totalBonuses.maxHp();
        int finalMaxSp = (int) (nakedProfile.maxSp() * (1 + totalBonuses.maxSpPercent())) + totalBonuses.maxSp();
        Attack finalAttack = new Attack(nakedProfile.attack().statAttack(), nakedProfile.attack().weaponAttack() + totalBonuses.attack());
        Defense finalDefense = new Defense(nakedProfile.defense().percentageReduction() + totalBonuses.defense(), nakedProfile.defense().flatReduction());
        MagicDefense finalMagicDefense = new MagicDefense(nakedProfile.magicDefense().percentageReduction() + totalBonuses.magicDefense(), nakedProfile.magicDefense().flatReduction());
        int finalHit = nakedProfile.hitRate() + totalBonuses.hit();
        int finalCrit = nakedProfile.criticalRate() + totalBonuses.criticalRate();
        Flee finalFlee = new Flee(nakedProfile.flee().normalFlee() + totalBonuses.flee(), nakedProfile.flee().luckyDodge());
        // TODO: Aplicar bonos de MATK y ASPD

        // --- Paso 5: Ensamblar el Perfil Final ---
        BaseProfile finalBaseProfile = new NakedProfile(
                nakedProfile.id(), nakedProfile.name(), nakedProfile.baseLevel(), nakedProfile.jobId(),
                finalMaxHp, finalMaxSp, finalStats, nakedProfile.race(), nakedProfile.size(),
                finalAttackElement,
                finalDefenseElement,
                finalAttack, finalHit, nakedProfile.attackDelayInTicks(), finalCrit,
                nakedProfile.magicAttack(),
                finalDefense, finalMagicDefense, finalFlee,
                nakedProfile.availableSkills()
        );

        var finalPlayerProfile = new PlayerProfile(finalBaseProfile, equipment, inventories, specialEffects);


        StatLogEvent.FINAL_PROFILE_ASSEMBLED.log(logger, finalPlayerProfile.name(), finalPlayerProfile);
        return finalPlayerProfile;
    }
}