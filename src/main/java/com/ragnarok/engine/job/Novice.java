package com.ragnarok.engine.job;

import com.ragnarok.engine.stat.StatBlock;

import java.util.Map;



/**
 * Implementación del Job "Novice".
 * Hereda todos los valores por defecto de la clase Job,
 * ya que un Novice no tiene bonus de stats.
 */
public class Novice extends Job {

    @Override
    public String getId() {
        return "NOVICE";
    }

    @Override
    public String getJobName() {
        return "Novice";
    }

    @Override
    protected Map<Integer, StatBlock> getJobBonusSchedule() {
        return Map.of();
    }
}