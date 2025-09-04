package stat;

import enums.Element;
import enums.Race;
import enums.Size;
import enums.StatusEffect;
import job.Job;
import job.Novice;

import java.util.HashSet;


@Deprecated
public class StatLogic {



    private long id;
    private int baseLevel = 1;
    private int jobLevel = 1;

    private Job currentJob = new Novice();

    private int maxHP;
    private int maxSP;



    private int baseStr = 1;
    private int baseAgi = 1;
    private int baseVit = 1;
    private int baseInt = 1;
    private int baseDex = 1;
    private int baseLuk = 1;

    private Race race;
    private Element element;
    private Size size;


    private int currentHp;
    private HashSet<StatusEffect> statusEffects;



    // Constructor

    // Secondary stats should be methods here

    public int getTotalVit() {
        return this.baseVit + this.currentJob.getVitBonus(this.jobLevel);
    }

//    public StatBlock calculateTotalStats() {
//         1. Empezamos con los stats base del personaje
//        StatBlock baseStats = new StatBlock(this.baseStr, this.baseAgi, ...);
//
//         2. Obtenemos el bloque de bonus del Job
//        StatBlock jobBonuses = this.currentJob.getJobStatBonuses(this.jobLevel);
//
//         3. Obtenemos el bloque de bonus de TODO el equipo equipado
//         StatBlock equipmentBonuses = this.equipment.getTotalStatBonuses();
//
//         4. Obtenemos el bloque de bonus de TODOS los buffs activos
//         StatBlock buffBonuses = this.activeStatusEffects.getTotalStatBonuses();
//
//         5. Sumamos todos los bloques en un pipeline
//        return baseStats
//                .add(jobBonuses);
//         .add(equipmentBonuses)
//         .add(buffBonuses);
//    }

    public int getMaxHp() {
        // 1. Pedimos al Job actual los "ingredientes" de la fórmula.
        int baseHpFromJob = currentJob.getBaseHp();
        int hpPerVit = currentJob.getVitHpFactor();
        int hpPerLevel = currentJob.getLevelHpFactor();

        // 2. Obtenemos la VIT total del personaje.
        int totalVit = getTotalVit();

        // 3. Aplicamos nuestra fórmula lineal y simple.
        int maxHp = baseHpFromJob + (totalVit * hpPerVit) + (this.baseLevel * hpPerLevel);

        return maxHp;
    }



}
