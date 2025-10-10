package com.ragnarok.engine.item.equip.service;


import com.ragnarok.engine.actor.BaseProfile;
import com.ragnarok.engine.actor.PlayerProfile;
import com.ragnarok.engine.character.CharacterData;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.EquipmentSlot;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.template.ArmorTemplate;
import com.ragnarok.engine.item.template.WeaponTemplate;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.repository.EquipmentRepository;
import com.ragnarok.engine.repository.JobRepository;
import com.ragnarok.engine.stat.StatCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Tests for EquipmentService")
class EquipmentServiceTest {

    // --- Services ---
    private EquipmentService equipmentService;
    private StatCalculator statCalculator; // Needed to build the initial naked profile

    // --- Test Data ---
    private PlayerProfile initialSwordmanProfile;
    private List<EquipInstance> characterInventory;

    // --- Arsenal of TEMPLATES ---
    private static final WeaponTemplate SWORD_TPL = (WeaponTemplate) EquipmentRepository.findById(2002L);
    private static final WeaponTemplate KNIFE_TPL = (WeaponTemplate) EquipmentRepository.findById(2001L);
    // ... (otros templates que usaremos más adelante)

    @BeforeEach
    void setUp() {
        // 1. Instanciamos los servicios necesarios
        this.equipmentService = new EquipmentService();
        this.statCalculator = new StatCalculator();

        // 2. Creamos el estado base del personaje siguiendo la nueva arquitectura
        CharacterData swordmanData = new CharacterData(
                12L, "Dyrk", 50, 40, "SWORDMAN",
                50, 27, 23, 1, 30, 1, Collections.emptyList()
        );
        Job swordmanJob = JobRepository.findById("SWORDMAN");

        // 2a. Obtenemos el perfil "desnudo"
        BaseProfile nakedProfile = statCalculator.buildState(swordmanData, swordmanJob);

        // 2b. Ensamblamos el PlayerProfile completo inicial (sin equipo)
        this.initialSwordmanProfile = new PlayerProfile(
                nakedProfile,
                CharacterEquipment.UNEQUIPPED,     // Empezamos con el equipo vacío
                CharacterInventories.EMPTY,     // Y los inventarios vacíos
                Collections.emptyList()         // Y sin efectos especiales
        );

        // 3. Creamos un inventario mock con instancias de equipo
        this.characterInventory = new ArrayList<>();
        this.characterInventory.add(new EquipInstance(SWORD_TPL)); // slot 0
        this.characterInventory.add(new EquipInstance(KNIFE_TPL)); // slot 1
    }

    @Test
    @DisplayName("Should equip an item on an empty slot successfully")
    void equip_onEmptySlot_shouldSucceed() {
        // ARRANGE
        // Partimos del perfil inicial creado en el setUp
        PlayerProfile initialState = this.initialSwordmanProfile;
        EquipInstance swordToEquip = this.characterInventory.get(0);

        // Pre-condición: Verificamos que el slot está realmente vacío
        assertThat(initialState.equipment().rightHand()).isNull();

        // ACT
        EquipResult result = equipmentService.equip(initialState, swordToEquip, EquipmentSlot.RIGHT_HAND);

        // ASSERT
        // Verificamos que no se ha devuelto ningún objeto al inventario
        assertThat(result.returnedItems()).isEmpty();

        // Verificamos que se ha creado una nueva instancia del perfil (inmutabilidad)
        PlayerProfile finalState = result.updatedState();
        assertThat(finalState).isNotSameAs(initialState);

        // Verificamos que el nuevo objeto está en el slot correcto
        assertThat(finalState.equipment().rightHand()).isSameAs(swordToEquip);
        assertThat(finalState.equipment().leftHand()).isNull(); // El otro slot no debe haber cambiado
    }


}
