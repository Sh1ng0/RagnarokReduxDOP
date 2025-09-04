package job;

import enums.Element;
import enums.Race;
import enums.Size;

/**
 * Clase abstracta que sirve como plantilla para todos los Jobs del juego.
 * Sigue el principio de Composición sobre Herencia: un Actor TIENE un Job.
 *
 * Esta clase contiene los "factores" que definen la identidad de un Job:
 * - Sus bonus a los stats primarios.
 * - Los multiplicadores para las fórmulas de stats secundarios (HP, SP, etc.).
 */
public abstract class Job {



    /**
     * Cada Job debe tener un nombre único.
     * @return El nombre del Job.
     */
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

    public int getStrBonus(int jobLevel) {
        return 0;
    }

    public int getAgiBonus(int jobLevel) {
        return 0;
    }

    public int getVitBonus(int jobLevel) {
        return 0;
    }

    public int getIntBonus(int jobLevel) {
        return 0;
    }

    public int getDexBonus(int jobLevel) {
        return 0;
    }

    public int getLukBonus(int jobLevel) {
        return 0;
    }

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
}