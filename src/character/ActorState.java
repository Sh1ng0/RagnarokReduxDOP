package character;

import enums.Element;
import enums.Race;
import enums.Size;
import stat.StatBlock;

/**
 * Un record inmutable que representa la "foto" de un personaje en un instante.
 * Este es el objeto que el motor de combate (el switch DOP) utilizará.
 * No contiene lógica, solo los datos finales ya calculados.
 */
public record ActorState(
        long id,
        String name,
        int currentHp,
        int maxHp,
        StatBlock totalStats, // El bloque con los stats finales (str, vit, etc.)
        Race finalRace,
        Size finalSize,
        Element finalElement
        // ...y cualquier otro dato final que el combate necesite
) {}