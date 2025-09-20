package com.ragnarok.engine.stat;


import com.ragnarok.engine.actor.ActorProfile;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.actor.Position;
import com.ragnarok.engine.character.CharacterData;

import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.monster.MonsterData;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


// QUIZA ESTO DEBERÍA DIVIDIRSE EN 3 CALCULADORAS ya que conforme se escale, se puede volver muy monolítica
// - Baseline
// - Stats de equipo
// - Skill buffs (?) other (?)
/**
 * The central calculation engine for all actor profiles in the game.
 * <p>
 * This class operates as a stateless service. It does not hold any actor data itself;
 * instead, it receives raw data objects (like {@code CharacterData} or {@code MonsterData})
 * as input, applies the game's mathematical formulas, and returns a final,
 * immutable {@link com.ragnarok.engine.actor.ActorProfile}.
 * <p>
 * It is the single source of truth for profile creation, ensuring that all actors,
 * whether they are players or monsters, are built using a consistent set of rules.
 */
public class StatCalculator {

    private static final int TICKS_PER_SECOND = 20;

    public ActorProfile buildState(CharacterData data, Job job) {

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

        // empty for now (Still to be implemented the skill stuff
        Map<String, Integer> availableSkills = Collections.emptyMap();

        // Equipment stuff

        CharacterEquipment playerEquipment = CharacterEquipment.UNEQUIPPED;

        return new ActorProfile(
                // Identificadores
                data.id(),
                data.name(),
                data.baseLevel(),
                job.getId(),

                // Recursos (HP/SP)
                maxHp,
                maxSp, // <-- CORREGIDO: Usamos la variable maxSp calculada

                // Stats y Propiedades
                totalStats,
                finalRace,
                finalSize,
                finalElement,

                // Atributos de Combate
                attack,
                hitRate,
                attackDelayInTicks,
                criticalRate,
                magicAttack,
                defense,
                magicDefense,
                flee,

                // Habilidades y Equipo
                availableSkills,
                Optional.of(playerEquipment)
        );
        }

    private StatBlock calculateTotalStats(CharacterData data, Job job){

        StatBlock baseStats = new StatBlock(data.baseStr(), data.baseAgi(), data.baseVit(), data.baseInt(), data.baseDex(), data.baseLuk());
        StatBlock jobBonuses = job.getJobStatBonuses(data.jobLevel());
        return baseStats.add(jobBonuses);

    }

    // HP / SP

    private int calculateMaxHp(CharacterData data, Job job, StatBlock totalStats) {
        // Paso 1: Calcular el HP base a partir del nivel y los factores del Job.
        double hpBase = job.getBaseHpConstant() + (data.baseLevel() * job.getLevelHpFactor());

        // Paso 2: Aplicar el multiplicador porcentual de la VIT.
        double maxHp = hpBase * (1.0 + totalStats.vit() / 100.0);

        return (int) maxHp;
    }

    private int calculateMaxSp(CharacterData data, Job job, StatBlock totalStats) {
        // Paso 1: Calcular el SP base a partir del nivel y los factores del Job.
        double spBase = job.getBaseSpConstant() + (data.baseLevel() * job.getLevelSpFactor());

        // Paso 2: Aplicar el multiplicador porcentual del INT.
        double maxSp = spBase * (1.0 + totalStats.intel() / 100.0);

        return (int) maxSp;
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

    // Seems too high somehow, keep an eye for balance

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

    // --- NUEVO MÉTODO PARA MONSTRUOS ---
    public ActorProfile buildState(MonsterData data) {
        StatBlock monsterStats = new StatBlock(
                data.str(), data.agi(), data.vit(),
                data.intel(), data.dex(), data.luk()
        );

        // La construcción de stats secundarios para monstruos queda centralizada aquí.
        Attack monsterAttack = new Attack(data.baseAttack(), 0);
        Defense monsterDefense = new Defense(0, data.vit());
        MagicDefense monsterMagicDefense = new MagicDefense(0, data.intel());
        Flee monsterFlee = new Flee(data.fleeRate(), 0);
        MagicAttack monsterMagicAttack = new MagicAttack(0, 0); // Placeholder

        return new ActorProfile(
                data.id(),
                data.name(),
                data.level(),
                "monster." + data.id(), // Convención para el "jobId" de un monstruo
                data.maxHp(),
                data.maxSp(),
                monsterStats,
                data.race(),
                data.size(),
                data.element(),
                monsterAttack,
                data.hitRate(),
                data.attackDelayInTicks(),
                data.criticalRate(),
                monsterMagicAttack,
                monsterDefense,
                monsterMagicDefense,
                monsterFlee,
                Collections.emptyMap(), // Los monstruos podrían tener skills
                Optional.empty()       // Los monstruos no tienen equipo
        );
    }

}
