package stat;


import character.CharacterData;
import actor.ActorState;
import enums.Element;
import enums.Race;
import enums.Size;
import job.Job;

/**
 * El motor de cálculo de stats para JUGADORES.
 * Esta clase es un servicio "sin estado" (stateless). No almacena datos de un personaje,
 * sino que los recibe como parámetros, ejecuta las fórmulas y devuelve un estado final calculado.
 * Es la "cocina" de nuestra arquitectura.
 */
public class StatCalculator {

    public ActorState buildState(CharacterData data, Job job) {
        // Paso 1: Calcular stats primarios totales
        StatBlock totalStats = calculateTotalStats(data, job);

        // Paso 2: Calcular stats secundarios básicos (HP/SP)
        int maxHp = calculateMaxHp(data, job, totalStats);
        int maxSp = calculateMaxSp(data, job, totalStats);

        // --- PASO 3: CALCULAR NUEVOS STATS DE COMBATE ---
        Attack attack = calculateAttack(data, totalStats);
        int defense = calculateDefense(totalStats);
        int hitRate = calculateHitRate(data, totalStats);
        int fleeRate = calculateFleeRate(data, totalStats);
        int criticalRate = calculateCriticalRate(totalStats);
        int magicAttack = calculateMagicAttack(totalStats);
        int magicDefense = calculateMagicDefense(totalStats);
        double attackSpeed = 150.0; // Valor fijo por ahora

        // Paso 4: Obtener propiedades finales
        Race finalRace = job.getBaseRace();
        Size finalSize = job.getBaseSize();
        Element finalElement = job.getBaseElement();

        // Paso 5: Ensamblar y devolver el "plato final" completo.
        return new ActorState(
                data.id(),
                data.name(),
                maxHp, // Asumimos que al calcular, el currentHp es el máximo
                maxHp,
                maxSp,
                maxSp,
                totalStats,
                finalRace,
                finalSize,
                finalElement,
                attack,
                hitRate,
                attackSpeed,
                criticalRate,
                magicAttack,
                defense,
                magicDefense,
                fleeRate
        );
    }




    private StatBlock calculateTotalStats(CharacterData data, Job job){

        StatBlock baseStats = new StatBlock(data.baseStr(), data.baseAgi(), data.baseVit(), data.baseInt(), data.baseDex(), data.baseLuk());
        StatBlock jobBonuses = job.getJobStatBonuses(data.jobLevel());
        return baseStats.add(jobBonuses);

    }

    private int calculateMaxHp(CharacterData data, Job job, StatBlock totalStats) {
        // ... (sin cambios)
        return job.getBaseHp() + (totalStats.vit() * job.getVitHpFactor()) + (data.baseLevel() * job.getLevelHpFactor());
    }

    private int calculateMaxSp(CharacterData data, Job job, StatBlock totalStats) {
        // ... (sin cambios)
        return job.getBaseSp() + (totalStats.intel() * job.getIntSpFactor());
    }



    // --- NUEVOS MÉTODOS DE CÁLCULO ---

    private Attack calculateAttack(CharacterData data, StatBlock totalStats) {
        int calculatedStatusAttack = totalStats.str() + (totalStats.dex() / 5) + (totalStats.luk() / 5);

        int calculatedWeaponAttack = 0; // Placeholder

        return new Attack(calculatedStatusAttack, calculatedWeaponAttack);
    }

    private int calculateDefense(StatBlock totalStats) {
        return totalStats.vit() / 2;
    }

    private int calculateMagicDefense(StatBlock totalStats) {
        return totalStats.intel() / 2;
    }

    private int calculateHitRate(CharacterData data, StatBlock totalStats) {
        return 175 + data.baseLevel() + totalStats.dex();
    }

    private int calculateFleeRate(CharacterData data, StatBlock totalStats) {
        return 100 + data.baseLevel() + totalStats.agi();
    }

    private int calculateCriticalRate(StatBlock totalStats) {
        return 1 + (totalStats.luk() / 3);
    }

    private int calculateMagicAttack(StatBlock totalStats) {
        return totalStats.intel() + (totalStats.intel() / 2);
    }

}
