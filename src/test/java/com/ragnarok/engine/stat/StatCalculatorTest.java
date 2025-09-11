package com.ragnarok.engine.stat;


import com.ragnarok.engine.character.CharacterData;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.job.Novice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Pruebas para el Calculador de Stats (StatCalculator)")
class StatCalculatorTest {


    private StatCalculator statCalculator;

    @BeforeEach
    void setUp(){

        System.out.println("Ejecutando setUp(): Nueva instancia de StatCalculator creada.");
        this.statCalculator = new StatCalculator();
    }


//    @Test
//    @DisplayName("debería calcular el MaxHP correctamente basado en el nivel y la VIT")
//    void shouldCalculateMaxHpCorrectly() {
//        // Arrange: Preparamos todos los datos de entrada necesarios.
//        // Usamos números sencillos para poder calcular el resultado esperado a mano.
//        final int baseLevel = 10;
//        final int baseVit = 20;
//        final int expectedMaxHp = 180; // El resultado que esperamos obtener.
//
//        // Creamos un Job. Usaremos Novice ya que conocemos sus constantes.
//        Job noviceJob = new Novice();
//
//        // Creamos los datos del personaje con los valores específicos para este test.
//        // El resto de stats no importan para este cálculo, así que pueden ser 1.
//        CharacterData characterData = new CharacterData(
//                1, "Test Player", baseLevel, 1,
//                1, 1, baseVit, 1, 1, 1
//        );
//
//        // Act: Ejecutamos el método que queremos probar.
//        // El método `buildState` es la "entrada" pública a la lógica del StatCalculator.
//        var resultingState = statCalculator.buildState(characterData, noviceJob);
//
//        // Assert: Verificamos que el resultado es el esperado.
//        // Usamos AssertJ para una sintaxis clara y legible.
//        // Nos centramos exclusivamente en el campo que nos interesa: maxHp.
//        assertThat(resultingState.maxHp()).isEqualTo(expectedMaxHp);
//    }




}
