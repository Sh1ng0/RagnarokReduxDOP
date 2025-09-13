package com.ragnarok.engine.repository;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.item.equip.*;
import com.ragnarok.engine.stat.StatBlock;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Repositorio en memoria para todos los items de equipo del juego.
 * Actúa como un catálogo centralizado y estático.
 */
public final class EquipmentRepository {

    private static final Map<Long, Equipment> ITEMS;

    static {
        // --- Definición de Items de Ejemplo ---

        // 1. Cotton Shirt [1]
        var cottonShirtBonuses = new EquipmentBonuses(
                StatBlock.ZERO, 0, 0, 0, 2, 0, 0, 0, 0,
                Collections.emptyMap(), Collections.emptyMap()
        );
        var cottonShirt = new ArmorItem(
                1001L, "Cotton Shirt", Element.NEUTRAL, ArmorType.ARMOR,
                cottonShirtBonuses, 1, Collections.emptyList(), 1
        );

        // 2. Knife [2]
        var knifeBonuses = new EquipmentBonuses(
                StatBlock.ZERO, 0, 0, 10, 0, 0, 0, 0, 0,
                Collections.emptyMap(), Collections.emptyMap()
        );
        var knife = new WeaponItem(
                2001L, "Knife", Element.NEUTRAL, WeaponType.DAGGER,
                knifeBonuses, 1, Collections.emptyList(), 2
        );

        // --- Población del Mapa ---
        ITEMS = Map.of(
                cottonShirt.id(), cottonShirt,
                knife.id(), knife
                // Aquí añadiremos todos los futuros items...
        );
    }

    /**
     * Constructor privado para prevenir la instanciación.
     */
    private EquipmentRepository() {}

    /**
     * Busca un EquipmentItem por su ID único.
     * @param itemId el ID del item a buscar.
     * @return el EquipmentItem correspondiente.
     * @throws IllegalArgumentException si no se encuentra el item.
     */
    public static Equipment findById(long itemId) {
        Equipment item = ITEMS.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("No se encontró ningún EquipmentItem con el ID: " + itemId);
        }
        return item;
    }

    /**
     * Busca una lista de EquipmentItems a partir de una lista de IDs.
     * @param itemIds la lista de IDs a buscar.
     * @return una lista inmutable con los EquipmentItems encontrados.
     */
    public static List<Equipment> findByIds(List<Long> itemIds) {
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
