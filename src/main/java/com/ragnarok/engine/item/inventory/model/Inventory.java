package com.ragnarok.engine.item.inventory.model;

import com.ragnarok.engine.item.instance.ItemInstance;

import java.util.List;
import java.util.Map;

/**
 * A generic, immutable record representing a container for a specific type of item.
 * It uses a Map for high-performance lookups (O(1)) while preserving insertion order
 * by expecting a LinkedHashMap implementation.
 *
 * @param <K> The type of the key used to identify items (e.g., UUID for unique items, Long for templates).
 * @param <V> The type of {@link ItemInstance} this inventory can hold.
 * @param items A map of the items currently in this inventory.
 * @param capacity The maximum number of unique item stacks/instances this inventory can hold.
 */
public record Inventory<K, V extends ItemInstance>(Map<K, V> items, int capacity) {
}