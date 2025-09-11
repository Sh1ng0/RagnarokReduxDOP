## Documentación del Proyecto: Motor de Combate Java DOP
Este documento resume la arquitectura y las decisiones de diseño para un motor de combate en Java moderno, con un fuerte énfasis en la Programación Orientada a Datos (DOP) y la simplicidad de diseño.

### Arquitectura Principal: "La Receta, la Cocina y el Plato"
El sistema se divide en tres capas conceptuales para una clara separación de responsabilidades:

La Receta (Datos Crudos): Representa los datos base, inmutables, que vendrían de una base de datos o un archivo de configuración.

CharacterData: Un record con los datos guardados de un jugador (nivel, stats asignados, job).

MonsterData: Un record con los stats fijos y finales de un monstruo.

La Cocina (Lógica de Cálculo): Servicios sin estado (stateless) responsables de transformar los datos crudos en un estado de combate funcional.

StatCalculator: Para jugadores. Toma CharacterData y un Job para calcular el ActorState completo, aplicando fórmulas de stats, bonus de job, etc.

MonsterFactory: Para monstruos. Un servicio más simple que mapea directamente un MonsterData a un ActorState.

El Plato (Estado de Combate): La estructura de datos unificada y final que representa a cualquier entidad en el combate.

ActorState: El record inmutable central de todo el proyecto. Es la única "moneda" que el motor de combate entiende. Contiene todos los stats finales, la posición, y el estado temporal de una entidad.

### Diseño de Stats Complejos: El Patrón de Records
Para evitar la complejidad de tener múltiples campos primitivos y capturar la "esencia" de las mecánicas del RO clásico, hemos encapsulado los stats de combate complejos en sus propios records.

Attack(statusAttack, weaponAttack): Separa el ataque que proviene de los stats del que proviene del equipo.

Defense(percentageReduction, flatReduction): Separa la defensa porcentual (del equipo) de la plana (de la VIT).

MagicAttack(minMatk, maxMatk): Captura la naturaleza de "rango" del daño mágico.

MagicDefense(percentageReduction, flatReduction): Sigue el mismo patrón que la defensa física, pero con INT.

Flee(normalFlee, luckyDodge): Separa la esquiva normal de la esquiva especial basada en LUK.

### El Sistema de Job para Jugadores
La progresión de los jugadores se gestiona mediante un sistema de Jobs que sigue estos principios:

Composición sobre Herencia: Un Actor tiene un Job.

Clase Abstracta Job: Se utiliza una clase abstracta como base para compartir lógica común, como el cálculo de bonus acumulados.

Template Method Pattern: La clase Job define el algoritmo para calcular los bonus, mientras que las subclases (Swordman, Novice) solo tienen la responsabilidad de proveer los datos (un Map<Integer, StatBlock> con la tabla de bonus).

### Preparación para Combate en Tiempo Real
Aunque el motor de combate en sí es agnóstico al tiempo, está diseñado para ser "orquestado" por un sistema externo en tiempo real (como un GameLoop).

El Contrato de Tiempo y Espacio: El ActorState incluye campos que forman un contrato con el GameLoop:

Position position: Define el "dónde".

long nextActionTick: Define el "cuándo", indicando el momento en el que el actor podrá volver a actuar.

Cálculo de attackDelayInTicks: Hemos reemplazado el concepto abstracto de ASPD por un cálculo directo del retraso en ticks entre ataques, un dato concreto y útil para el GameLoop.

### Visión del Motor de Combate (DOP)
La lógica de interacciones de combate (el núcleo DOP) vivirá en un paquete separado (combat) y se basará en:

Una sealed interface GameEvent: Creará un universo cerrado y seguro de todas las acciones posibles (PlayerAttackEvent, SkillUsedEvent, etc.).

Pattern Matching Exhaustivo: El motor principal será un switch sobre GameEvent. Al ser sealed, el compilador nos obligará a gestionar todos los tipos de eventos posibles, eliminando la necesidad de un default y previniendo bugs en tiempo de compilación.

