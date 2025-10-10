package com.ragnarok.engine.stat;


import com.ragnarok.engine.actor.NakedProfile;
import com.ragnarok.engine.character.CharacterData;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.job.Novice;
import com.ragnarok.engine.job.firstjob.Swordman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Pruebas para el Calculador de Stats (StatCalculator)")
class StatCalculatorTest {


    private StatCalculator statCalculator;

    @BeforeEach
    void setUp(){

        System.out.println("Ejecutando setUp(): Nueva instancia de StatCalculator creada.");
        this.statCalculator = new StatCalculator();
    }


    // HP & SP

    @Test
    @DisplayName("debería calcular el MaxHP correctamente")
    void shouldCalculateMaxHpCorrectly() {
        // ARRANGE
        CharacterData characterData = new CharacterData(
                1L, "Test Player", 10, 1, "NOVICE",
                1, 1, 20, 1, 1, 1,
                List.of()
        );
        Job noviceJob = new Novice();

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.maxHp()).isEqualTo(120);
    }

    @Test
    @DisplayName("debería calcular el MaxSP correctamente")
    void shouldCalculateMaxSpCorrectly() {
        // ARRANGE
        CharacterData characterData = new CharacterData(
                2L, "Test Player 2", 20, 1, "NOVICE",
                1, 1, 1, 30, 1, 1,
                List.of()
        );
        Job noviceJob = new Novice();

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.maxSp()).isEqualTo(39);
    }


    // ATTACK

    @Test
    @DisplayName("debería calcular el Status Attack correctamente y el Weapon Attack debe ser cero")
    void shouldCalculateAttackCorrectly() {
        // ARRANGE
        CharacterData characterData = new CharacterData(
                3L, "Test Player 3", 1, 1, "NOVICE",
                40, 1, 1, 1, 20, 10,
                List.of()
        );
        Job noviceJob = new Novice();

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.attack().statAttack()).isEqualTo(46);
        assertThat(resultingState.attack().weaponAttack()).isZero();
    }


    // MATK

    @Test
    @DisplayName("debería calcular el Ataque Mágico (min y max) correctamente basado en la INT")
    void shouldCalculateMagicAttackCorrectly() {
        // ARRANGE

        final int baseInt = 35;


        final int expectedMinMatk = 60;

        final int expectedMaxMatk = 84;

        Job noviceJob = new Novice();
        CharacterData characterData = new CharacterData(
                11L, "Test Player 11",
                1, 1, "NOVICE",
                1, 1, 1, baseInt, 1, 1,
                List.of()
        );

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.magicAttack().minMatk()).isEqualTo(expectedMinMatk);
        assertThat(resultingState.magicAttack().maxMatk()).isEqualTo(expectedMaxMatk);
    }


    // DEFENSE

    @Test
    @DisplayName("debería calcular la Defensa Plana (VIT) correctamente y la porcentual debe ser cero")
    void shouldCalculateDefenseCorrectly() {
        // ARRANGE
        CharacterData characterData = new CharacterData(
                4L, "Test Player 4", 1, 1, "NOVICE",
                1, 1, 55, 1, 1, 1,
                List.of()
        );
        Job noviceJob = new Novice();

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.defense().flatReduction()).isEqualTo(55);
        assertThat(resultingState.defense().percentageReduction()).isZero();
    }


    // MDEF

    @Test
    @DisplayName("debería calcular la Defensa Mágica correctamente basado en la INT")
    void shouldCalculateMagicDefenseCorrectly() {
        //  ARRANGE

        final int baseInt = 42;
        final int expectedFlatMagicDefense = 42;

        Job noviceJob = new Novice();

        CharacterData characterData = new CharacterData(
                5L, "Test Player 5",
                1, 1, "NOVICE",
                1, 1, 1, baseInt, 1, 1,
                List.of()
        );

        //  ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        //  ASSERT
        assertThat(resultingState.magicDefense().flatReduction()).isEqualTo(expectedFlatMagicDefense);
        assertThat(resultingState.magicDefense().percentageReduction()).isZero(); // Verificamos el placeholder
    }


    // HIT

    @Test
    @DisplayName("debería calcular el Hit Rate correctamente basado en nivel y DEX")
    void shouldCalculateHitRateCorrectly() {
        // ARRANGE
        final int baseLevel = 50;
        final int baseDex = 30;
        final int expectedHitRate = 255; // 175 + 50 + 30

        Job noviceJob = new Novice();
        CharacterData characterData = new CharacterData(
                6L, "Test Player 6",
                baseLevel, 1, "NOVICE",
                1, 1, 1, 1, baseDex, 1,
                List.of()
        );

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.hitRate()).isEqualTo(expectedHitRate);
    }


    // FLEE

    @Test
    @DisplayName("debería calcular el Flee y Lucky Dodge correctamente")
    void shouldCalculateFleeAndLuckyDodgeCorrectly() {
        // ARRANGE
        CharacterData characterData = new CharacterData(
                7L, "Test Player 7", 60, 1, "NOVICE",
                1, 70, 1, 1, 1, 40,
                List.of()
        );
        Job noviceJob = new Novice();

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.flee().normalFlee()).isEqualTo(230);
        assertThat(resultingState.flee().luckyDodge()).isEqualTo(5);
    }


    // ASPD

    @Test
    @DisplayName("debería calcular el retraso de ataque correctamente con stats moderados")
    void shouldCalculateAttackDelayCorrectlyForModerateStats() {

        // ARRANGE

        final int baseAgi = 50;
        final int baseDex = 30;

        final int expectedTicks = 15;



        Job noviceJob = new Novice(); // baseDelay = 2.0s
        CharacterData characterData = new CharacterData(
                8L, "Test Player 8",
                1, 1, "NOVICE",
                1, baseAgi, 1, 1, baseDex, 1,
                List.of()
        );

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.attackDelayInTicks()).isEqualTo(expectedTicks);
    }

    @Test
    @DisplayName("debería aplicar el retraso mínimo (cap) con stats muy altos")
    void shouldCapAttackDelayAtMinimumValueForHighStats() {
        //  ARRANGE

        final int baseAgi = 200;
        final int baseDex = 100;


        final int expectedTicks = 4;

        Job noviceJob = new Novice();
        CharacterData characterData = new CharacterData(
                9L, "Test Player 9",
                1, 1, "NOVICE",
                1, baseAgi, 1, 1, baseDex, 1,
                List.of()
        );

        //  ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        //  ASSERT
        assertThat(resultingState.attackDelayInTicks()).isEqualTo(expectedTicks);
    }


    // CRIT

    @Test
    @DisplayName("debería calcular el Critical Rate correctamente basado en la LUK")
    void shouldCalculateCriticalRateCorrectly() {
        // ARRANGE

        final int baseLuk = 33;
        final int expectedCriticalRate = 12; // 1 + (33 / 3) = 1 + 11 = 12

        Job noviceJob = new Novice();
        CharacterData characterData = new CharacterData(
                10L, "Test Player 10",
                1, 1, "NOVICE",
                1, 1, 1, 1, 1, baseLuk,
                List.of()
        );

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.criticalRate()).isEqualTo(expectedCriticalRate);
    }


    // --- PROFILE DEFAULTS ---

    @Test
    @DisplayName("debería crear un NakedProfile con las propiedades por defecto correctas (Raza, Tamaño, Elementos)")
    void shouldCreateNakedProfileWithCorrectDefaultProperties() {
        // ARRANGE
        // Usamos un personaje genérico, ya que estas propiedades no dependen de los stats ni del Job.
        CharacterData characterData = new CharacterData(
                99L, "Default Player", 1, 1, "NOVICE",
                1, 1, 1, 1, 1, 1,
                List.of()
        );
        Job noviceJob = new Novice();

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState).isInstanceOf(NakedProfile.class);
        assertThat(resultingState.race()).isEqualTo(Race.DEMI_HUMAN);
        assertThat(resultingState.size()).isEqualTo(Size.MEDIUM);
        assertThat(resultingState.attackElement()).isEqualTo(Element.NEUTRAL);
        assertThat(resultingState.defenseElement()).isEqualTo(Element.NEUTRAL);
    }


    // --- COMPREHENSIVE TEST ---

    @Test
    @DisplayName("debería construir un NakedProfile completo y correcto para un arquetipo de Swordman")
    void shouldCorrectlyBuildStateForSwordmanArchetype() {
        // ARRANGE
        CharacterData characterData = new CharacterData(
                12L, "Dyrk", 50, 40, "SWORDMAN",
                50, 27, 23, 1, 30, 1,
                List.of()
        );
        Job swordmanJob = new Swordman();

        // ACT
        var resultingState = statCalculator.buildState(characterData, swordmanJob);

        // ASSERT
        assertThat(resultingState.totalStats().str()).isEqualTo(54);
        assertThat(resultingState.totalStats().vit()).isEqualTo(26);
        assertThat(resultingState.maxHp()).isEqualTo(567);
        assertThat(resultingState.attack().statAttack()).isEqualTo(60);
        assertThat(resultingState.attack().weaponAttack()).isZero();
        assertThat(resultingState.defense().flatReduction()).isEqualTo(26);
        assertThat(resultingState.defense().percentageReduction()).isZero();
    }
}