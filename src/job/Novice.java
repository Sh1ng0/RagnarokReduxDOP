package job;

import enums.Element;
import enums.Race;
import enums.Size;
import stat.StatBlock;

/**
 * Implementación del Job "Novice".
 * Hereda todos los valores por defecto de la clase Job,
 * ya que un Novice no tiene bonus de stats.
 */
public class Novice extends Job {

    @Override
    public String getJobName() {
        return "Novice";
    }

    public StatBlock getJobStatBonuses(int jobLevel) {
        // El default devuelve un bloque vacío (todo a cero)
        return new StatBlock(0, 0, 0, 0, 0, 0);
    }

    // --- Propiedades Base (con defaults para humanos) ---
    public Race getBaseRace() { return Race.DEMI_HUMAN; }
    public Size getBaseSize() { return Size.MEDIUM; }
    public Element getBaseElement() { return Element.NEUTRAL; }

    // --- Factores de Fórmula (con defaults de nuestro sistema simplificado) ---
    public int getBaseHp() { return 50; }
    public int getVitHpFactor() { return 15; }
    public int getLevelHpFactor() { return 5; }
    public int getBaseSp() { return 10; }
    public int getIntSpFactor() { return 10; }
}