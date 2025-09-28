La Diferencia Clave: Describir el Efecto vs. Aplicar el Efecto
El record HealEffect (La Descripción / El Blueprint) 📝:
Este record forma parte de la plantilla de un ítem (ConsumableTemplate). Una plantilla es un "blueprint", datos estáticos que definen un tipo de ítem para todo el juego, independientemente de qué jugador lo use o cuándo. No puede ni debe saber nada sobre un ActorView específico o su currentHp.

El enum Stat actúa como una etiqueta o un puntero. Simplemente describe de forma abstracta qué estadística se ve afectada. La plantilla de una "Poción Roja" dice: "Yo afecto a la estadística Stat.HP en una cantidad de 50". Es una descripción genérica.

El ConsumableService (La Acción / El Motor) ⚙️:
Este servicio es el que actúa. Cuando un jugador usa la poción, el servicio recibe dos cosas:

El ActorView del jugador (con su currentHp y currentSp).

La ConsumableTemplate del ítem que se está usando.

La lógica dentro del servicio es la que conecta ambos mundos:

Mira la plantilla y lee el HealEffect.

Ve la etiqueta Stat.HP.

Y dice: "Ah, vale, entonces tengo que coger el currentHp del ActorView que me han pasado y sumarle la cantidad".