

***

# Roadmap del Proyecto: "Ragnarok V"

Hemos completado la fase de cimentación de la lógica de equipamiento y ahora podemos empezar a construir los sistemas de juego principales sobre esa base sólida.

## ✅ Fase 1: Lógica y Testeo del Equipamiento (Completada)
Esta fase está 100% finalizada y validada.

* **Diseño de la Arquitectura Central:** Se ha establecido el patrón `Template` vs. `Instance`, la inmutabilidad del `ActorState` y los servicios *stateless*.
* **Refactor de Jerarquía de Jobs:** Se ha implementado la lógica para que el sistema entienda las "familias" de jobs (ej. `Assassin` hereda de `Thief`), haciendo las definiciones de los ítems más limpias y robustas.
* **Suite de Tests Completa:** El `EquipmentService` ha superado con éxito todos los casos de prueba de nuestra checklist, incluyendo:
    * Equipamiento básico y swaps.
    * Validaciones de nivel, job y slot.
    * Lógica de armas a dos manos.
    * Lógica de *Dual Wield* para el `Assassin`.

## ➡️ Fase 2: Expansión de Stats de Combate (Siguiente Paso Inmediato)
Este es nuestro próximo objetivo. Consiste en hacer que las estadísticas de los ítems tengan un impacto real en el combate.

* **Añadir Velocidad de Ataque a las Armas:**
    * Añadir un campo `int attackSpeed` al record `WeaponTemplate`.
    * Refactorizar el `StatCalculator` para que lea este nuevo atributo y calcule el `attackDelayInTicks` final del `ActorState`, incluyendo la lógica para el ASPD medio en caso de *dual wield*.

## 🚀 Fase 3: Sistemas de Juego Fundamentales (Futuro a Corto/Medio Plazo)
Una vez que el `StatCalculator` sea más completo, podemos construir los sistemas de juego principales.

* **Sistema de Inventario:** Crear el `record Inventory` y el `InventoryService` para gestionar el inventario del jugador, y el "orquestador" que coordine las acciones entre el inventario y el equipo.
* **Motor de Combate (V1):** Implementar la primera versión del "gran switch de pattern matching" que tomará dos `ActorState` y resolverá un ataque, calculando el daño final.
* **Sistema de Progresión:** Implementar la lógica de subida de nivel y la aplicación de los *Job Bonus* que ya están definidos en las clases de Job, centralizando este cálculo en el `StatCalculator`.

## 🎨 Fase 4: Pipeline de Animación (Futuro)
Paralelamente, se puede empezar a trabajar en el sistema que dará vida a los personajes.

* **Diseño:** Formalizar el "Flujo de Trabajo Híbrido Artista-IA".
* **Implementación:** Crear el `AnimationService` orientado a datos.
* **Característica Clave:** Implementar las "Animaciones por Stats" para que la progresión del personaje sea visual.

## 🌌 Fase 5: Arquitectura a Gran Escala (La Visión Final)
Estos son los conceptos de alto nivel que guían el diseño para asegurar que el proyecto pueda crecer hasta convertirse en un juego multijugador masivo.

* **Arquitectura Orientada a Eventos:** Desacoplar la UI y el Engine usando Kafka.
* **Gestión de Estado "Caliente":** Usar Redis para mantener el estado global en tiempo real de los jugadores online.
* **Concurrencia Masiva:** Implementar el patrón "Actor por Sesión" utilizando *Virtual Threads* para manejar miles de jugadores simultáneamente.