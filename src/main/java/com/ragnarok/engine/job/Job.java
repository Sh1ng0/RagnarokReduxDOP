package com.ragnarok.engine.job;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.stat.StatBlock;

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


    /**
     * Devuelve el identificador único y en formato String para este Job.
     * Este ID debe coincidir con el usado en CharacterData.jobId.
     * @return El ID del Job (ej: "NOVICE", "SWORDSMAN").
     */
    public abstract String getId();

    public abstract String getJobName();


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

    // Cada job debe asegurarse de implementar su propio método
    public abstract double getBaseAttackDelaySeconds();

    public Race getBaseRace() {
        return Race.DEMI_HUMAN; // Por defecto para la mayoría de jobs de jugador
    }

    public Size getBaseSize() {
        return Size.MEDIUM; // Por defecto
    }

    public Element getBaseElement() {
        return Element.NEUTRAL; // Por defecto
    }



    public int getBaseHpConstant() {
        return 50;
    }

    /**
     * Devuelve cuántos puntos de HP se ganan por cada Nivel Base.
     */
    public int getLevelHpFactor() {
        return 5;
    }
    /**
     * Devuelve la constante de SP base para este Job.
     */
    public int getBaseSpConstant() {
        return 10;
    }

    /**
     * Devuelve cuántos puntos de SP base se ganan por cada Nivel Base.
     */
    public int getLevelSpFactor() {
        return 1; // Los Novice ganan muy poco SP por nivel
    }
}