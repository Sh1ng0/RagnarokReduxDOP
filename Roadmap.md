# Roadmap del Proyecto: "Ragnarok V"

Hemos completado la fase de cimentación de la lógica de equipamiento y ahora podemos empezar a construir los sistemas de juego principales sobre esa base sólida.

## ✅ Fase 1: Lógica y Testeo del Equipamiento (Completada)
Esta fase está 100% finalizada y validada. Representa la base de la interacción con ítems.

* **Diseño de la Arquitectura Central:** Patrón `Template` vs. `Instance`, inmutabilidad del `ActorProfile` y servicios *stateless*.
* **Refactor de Jerarquía de Jobs:** Lógica de "familias" de jobs implementada.
* **Suite de Tests Completa:** `EquipmentService` validado.

## ➡️ Fase 2: Sistema de Inventario y Refactor del Modelo de Actor (En Progreso)
Esta es la fase actual. Consiste en construir el sistema de inventario y luego refactorizar el modelo de actor para separar las responsabilidades de forma más limpia, sentando las bases para el cálculo de estadísticas de equipo.

* **1. Finalizar Sistema de Inventario (V1):**
  * **Crear el `InventoryService`:** Implementar la lógica *stateless* para añadir, quitar y usar ítems, utilizando el "switch de cartero" sobre la `sealed interface ItemInstance`.
  * **Escribir Suite de Tests:** Crear un conjunto de pruebas unitarias para validar la lógica del `InventoryService`.

* **2. Refactor Arquitectónico del Actor:**
  * **Crear el `CharacterProfile`:** Un nuevo `record` que compondrá el `ActorProfile` junto con `CharacterEquipment` y `CharacterInventories`. Será el contenedor de todo lo que es específico de un personaje jugable.
  * **Simplificar `ActorProfile`:** Se convertirá en el "perfil de combatiente universal", conteniendo solo los stats de combate aplicables tanto a jugadores como a monstruos.
  * **Crear el `CharacterProfileBuilder`:** Un nuevo servicio (la "segunda calculadora") responsable de:
    1.  Llamar a `StatCalculator` para obtener el `ActorProfile` base.
    2.  Calcular los bonos totales del equipo.
    3.  Aplicar los bonos al `ActorProfile` base.
    4.  Ensamblar y devolver el `CharacterProfile` final.

## 🚀 Fase 3: Sistemas de Juego Fundamentales (Futuro a Corto/Medio Plazo)
Con el nuevo modelo de actor establecido, podemos construir los sistemas de juego principales.

* **Motor de Combate (V1):** Implementar el "gran switch" que tomará dos `ActorProfile` y resolverá un ataque.
* **Sistema de Progresión:** Implementar la lógica de subida de nivel y la aplicación de *Job Bonus*.
* **Impacto de Stats de Equipo:** Utilizar el `CharacterProfileBuilder` para que las estadísticas de los ítems tengan un impacto real en el combate.

## 🎨 Fase 4: Pipeline de Animación (Futuro)
Paralelamente, se puede empezar a trabajar en el sistema que dará vida a los personajes.

* **Diseño:** Formalizar el "Flujo de Trabajo Híbrido Artista-IA".
* **Implementación:** Crear el `AnimationService` orientado a datos.

## 🌌 Fase 5: Arquitectura a Gran Escala (La Visión Final)
Conceptos de alto nivel que guían el diseño para la escalabilidad multijugador.

* **Arquitectura Orientada a Eventos (EDA):** Desacoplar UI y Engine (Kafka).
* **Gestión de Estado "Caliente":** Usar Redis para el estado global en tiempo real.
* **Concurrencia Masiva:** Patrón "Actor por Sesión" con *Virtual Threads*.