package job;

import enums.Element;
import enums.Race;
import enums.Size;
import stat.StatBlock;

import java.util.Map;

/**
 * Clase abstracta que sirve como plantilla para todos los Jobs del juego.
 * Sigue el principio de Composición sobre Herencia: un Actor TIENE un Job.
 *
 * Esta clase contiene los "factores" que definen la identidad de un Job:
 * - Sus bonus a los stats primarios.
 * - Los multiplicadores para las fórmulas de stats secundarios (HP, SP, etc.).
 */
public abstract class Job {

// id here...

    // We are lending base Race/Elment/Size from here

    /**
     * Cada Job debe tener un nombre único.
     * @return El nombre del Job.
     */
    protected abstract Map<Integer, StatBlock> getJobBonusSchedule();


    public final StatBlock getJobStatBonuses(int jobLevel){

        var bonusSchedule = getJobBonusSchedule();

        StatBlock accumulatedBonuses = StatBlock.ZERO;

        for (var entry : bonusSchedule.entrySet()) {
            if (entry.getKey() <= jobLevel) {
                accumulatedBonuses = accumulatedBonuses.add(entry.getValue());
            }
        }
        return accumulatedBonuses;
    }

    public double getBaseAttackDelaySeconds() {
        return 2.0; // Un valor por defecto de 2 segundos entre ataques.
    }

    public Race getBaseRace() {
        return Race.DEMI_HUMAN; // Por defecto para la mayoría de jobs de jugador
    }

    public Size getBaseSize() {
        return Size.MEDIUM; // Por defecto
    }

    public Element getBaseElement() {
        return Element.NEUTRAL; // Por defecto
    }


    public abstract String getJobName();

    // --- Bonus de Stats Primarios ---
    // Proporcionamos una implementación por defecto que devuelve 0.
    // Solo los Jobs que den un bonus real a un stat necesitarán sobreescribir el método correspondiente.


    // --- Factores para Fórmulas de Stats Secundarios (Nuestra Lógica Simplificada) ---
    // También proporcionamos valores por defecto basados en nuestra discusión.

    /**
     * Devuelve el HP base para este Job.
     */
    public int getBaseHp() {
        return 50;
    }

    /**
     * Devuelve cuántos puntos de HP se ganan por cada punto de VIT.
     */
    public int getVitHpFactor() {
        return 15;
    }

    /**
     * Devuelve cuántos puntos de HP se ganan por cada Nivel Base.
     */
    public int getLevelHpFactor() {
        return 5;
    }

    // Podríamos añadir los de SP también
    public int getBaseSp() { return 10; }
    public int getIntSpFactor() { return 10; }
}