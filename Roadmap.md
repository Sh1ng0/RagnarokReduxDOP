🗺️ The roadmap


1. Finalizar StatCalculator (Fórmulas Base) 🚀
   El primer paso es terminar la revisión de las fórmulas que no dependen de equipo (como maxHp, maxSp, etc.), sentando así las bases del sistema.

2. Fase de Pruebas Inicial 🧪
   Con la base lista, nos aseguramos de que todo funcione como debe:

Configurar las herramientas de testing: JUnit 5 y AssertJ.

Crear tests para toda la lógica fundamental que ya hemos definido, garantizando que es correcta antes de añadir más complejidad.

3. Desarrollar el Módulo de Item/Equipment ⚔️
   Ahora introducimos los objetos en el juego:

Definir las estructuras de datos para los objetos (Item, Equipment, etc.).

Crear un sistema para que un personaje pueda "equipar" estos objetos y beneficiarse de sus atributos.

4. Actualizar StatCalculator (Integración de Equipo) 🧩
   Volvemos al primer módulo para conectarlo todo:

Se reemplazan todos los valores provisionales (... = 0) con lógica real que lea las estadísticas del equipo que lleva el personaje.

5. Iniciar el Motor de Combate (DOP) 💥
   Con un ActorState ya completo y funcional (incluyendo los efectos del equipo), podemos empezar a desarrollar el núcleo de las interacciones y el combate.

