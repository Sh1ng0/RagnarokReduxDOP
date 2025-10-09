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

public class EquipmentStatCalculatorDRAFT2 {

    public PlayerProfile calculate(BaseProfile nakedProfile, CharacterEquipment equipment) {

        // --- Paso 1: Acumular bonos y efectos ---
        List<EquipInstance> equippedItems = Stream.of(
                equipment.rightHand(), equipment.leftHand(), equipment.headTop(), equipment.headMid(),
                equipment.headLow(), equipment.armor(), equipment.garment(), equipment.footgear(),
                equipment.accessory1(), equipment.accessory2()
        ).filter(Objects::nonNull).toList();

        // --- AJUSTE: Acumuladores separados ---
        EquipmentBonuses totalSecondaryBonuses = EquipmentBonuses.ZERO;
        StatBlock totalPrimaryBonuses = StatBlock.ZERO;
        List<CardEffect> specialEffects = new ArrayList<>();

        for (EquipInstance item : equippedItems) {
            // 1. Añadimos los bonos del propio equipo.
            // Los separamos para acumularlos por separado.
            EquipmentBonuses itemTemplateBonuses = item.getItemTemplate().bonuses();
            totalPrimaryBonuses = totalPrimaryBonuses.add(itemTemplateBonuses.primaryStats());
            totalSecondaryBonuses = totalSecondaryBonuses.add(itemTemplateBonuses); // Se suman stats secundarios y primarios, pero los primarios los reiniciaremos

            // 2. Añadimos los bonos y efectos de las cartas del item
            if (item.getSocketedCards() != null) {
                for (CardTemplate card : item.getSocketedCards()) {
                    for (CardEffect effect : card.effects()) {
                        if (effect instanceof CardEffect.StatBonus sb) {
                            // --- AJUSTE: Dispatch directo a los acumuladores ---
                            switch (sb.attribute()) {
                                case STR -> totalPrimaryBonuses = totalPrimaryBonuses.add(new StatBlock(sb.value(), 0, 0, 0, 0, 0));
                                case AGI -> totalPrimaryBonuses = totalPrimaryBonuses.add(new StatBlock(0, sb.value(), 0, 0, 0, 0));
                                case VIT -> totalPrimaryBonuses = totalPrimaryBonuses.add(new StatBlock(0, 0, sb.value(), 0, 0, 0));
                                case INT -> totalPrimaryBonuses = totalPrimaryBonuses.add(new StatBlock(0, 0, 0, sb.value(), 0, 0));
                                case DEX -> totalPrimaryBonuses = totalPrimaryBonuses.add(new StatBlock(0, 0, 0, 0, sb.value(), 0));
                                case LUK -> totalPrimaryBonuses = totalPrimaryBonuses.add(new StatBlock(0, 0, 0, 0, 0, sb.value()));

                                case MAX_HP -> totalSecondaryBonuses = totalSecondaryBonuses.withMaxHp(totalSecondaryBonuses.maxHp() + sb.value());
                                case ATK -> totalSecondaryBonuses = totalSecondaryBonuses.withAttack(totalSecondaryBonuses.attack() + sb.value());
                                // ... etc para el resto de stats secundarios ...
                            }
                        } else {
                            specialEffects.add(effect);
                        }
                    }
                }
            }
        }

        // --- AJUSTE: Combinación final ---
        // Creamos el contenedor final de bonos combinando los acumuladores.
        EquipmentBonuses totalBonuses = totalSecondaryBonuses.withPrimaryStats(totalPrimaryBonuses);

        // --- Paso 2 y 3 (sin cambios) ---
        StatBlock finalStats = nakedProfile.totalStats().add(totalBonuses.primaryStats());
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

        CharacterInventories inventories = (nakedProfile instanceof PlayerProfile p) ? p.inventories() : CharacterInventories.EMPTY;
        return new PlayerProfile(finalProfile, equipment, inventories, specialEffects);
    }

    // El método convertStatBonusToEquipmentBonuses() ya no es necesario y se ha eliminado.
}