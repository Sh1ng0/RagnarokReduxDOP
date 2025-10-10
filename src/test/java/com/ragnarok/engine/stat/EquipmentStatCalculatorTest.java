package com.ragnarok.engine.stat;


import com.ragnarok.engine.actor.BaseProfile;
import com.ragnarok.engine.actor.PlayerProfile;
import com.ragnarok.engine.character.CharacterData;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.Attribute;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.item.card.model.CardEffect;
import com.ragnarok.engine.item.card.model.CardSocketType;
import com.ragnarok.engine.item.equip.model.ArmorType;
import com.ragnarok.engine.item.equip.model.EquipmentBonuses;
import com.ragnarok.engine.item.equip.model.WeaponType;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.template.ArmorTemplate;
import com.ragnarok.engine.item.template.CardTemplate;
import com.ragnarok.engine.item.template.WeaponTemplate;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for EquipmentStatCalculator")
class EquipmentStatCalculatorTest {

    // --- Services ---
    private StatCalculator statCalculator;
    private EquipmentStatCalculator equipmentStatCalculator;

    // --- Test Data ---
    private BaseProfile nakedDyrkProfile; // El input para todos nuestros tests

    // --- ARSENAL DE TEMPLATES (Reutilizados de EquipmentServiceTest) ---
    private static final WeaponTemplate SWORD_TPL = new WeaponTemplate(
            2002L, "Sword", Element.NEUTRAL, WeaponType.ONE_HANDED_SWORD, 0.8,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 25, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            1, List.of("SWORDMAN", "ASSASSIN"), 1,
            List.of(WeaponType.ONE_HANDED_SWORD)
    );
    private static final ArmorTemplate SHIELD_TPL = new ArmorTemplate(
            1002L, "Shield", Element.NEUTRAL, ArmorType.SHIELD,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            1, List.of("SWORDMAN"), 0
    );

    // --- Cartas ---
    private static final CardTemplate MANTIS_CARD_TPL = new CardTemplate(
            5001L,
            "Mantis Card",
            List.of(new CardEffect.StatBonus(Attribute.STR, 2)), // Efecto: +2 STR
            CardSocketType.ACCESSORY // Asumiendo que existe este tipo
    );

    // --- Lógica para crear el anillo y engarzar la carta ---

    // 1. Creas una instancia única del anillo a partir de su template.
    // En este punto, 'myRing' es un objeto mutable con su propio UUID.
//    EquipInstance myRing = new EquipInstance(RING_TPL); [cite: 1]
//
//            // 2. Engarzas la carta en la instancia del anillo.
//            // Esto modifica el estado interno de 'myRing', añadiendo la carta a su lista de 'socketedCards'.
//            myRing.addCard(MANTIS_CARD_TPL); [cite: 1]


    // 3. ¡Listo! El objeto 'myRing' ya está preparado.
    // Ahora es una instancia única de "Ring" que contiene una "Mantis Card".
    // Este es el objeto que le pasarías al EquipmentService para ser equipado.
    // Su bono total implícito es +3 STR (+1 del anillo, +2 de la carta).

    // --- Accesorios ---
    private static final ArmorTemplate RING_TPL = new ArmorTemplate(
            3001L,
            "Ring",
            Element.NEUTRAL,
            ArmorType.ACCESSORY,
            // Bono base del anillo: +1 STR
            new EquipmentBonuses(new StatBlock(1, 0, 0, 0, 0, 0), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            1,
            Collections.emptyList(), // Equipable por todos los Jobs
            1 // 1 slot para cartas
    );

    @BeforeEach
    void setUp() {
        // 1. Instanciamos los servicios que vamos a usar.
        this.statCalculator = new StatCalculator();
        this.equipmentStatCalculator = new EquipmentStatCalculator();

        // 2. Creamos los datos en crudo de "Dyrk", nuestro personaje de pruebas.
        CharacterData dyrkData = new CharacterData(
                12L, "Dyrk", 50, 40, "SWORDMAN",
                50, 27, 23, 1, 30, 1, Collections.emptyList()
        );
        Job swordmanJob = JobRepository.findById("SWORDMAN");

        // 3. Usamos el StatCalculator para generar el NakedProfile.
        // Este será el estado "antes" que usaremos como entrada en todos los tests.
        this.nakedDyrkProfile = statCalculator.buildState(dyrkData, swordmanJob);

        // Verificación rápida para asegurar que el setup funcionó.
        assertThat(this.nakedDyrkProfile).isNotNull();
        assertThat(this.nakedDyrkProfile.name()).isEqualTo("Dyrk");
    }

    // --- Aquí empezarán nuestros casos de prueba ---

}