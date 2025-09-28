# Design Philosophy: The `template` Package

## 1. Executive Summary

The package `com.ragnarok.engine.item.template` is the central catalog and the single source of truth for all item templates in the game.
Its design is based on the principles of **Data-Oriented Programming (DOP)** and leverages modern Java features (*records*, *sealed interfaces*) to create a robust, safe, and high-performance system.

---

## 2. Core Principles

### Pure and Immutable Data (DOP)

All templates are Java `record`s (`WeaponTemplate`, `ConsumableTemplate`, etc.).
This ensures that templates are simple containers of immutable data.
They contain no business logic; their only responsibility is to describe the base properties of an item.

### Type Safety (*sealed* Hierarchy)

The root of everything is `ItemTemplate`, a *sealed interface*.
This means the compiler knows the exact and complete list of all item template types that can exist in the game.
A new template type cannot be created without being explicitly registered in this hierarchy, eliminating an entire class of errors.

### Expressive Hierarchy

The system uses a hierarchy of nested *sealed* interfaces to group common functionality.
`ItemTemplate` is the root, and `EquipmentTemplate` extends `ItemTemplate` to group all equipment templates.
This allows all items to be treated generically when necessary, or specifically (as equipment) when required.

---

## 3. Capabilities and Potenti
