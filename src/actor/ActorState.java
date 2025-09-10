package actor;

import enums.Element;
import enums.Race;
import enums.Size;
import stat.Attack;
import stat.StatBlock;

/**
 * Un record inmutable que representa la "foto" de un personaje en un instante.
 * Este es el objeto que el motor de combate (el switch DOP) utilizará.
 * No contiene lógica, solo los datos finales ya calculados.
 */
public record ActorState(
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
        double attackSpeed,  // ASPD
        int criticalRate,

        // Atributos de ataque mágico (opcional por ahora, pero bueno tenerlos)
        int magicAttack,

        // Atributos de defensa física
        int defense, // El DEF del equipo, que resta directamente
           // El DEF de la VIT, que resta porcentualmente

        // Atributos de defensa mágica
        int magicDefense,


        // Atributos de evasión
        int fleeRate       // Evasión
) {}