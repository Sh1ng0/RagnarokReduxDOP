package stat;


import actor.Position;
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

    private static final int TICKS_PER_SECOND = 20;

    public ActorState buildState(CharacterData data, Job job) {

        StatBlock totalStats = calculateTotalStats(data, job);

        // HP / SP
        int maxHp = calculateMaxHp(data, job, totalStats);
        int maxSp = calculateMaxSp(data, job, totalStats);

        // SECONDARY STATS

        Attack attack = calculateAttack(data, totalStats);

        Defense defense = calculateDefense(totalStats);
        int hitRate = calculateHitRate(data, totalStats);
        Flee flee = calculateFlee(data, totalStats);
        int criticalRate = calculateCriticalRate(totalStats);
        MagicAttack magicAttack = calculateMagicAttack(totalStats);
        MagicDefense magicDefense = calculateMagicDefense(totalStats);
        int attackDelayInTicks = calculateAttackDelayInTicks(job, totalStats);

        // RACE / SIZE / ELEMENT
        Race finalRace = job.getBaseRace();
        Size finalSize = job.getBaseSize();
        Element finalElement = job.getBaseElement();

        // build
        return new ActorState(
                0,
                Position.ORIGIN,
                data.id(),
                data.name(),
                maxHp,
                maxHp,
                maxSp,
                maxSp,
                totalStats,
                finalRace,
                finalSize,
                finalElement,
                attack,
                hitRate,
                attackDelayInTicks,
                criticalRate,
                magicAttack,
                defense,
                magicDefense,
                flee
        );
    }

    private StatBlock calculateTotalStats(CharacterData data, Job job){

        StatBlock baseStats = new StatBlock(data.baseStr(), data.baseAgi(), data.baseVit(), data.baseInt(), data.baseDex(), data.baseLuk());
        StatBlock jobBonuses = job.getJobStatBonuses(data.jobLevel());
        return baseStats.add(jobBonuses);

    }

    // HP / SP

    private int calculateMaxHp(CharacterData data, Job job, StatBlock totalStats) {

        return job.getBaseHp() + (totalStats.vit() * job.getVitHpFactor()) + (data.baseLevel() * job.getLevelHpFactor());
    }

    private int calculateMaxSp(CharacterData data, Job job, StatBlock totalStats) {

        return job.getBaseSp() + (totalStats.intel() * job.getIntSpFactor());
    }



    // ATTACK

    private Attack calculateAttack(CharacterData data, StatBlock totalStats) {
        int calculatedStatusAttack = totalStats.str() + (totalStats.dex() / 5) + (totalStats.luk() / 5);

        int calculatedWeaponAttack = 0; // Placeholder

        return new Attack(calculatedStatusAttack, calculatedWeaponAttack);
    }

    // DEFENSE

    private Defense calculateDefense(StatBlock totalStats) {
        // La defensa del equipo es porcentual. Placeholder hasta que haya equipo.
        int equipDef = 0;

        // La defensa de la VIT es plana y directa.
        int vitDef = totalStats.vit();

        return new Defense(equipDef, vitDef);
    }

    // MAGIC DEFENSE

    private MagicDefense calculateMagicDefense(StatBlock totalStats) {
        //Equipment stuff
        int equipMdef = 0;

        int intMdef = totalStats.intel();

        return new MagicDefense(equipMdef, intMdef);
    }

    // HIT

    private int calculateHitRate(CharacterData data, StatBlock totalStats) {
        return 175 + data.baseLevel() + totalStats.dex();
    }

    // FLEE

    private Flee calculateFlee(CharacterData data, StatBlock totalStats) {
        // La esquiva normal, basada en AGI y Nivel Base.
        int calculatedNormalFlee = 100 + data.baseLevel() + totalStats.agi();

        // La esquiva de la suerte, basada en LUK habrá que ver si la tratamos como un procentaje, trabajo del DOP
        int calculatedLuckyDodge = 1 + (totalStats.luk() / 10);

        return new Flee(calculatedNormalFlee, calculatedLuckyDodge);
    }

    // CRITICAL RATE

    private int calculateCriticalRate(StatBlock totalStats) {
        return 1 + (totalStats.luk() / 3);
    }

    // MAGIC ATTACK

    private MagicAttack calculateMagicAttack(StatBlock totalStats) {
        int intel = totalStats.intel();

        // Max
        int termMax = intel / 5;
        int maxMatk = intel + (termMax * termMax);

        // Min
        int termMin = intel / 7;
        int minMatk = intel + (termMin * termMin);

        return new MagicAttack(minMatk, maxMatk);
    }


    // ASPD keep an eye for balancing!!
    private int calculateAttackDelayInTicks(Job job, StatBlock totalStats) {

        double baseDelayInSeconds = job.getBaseAttackDelaySeconds();

        // 2. AGI y DEX reducen este retraso.
        // La fórmula es arbitraria, podemos ajustarla para el balanceo.
        double speedFactor = totalStats.agi() + (totalStats.dex() / 10.0);
        double finalDelayInSeconds = baseDelayInSeconds - (speedFactor * 0.01);

        // 3. Establecemos un límite mínimo para que el retraso no sea cero o negativo.
        // Ningún ataque puede ser más rápido que 4 ticks (0.2 segundos), por ejemplo.
        double cappedDelay = Math.max(0.2, finalDelayInSeconds);

        // 4. Convertimos el resultado final a ticks.
        return (int) (cappedDelay * TICKS_PER_SECOND);
    }

}
