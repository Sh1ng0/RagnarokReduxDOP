package com.ragnarok.engine.job.secondjob_a;

import com.ragnarok.engine.enums.JobTier;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.stat.StatBlock;

import java.util.List;
import java.util.Map;

public class Assassin extends Job {

    /**
     * The declarative map of stat bonuses per job level, based on the classic
     * iRO Wiki bonus table for the Assassin job.
     */
    private static final Map<Integer, StatBlock> JOB_BONUS_SCHEDULE = Map.ofEntries(
            // STR Bonuses
            Map.entry(11, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(25, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(27, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(32, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(45, new StatBlock(1, 0, 0, 0, 0, 0)),
            Map.entry(48, new StatBlock(1, 0, 0, 0, 0, 0)),

            // AGI Bonuses
            Map.entry(1,  new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(2,  new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(3,  new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(15, new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(16, new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(17, new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(18, new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(19, new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(20, new StatBlock(0, 1, 0, 0, 0, 0)),
            Map.entry(21, new StatBlock(0, 1, 0, 0, 0, 0)),

            // VIT Bonuses
            Map.entry(6,  new StatBlock(0, 0, 1, 0, 0, 0)),
            Map.entry(8,  new StatBlock(0, 0, 1, 0, 0, 0)),

            // INT Bonuses
            Map.entry(4,  new StatBlock(0, 0, 0, 1, 0, 0)),
            Map.entry(14, new StatBlock(0, 0, 0, 1, 0, 0)),
            Map.entry(38, new StatBlock(0, 0, 0, 1, 0, 0)),
            Map.entry(42, new StatBlock(0, 0, 0, 1, 0, 0)),

            // DEX Bonuses
            Map.entry(9,  new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(24, new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(30, new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(31, new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(40, new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(41, new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(46, new StatBlock(0, 0, 0, 0, 1, 0)),
            Map.entry(50, new StatBlock(0, 0, 0, 0, 1, 0))
    );

    @Override
    public String getId() {
        return "ASSASSIN";
    }

    @Override
    public String getJobName() {
        return "Assassin";
    }

    @Override
    protected Map<Integer, StatBlock> getJobBonusSchedule() {
        return JOB_BONUS_SCHEDULE;
    }

    @Override
    public int getLevelHpFactor() {
        return 6; // Assassins are typically glass cannons.
    }

    @Override
    public double getBaseAttackDelaySeconds() {
        return 1.1; // Fast base speed, which is amplified by their high AGI bonuses.
    }

    @Override
    public boolean canDualWield() {
        // This is the key that unlocks all our dual wield logic.
        return true;
    }

    // Job path

    @Override
    public JobTier getJobTier() {
        // Asignamos el Assassin a la primera rama de los "Second Job".
        return JobTier.SECOND_A;
    }

    @Override
    public List<String> getPreviousJobIds() {
        return List.of("THIEF");
    }
}