package com.ragnarok.engine.stat;


import com.ragnarok.engine.character.CharacterData;
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

    // ARRANGE

    // ACT

    // ASSERT


    @Test
    @DisplayName("Test para el cálculo de MAX HP")
    void shouldCalculateMaxHpCorrectly() {

        // ARRANGE

        final int baseLevel = 10;
        final int baseVit = 20;
        final int expectedMaxHp = 120;

        Job noviceJob = new Novice();

        CharacterData characterData = new CharacterData(
                1L,
                "Test Player",
                baseLevel,
                1, // jobLevel no afecta a este cálculo
                "NOVICE", // jobId no se usa directamente en el calculator, pero es bueno ponerlo
                1, 1, baseVit, 1, 1, 1,
                List.of() // Lista de equipo vacía
        );

        // ACT

        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT

        assertThat(resultingState.maxHp()).isEqualTo(expectedMaxHp);
    }


    @Test
    @DisplayName("debería calcular el MaxSP correctamente basado en el nivel y la INT")
    void shouldCalculateMaxSpCorrectly() {
        // ARRANGE

        final int baseLevel = 20;
        final int baseInt = 30;
        final int expectedMaxSp = 39;

        Job noviceJob = new Novice();

        CharacterData characterData = new CharacterData(
                2L,
                "Test Player 2",
                baseLevel,
                1,
                "NOVICE",
                1, 1, 1, baseInt, 1, 1,
                List.of()
        );

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        //  ASSERT
        assertThat(resultingState.maxSp()).isEqualTo(expectedMaxSp);
    }

    // ATTACK

    @Test
    @DisplayName("debería calcular el Attack correctamente basado en STR, DEX y LUK")
    void shouldCalculateAttackCorrectly() {
        // ARRANGE

        final int baseStr = 40;
        final int baseDex = 20;
        final int baseLuk = 10;


        final int expectedStatAttack = 46;

        Job noviceJob = new Novice();

        CharacterData characterData = new CharacterData(
                3L,
                "Test Player 3",
                1, 1, "NOVICE",
                baseStr, 1, 1, 1, baseDex, baseLuk,
                List.of()
        );

        //  ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        //  ASSERT
        //
        assertThat(resultingState.attack().statAttack()).isEqualTo(expectedStatAttack);
        assertThat(resultingState.attack().weaponAttack()).isZero();
    }

    // DEFENSE

    @Test
    @DisplayName("debería calcular la Defensa correctamente basado en la VIT")
    void shouldCalculateDefenseCorrectly() {
        // ARRANGE

        final int baseVit = 55;


        final int expectedFlatDefense = 55;

        Job noviceJob = new Novice();

        CharacterData characterData = new CharacterData(
                4L,
                "Test Player 4",
                1, 1, "NOVICE",
                1, 1, baseVit, 1, 1, 1,
                List.of()
        );

        //  ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        //  ASSERT

        assertThat(resultingState.defense().flatReduction()).isEqualTo(expectedFlatDefense);
        assertThat(resultingState.defense().percentageReduction()).isZero(); // Verificamos el placeholder
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
        final int baseLevel = 60;
        final int baseAgi = 70;
        final int baseLuk = 40;

        final int expectedNormalFlee = 230; // 100 + 60 + 70
        final int expectedLuckyDodge = 5;   // 1 + (40 / 10)

        Job noviceJob = new Novice();
        CharacterData characterData = new CharacterData(
                7L, "Test Player 7",
                baseLevel, 1, "NOVICE",
                1, baseAgi, 1, 1, 1, baseLuk,
                List.of()
        );

        // ACT
        var resultingState = statCalculator.buildState(characterData, noviceJob);

        // ASSERT
        assertThat(resultingState.flee().normalFlee()).isEqualTo(expectedNormalFlee);
        assertThat(resultingState.flee().luckyDodge()).isEqualTo(expectedLuckyDodge);
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

    @Test
    @DisplayName("debería construir un ActorProfile completo y correcto para un arquetipo de Swordman")
    void shouldCorrectlyBuildStateForSwordmanArchetype() {
        // ARRANGE

        CharacterData characterData = new CharacterData(
                12L, "Dyrk",
                50, 40, "SWORDMAN",
                50, 27, 23, 1, 30, 1,
                List.of()
        );

        Job swordmanJob = new Swordman();

        // ACT
        var resultingState = statCalculator.buildState(characterData, swordmanJob);

        // ASSERT


        // 1. Stats Totales (Base + Job Bonus)
        assertThat(resultingState.totalStats().str()).isEqualTo(54); // 50 + 4
        assertThat(resultingState.totalStats().agi()).isEqualTo(28); // 27 + 1
        assertThat(resultingState.totalStats().vit()).isEqualTo(26); // 23 + 3
        assertThat(resultingState.totalStats().intel()).isEqualTo(1);  // 1 + 0
        assertThat(resultingState.totalStats().dex()).isEqualTo(33); // 30 + 3
        assertThat(resultingState.totalStats().luk()).isEqualTo(2);  // 1 + 1

        // 2. Recursos (HP/SP)
        assertThat(resultingState.maxHp()).isEqualTo(567);
        assertThat(resultingState.maxSp()).isEqualTo(60);

        // 3. Atributos Ofensivos
        assertThat(resultingState.attack().statAttack()).isEqualTo(60);
        assertThat(resultingState.attack().weaponAttack()).isZero();
        assertThat(resultingState.hitRate()).isEqualTo(258);
        assertThat(resultingState.criticalRate()).isEqualTo(1);
        assertThat(resultingState.attackDelayInTicks()).isEqualTo(15);

        // 4. Atributos Mágicos
        assertThat(resultingState.magicAttack().minMatk()).isEqualTo(1);
        assertThat(resultingState.magicAttack().maxMatk()).isEqualTo(1);

        // 5. Atributos Defensivos
        assertThat(resultingState.defense().flatReduction()).isEqualTo(26);
        assertThat(resultingState.defense().percentageReduction()).isZero();
        assertThat(resultingState.magicDefense().flatReduction()).isEqualTo(1);
        assertThat(resultingState.magicDefense().percentageReduction()).isZero();
        assertThat(resultingState.flee().normalFlee()).isEqualTo(178);
        assertThat(resultingState.flee().luckyDodge()).isEqualTo(1);
    }












    }
