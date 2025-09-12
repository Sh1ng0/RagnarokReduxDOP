🗺️ Roadmap Actualizado del Proyecto
Fase 1: Núcleo de Stats y Validación ✅
(Esta fase está completada)

Finalizar StatCalculator: Se completaron y refactorizaron todas las fórmulas de stats base que no dependen de equipo.

Testing exhaustivo: Se configuró JUnit 5/AssertJ y se crearon tests unitarios y de escenario (arquetipo) que validan la correcta implementación de toda la lógica de cálculo.

Fase 2: Módulo de Items y Equipo ✏️
(Esta es nuestra fase actual)

Definir y Modelar los Datos: Finalizar el diseño de las estructuras de datos (records y enums) para el equipo (EquipmentItem, EquipmentSlot, EquipmentBonuses).

Crear el EquipmentRepository: Implementar el repositorio en memoria que actuará como catálogo central de todos los items del juego.

Testing del Módulo: Crear tests unitarios para el EquipmentRepository para asegurar que la carga y recuperación de nuestros items de ejemplo funciona correctamente.

Fase 3: Integración del Equipo en el Cálculo de Stats 🧩
Actualizar StatCalculator: Modificar el StatCalculator para que acepte una lista de equipo del personaje.

Implementar Lógica de Bonus: Reemplazar los valores placeholder = 0 con la lógica real que sume todos los EquipmentBonuses del equipo equipado.

Ampliar Pruebas: Actualizar los tests existentes, especialmente el test de arquetipo, para verificar que los stats se calculan correctamente cuando un personaje lleva equipo.

Fase 4: Desarrollo del Motor de Combate (DOP) 💥
Diseñar GameEvent: Crear la sealed interface que modelará todos los posibles eventos de combate (ataque, recibir daño, usar habilidad, etc.).

Construir el CombatEngine: Implementar el núcleo del motor de combate, que será una función pura que toma un estado del juego y un evento, y devuelve un nuevo estado del juego.



OUT OF SCOPE/ AFTER COMBATE ENGINE

MySQL db with Hypersistence