package com.ragnarok.engine.repository;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.item.defaults.EquipmentDefaults;
import com.ragnarok.engine.item.equip.model.*;
import com.ragnarok.engine.item.template.ArmorTemplate;
import com.ragnarok.engine.item.template.EquipmentTemplate;
import com.ragnarok.engine.item.template.WeaponTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Repositorio en memoria para todos los items de equipo del juego.
 * Actúa como un catálogo centralizado y estático.
 */
/**
 * An in-memory repository that acts as a catalog for all available Equipment items.
 * This serves as a single source of truth for item data, simulating a database.
 */
public final class EquipmentRepository {

    private static final Map<Long, EquipmentTemplate> ITEMS;

    static {
        // --- Item Definitions ---

        // Armor
        var cottonShirt = new ArmorTemplate(1001L, "Cotton Shirt", Element.NEUTRAL, ArmorType.ARMOR, EquipmentBonuses.ZERO.withDefense(2), 1, Collections.emptyList(), 1);
        var basicShield = new ArmorTemplate(1002L, "Shield", Element.NEUTRAL, ArmorType.SHIELD, EquipmentBonuses.ZERO.withDefense(1), 1, List.of("SWORDMAN"), 1);

        // Weapons
        var knife = new WeaponTemplate(2001L, "Knife", Element.NEUTRAL, WeaponType.DAGGER,0.7, EquipmentBonuses.ZERO.withAttack(10), 1, Collections.emptyList(), 2, EquipmentDefaults.STANDARD_DAGGER_COMPATIBILITY);
        var basicSword = new WeaponTemplate(2002L, "Sword", Element.NEUTRAL, WeaponType.ONE_HANDED_SWORD, 1,EquipmentBonuses.ZERO, 1, List.of("SWORDMAN", "THIEF"), 1, EquipmentDefaults.STANDARD_1H_SWORD_COMPATIBILITY);
        var twoHandedSword = new WeaponTemplate(2003L, "Two-Handed Sword", Element.NEUTRAL, WeaponType.TWO_HANDED_SWORD, 1.5, EquipmentBonuses.ZERO, 25, List.of("SWORDMAN"), 2);
        var highLevelSword = new WeaponTemplate(2004L, "Blade of the Heavens", Element.HOLY, WeaponType.ONE_HANDED_SWORD,1, EquipmentBonuses.ZERO, 99, List.of("SWORDMAN"), 0);
        var assassinKatar = new WeaponTemplate(2005L, "Katar of the Shadows", Element.POISON, WeaponType.KATAR,1, EquipmentBonuses.ZERO, 40, List.of("ASSASSIN"), 2);
        var incompatibleAxe = new WeaponTemplate(2006L, "Battle Axe", Element.EARTH, WeaponType.ONE_HANDED_AXE, 1, EquipmentBonuses.ZERO, 10, List.of("SWORDMAN"), 1, EquipmentDefaults.NON_COMPATIBLE);


        // --- Populate the Map ---
        ITEMS = Map.of(
                cottonShirt.id(), cottonShirt,
                basicShield.id(), basicShield,
                knife.id(), knife,
                basicSword.id(), basicSword,
                twoHandedSword.id(), twoHandedSword,
                highLevelSword.id(), highLevelSword,
                assassinKatar.id(), assassinKatar,
                incompatibleAxe.id(), incompatibleAxe
        );
    }

    private EquipmentRepository() {}

    public static EquipmentTemplate findById(long itemId) {
        EquipmentTemplate item = ITEMS.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("No EquipmentItem found with ID: " + itemId);
        }
        return item;
    }

    /**
     * Busca una lista de EquipmentItems a partir de una lista de IDs.
     * @param itemIds la lista de IDs a buscar.
     * @return una lista inmutable con los EquipmentItems encontrados.
     */
    public static List<EquipmentTemplate> findByIds(List<Long> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            return Collections.emptyList();
        }
        return itemIds.stream()
                .map(EquipmentRepository::findById)
                .toList();
    }
}

//EJEMPLO DE USO PARA EL FUTURO
//
//Equipment item = EquipmentRepository.findById(2001L);
//
//if (item instanceof WeaponItem weapon) {
//        // Es un arma, podemos acceder a sus propiedades únicas
//        System.out.println("Tipo de arma: " + weapon.type());
//        System.out.println("Compatibilidad: " + weapon.compatibleOffHandTypes());
//
//        } else if (item instanceof ArmorItem armor) {
//        // Es una armadura
//        System.out.println("Tipo de armadura: " + armor.type());
//        }
