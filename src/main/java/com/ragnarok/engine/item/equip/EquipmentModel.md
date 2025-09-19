# Architecture of the Equipment System

Este documento describe el diseño y el flujo de datos del sistema de equipamiento en el proyecto Ragnarok Online. La arquitectura se basa en los principios de la Programación Orientada a Datos (DOP), enfatizando una estricta separación entre datos inmutables y lógica sin estado.

-----

## Concepto Central: Plantilla vs. Instancia

La piedra angular del sistema es la distinción entre la **plantilla** de un objeto y su **instancia** única. Esto permite al sistema modelar con precisión la diferencia entre la definición maestra de un objeto y el objeto específico y tangible que posee un jugador.

  * **Plantilla (El Prototipo):** Una plantilla es la definición inmutable y compartida de un objeto. Contiene todos los datos estáticos: ataque/defensa base, nivel requerido, trabajos equipables, etc. Estos son los registros maestros almacenados en el `EquipmentRepository`. Por ejemplo, solo existe una plantilla de "Cuchillo" en todo el juego.

  * **Instancia (El Objeto Único):** Una instancia es un objeto específico y único que existe en el mundo del juego, creado a partir de una plantilla. Tiene su propia identidad única (UUID) y propiedades mutables y específicas de la instancia, como el nivel de refinamiento y las cartas encantadas. El inventario de un jugador es una colección de estas instancias únicas. Puede haber miles de instancias de "Cuchillo", cada una con su propia historia.

-----

## Desglose de Componentes

El sistema se divide en cuatro capas distintas que trabajan en conjunto.

### 1\. La Capa de Plantillas (Datos Estáticos)

Esta capa define lo que es cada objeto.

  * **`EquipmentRepository`**: Un catálogo en memoria que actúa como una base de datos para todas las plantillas de objetos. Es la única fuente de verdad para las definiciones de objetos.
  * **`Equipment (Sealed Interface)`**: Un contrato que asegura que todas las plantillas de objetos tengan propiedades comunes.
  * **`WeaponItem / ArmorItem (Records)`**: Los registros concretos e inmutables que sirven como las plantillas reales.
  * **`EquipmentBonuses (Record)`**: Un registro inmutable que define todas las bonificaciones de estadísticas que una plantilla o carta puede proporcionar.

### 2\. La Capa de Instancias (Datos Dinámicos)

Esta capa representa los objetos que los jugadores realmente poseen.

  * **`EquipInstance (Class)`**: Una clase mutable que representa un objeto único. Mantiene una referencia a su plantilla y añade su propio estado único (`refineLevel`, etc.). Su identidad se define por un UUID, no por sus propiedades.

### 3\. La Capa de Estado (Instantánea del Actor)

Esta capa captura el estado inmutable de un personaje en un momento dado.

  * **`CharacterEquipment (Record)`**: Una instantánea inmutable de los objetos `EquipInstance` únicos que un actor está usando actualmente en cada ranura.
  * **`ActorProfile (Record)`**: El contenedor de datos de nivel superior para un personaje, que incluye su `CharacterEquipment`.

### 4\. La Capa de Lógica (El "Motor")

Esta capa contiene la lógica sin estado que actúa sobre los datos.

  * **`EquipmentService`**: Un servicio sin estado cuya única responsabilidad es procesar las solicitudes de equipar/desequipar. Toma un `ActorProfile` y un `EquipInstance` como entrada y produce un nuevo `ActorProfile` como salida, sin modificar nunca el original.

-----

## Flujo de Datos: Equipar un Objeto

El siguiente diagrama ilustra el flujo de datos típico cuando un jugador equipa un objeto:

```text
[EquipmentRepository]
       |
(1. Provee la Plantilla para "Cuchillo")
       |
       V
[Creación/Drop de Objeto] -> (2. Crea `EquipInstance` de "Cuchillo") -> [Inventario del Jugador: List<EquipInstance>]
                                                                           |
                                     (3. El jugador elige equipar su instancia única de "Cuchillo")
                                                                           |
                                                                           V
+------------------------------------------------------------------------------------------------------------+
|                                                                                                            |
| [EquipmentService.equip(currentState, knifeInstance, RIGHT_HAND)]                                          |
|   |                                                                                                        |
|   +--> (4. Lee las reglas desde knifeInstance.getItemTemplate())                                            |
|   |                                                                                                        |
|   +--> (5. Crea un nuevo `CharacterEquipment` con el `knifeInstance` en la mano derecha)                   |
|   |                                                                                                        |
|   +--> (6. Lo envuelve en un nuevo `ActorProfile`)                                                            |
|   |                                                                                                        |
|   V                                                                                                        |
| (7. Devuelve `EquipResult` conteniendo el nuevo `ActorProfile` y cualquier objeto previamente equipado)      |
|                                                                                                            |
+------------------------------------------------------------------------------------------------------------+
```