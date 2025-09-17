package com.ragnarok.engine.job.firstjob;

import com.ragnarok.engine.enums.JobTier;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.stat.StatBlock;

import java.util.List;
import java.util.Map;

public class Swordman extends Job {

    /**
     * Mapa declarativo de los bonus de stat por nivel de job, ajustado a la
     * tabla de bonus clásica de iRO Wiki para el Swordsman.
     * Cada entrada representa el StatBlock específico que se gana en ese nivel.
     */

    @Override
    public String getId() {
        return "SWORDMAN";
    }

    @Override
    public String getJobName() {
        return "Swordsman";
    }




    private static final Map<Integer, StatBlock> JOB_BONUS_SCHEDULE = Map.ofEntries(
            // STR Bonuses
            Map.entry(2,  new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(14, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(33, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(40, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(47, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(49, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(50, new StatBlock(1, 0, 0, 0, 0, 0)),

            // AGI Bonuses
            Map.entry(30, new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(46, new StatBlock(0, 1, 0, 0, 0, 0)),

            // VIT Bonuses
            Map.entry(6,  new StatBlock(0, 0, 1, 0, 0, 0)),
            Map.entry(18, new StatBlock(0, 0, 1, 0, 0, 0)),
            Map.entry(38, new StatBlock(0, 0, 1, 0, 0, 0)),
            Map.entry(42, new StatBlock(0, 0, 1, 0, 0, 0)),

            // DEX Bonuses
            Map.entry(10, new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(22, new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(36, new StatBlock(0, 0, 0, 0, 1, 0)),

            // LUK Bonuses
            Map.entry(26, new StatBlock(0, 0, 0, 0, 0, 1)),
            Map.entry(44, new StatBlock(0, 0, 0, 0, 0, 1))
    );



    @Override
    protected Map<Integer, StatBlock> getJobBonusSchedule() {
        return JOB_BONUS_SCHEDULE;
    }

    @Override
    public int getLevelHpFactor() {
        return 8;
    }

    // ASPD

    @Override
    public double getBaseAttackDelaySeconds() {
        return 1.1;
    }

    @Override
    public boolean canDualWield() {
        return false;
    }

    // Job path

    @Override
    public JobTier getJobTier() {
        return JobTier.FIRST;
    }

    @Override
    public List<String> getPreviousJobIds() {
        return List.of("NOVICE");
    }

}