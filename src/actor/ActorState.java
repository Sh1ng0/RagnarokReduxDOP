package actor;

import enums.Element;
import enums.Race;
import enums.Size;
import stat.*;

/**
 * Un record inmutable que representa la "foto" de un personaje en un instante.
 * Este es el objeto que el motor de combate (el switch DOP) utilizará.
 * No contiene lógica, solo los datos finales ya calculados.
 */
public record ActorState(

        // UI stuff
        long nextActionTick,
        Position position,
        // Identificadores básicos
        long id,
        String name,

        // Recursos de combate (HP/SP)
        int currentHp,
        int maxHp,
        int currentSp,
        int maxSp,

        // Stats primarios (el bloque completo)
        StatBlock totalStats,

        // Propiedades defensivas
        Race race,
        Size size,
        Element element,

        // --- NUEVOS STATS SECUNDARIOS ---
        // Atributos de ataque físico
        Attack attack,
        int hitRate,       // Precisión
        int attackDelayInTicks,  // ASPD
        int criticalRate,

        // Atributos de ataque mágico (opcional por ahora, pero bueno tenerlos)
        MagicAttack magicAttack,

        // Atributos de defensa física
        Defense defense,


        // Atributos de defensa mágica
        MagicDefense magicDefense,


        // Atributos de evasión
        Flee flee       // Evasión
) {}