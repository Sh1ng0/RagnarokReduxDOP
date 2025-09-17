¡Claro! Aquí tienes el texto con formato Markdown.

## 1. Casos de Éxito Básicos (Happy Path) ✅

Estos son los tests más sencillos para asegurar que la funcionalidad principal funciona.

### Equipar en un slot vacío
* **Acción:** Equipar `BASIC_SWORD` en la `RIGHT_HAND` del Swordman.
* **Resultado esperado:** La espada está en la mano derecha y la lista de ítems devueltos está vacía.

### Equipar sobre un slot ocupado (Swap)
* **Acción:** Equipar `KNIFE` en la `RIGHT_HAND` del Swordman, que ya tiene `BASIC_SWORD` equipada.
* **Resultado esperado:** El cuchillo está en la mano derecha y la lista de ítems devueltos contiene la instancia de `BASIC_SWORD`.

---

## 2. Casos de Fallo por Validación (Guard Clauses) 🛑

Aquí probamos que todas nuestras reglas de negocio detienen operaciones inválidas. En todos estos casos, el estado del actor no debe cambiar y la lista de devueltos debe estar vacía.

### Fallo por requisito de nivel
* **Acción:** Intentar equipar `HIGH_LEVEL_SWORD` (Nivel 99) en nuestro Swordman de nivel 50.

### Fallo por requisito de Job
* **Acción:** Intentar equipar `ASSASSIN_KATAR` en nuestro Swordman.

### Fallo por incompatibilidad de slot
* **Acción:** Intentar equipar `BASIC_SHIELD` en la `RIGHT_HAND`.

---

## 3. Escenarios de Armas a Dos Manos ⚔️

Estos tests validan la lógica de "barrido" y la interacción con objetos de una mano.

### Equipar arma 2H sobre manos vacías
* **Acción:** Equipar `TWO_HANDED_SWORD` en el Swordman.
* **Resultado esperado:** La espada 2H está en la mano derecha, la izquierda está vacía (`null`), y no se devuelve ningún ítem.

### Equipar arma 2H sobre 2 ítems (espada y escudo)
* **Acción:** Equipar `TWO_HANDED_SWORD` en un Swordman que ya lleva `BASIC_SWORD` y `BASIC_SHIELD`.
* **Resultado esperado:** La espada 2H está en la mano derecha, la izquierda vacía, y la lista de devueltos contiene las instancias de la espada y el escudo.

### Equipar ítem 1H sobre arma 2H
* **Acción:** Equipar `BASIC_SHIELD` en la `LEFT_HAND` de un Swordman que ya lleva `TWO_HANDED_SWORD`.
* **Resultado esperado:** El escudo está en la mano izquierda, la derecha está vacía, y la lista de devueltos contiene la instancia de la espada a dos manos.

---

## 4. Escenarios de Dual Wield 🤺

Estos tests validan la lógica de compatibilidad entre armas, usando a nuestro `assassinState`.

### Dual Wield exitoso
* **Acción:** Con `assassinState` y `BASIC_SWORD` en la mano derecha, equipar `KNIFE` (compatible) en la mano izquierda.
* **Resultado esperado:** Ambas armas están equipadas y no se devuelve ningún ítem.

### Dual Wield fallido por incompatibilidad
* **Acción:** Con `assassinState` y `BASIC_SWORD` en la mano derecha, intentar equipar `INCOMPATIBLE_AXE` en la mano izquierda.
* **Resultado esperado:** La operación falla, el estado no cambia y no se devuelve ningún ítem.

---
