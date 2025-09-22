package com.ragnarok.engine.item.inventory.model;

import com.ragnarok.engine.item.instance.ItemInstance;

import java.util.List;

/**
 * Un 'record' genérico e inmutable que representa un contenedor para un tipo específico de ítem.
 * Mantiene una lista de ítems y define la capacidad máxima que puede almacenar.
 *
 * @param <T> El tipo de {@link ItemInstance} que este inventario puede contener.
 * @param items Una lista de los ítems actualmente en este inventario.
 * @param capacity El número máximo de ítems que este inventario puede contener.
 */
public record Inventory<T extends ItemInstance>(List<T> items, int capacity) {
}