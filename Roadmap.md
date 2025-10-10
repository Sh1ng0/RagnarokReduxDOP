# Roadmap del Proyecto Helheim Phase 1:


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


CLASE DE ORUQESTACIÓN DE METODOS QUE RECIBE LOS INPUTS DESDE EL UI, LLAMA A X METODO PARA HACER Y COSA (Por ejemplo, equipar un item, llama al servicio de equipar y al servicio de inventario)
## 🎨 Fase 4: Pipeline de Animación (Futuro)
Paralelamente, se puede empezar a trabajar en el sistema que dará vida a los personajes.

* **Diseño:** Formalizar el "Flujo de Trabajo Híbrido Artista-IA".
* **Implementación:** Crear el `AnimationService` orientado a datos.

## 🌌 Fase 5: Arquitectura a Gran Escala (La Visión Final)
Conceptos de alto nivel que guían el diseño para la escalabilidad multijugador.

* **Arquitectura Orientada a Eventos (EDA):** Desacoplar UI y Engine (Kafka).
* **Gestión de Estado "Caliente":** Usar Redis para el estado global en tiempo real.
* **Concurrencia Masiva:** Patrón "Actor por Sesión" con *Virtual Threads*.

ROADMAP PHASE 2

Roadmap del Proyecto "Helheim" (Actualizado)
Diseñar e Implementar el Sistema de Refinamiento (Refinement System)

Objetivo: Crear la lógica que calcule los bonos adicionales que otorgan los objetos según su nivel de refinamiento (ej. +ATK en armas, +DEF en armaduras) y cómo esto afecta al EquipmentStatCalculator.

Crear el CardService

Objetivo: Desarrollar el servicio que gestionará la lógica de engarzar y quitar cartas de las ranuras de un EquipInstance, asegurando que se respeten las reglas (slots disponibles, tipo de carta, etc.).

Finalizar los Tests para EquipmentStatCalculator

Objetivo: Con los sistemas de refinamiento y cartas ya definidos, crear una suite de tests exhaustiva que valide todos los casos de uso: bonos de equipo base, bonos de cartas, bonos de refinamiento y la correcta recolección de efectos especiales.

Añadir Mecánicas de Interacción (Draw Speed, etc.)

Objetivo: Empezar a implementar las mecánicas "divertidas" del documento de diseño, como el sistema de desenfundado (draw speed), para dar vida a la interacción del jugador.

Diseñar el Sistema de Habilidades (Skills)

Objetivo: Abordar el gran reto arquitectónico de cómo las habilidades (activas, pasivas, buffs) se integran con el sistema de perfiles y combate.

Construir el "Gran Switch" de Interacciones de Combate

Objetivo: Desarrollar el núcleo de la lógica de combate, que tomará los perfiles de los actores, las habilidades usadas y los efectos especiales para calcular el resultado de cada acción.

Implementar la Persistencia (Base de Datos)

Objetivo: Diseñar y escribir la lógica para guardar y cargar el estado de los jugadores (incluyendo sus EquipInstance únicos) y los templates de monstruos.

Escribir la Capa de Orquestación Final

Objetivo: Crear la clase "orquestadora" que una todas las piezas: recibirá los inputs del jugador, llamará a los servicios correspondientes (EquipmentService, CardService, calculadores, etc.) en el orden correcto y gestionará el estado final.

