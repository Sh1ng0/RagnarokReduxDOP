package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.actor.ActorState;
import com.ragnarok.engine.character.CharacterData;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.EquipmentSlot;
import com.ragnarok.engine.item.equip.model.*;
import com.ragnarok.engine.item.instance.EquipInstance;
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

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Tests for EquipmentService")
class EquipmentServiceTest {

    private EquipmentService equipmentService;
    private StatCalculator statCalculator;

    // --- Actors for different test scenarios ---
    private ActorState swordmanState;
    private ActorState assassinState;

    // This will act as a mock inventory for our test character
    private List<EquipInstance> characterInventory;

    // --- Arsenal of TEMPLATES, our single source of truth ---
    private static final WeaponTemplate KNIFE_TPL = (WeaponTemplate) EquipmentRepository.findById(2001L);
    private static final WeaponTemplate SWORD_TPL = (WeaponTemplate) EquipmentRepository.findById(2002L);
    private static final ArmorTemplate SHIELD_TPL = (ArmorTemplate) EquipmentRepository.findById(1002L);
    private static final WeaponTemplate TWO_HANDED_SWORD_TPL = (WeaponTemplate) EquipmentRepository.findById(2003L);
    private static final WeaponTemplate HIGH_LEVEL_SWORD_TPL = (WeaponTemplate) EquipmentRepository.findById(2004L);
    private static final WeaponTemplate ASSASSIN_KATAR_TPL = (WeaponTemplate) EquipmentRepository.findById(2005L);
    private static final WeaponTemplate INCOMPATIBLE_AXE_TPL = (WeaponTemplate) EquipmentRepository.findById(2006L);

    @BeforeEach
    void setUp() {
        this.equipmentService = new EquipmentService();
        this.statCalculator = new StatCalculator();

        // 1. Create the base actor states
        CharacterData swordmanData = new CharacterData(12L, "Dyrk", 50, 40, "SWORDMAN", 50, 27, 23, 1, 30, 1, Collections.emptyList());
        Job swordmanJob = JobRepository.findById("SWORDMAN");
        this.swordmanState = statCalculator.buildState(swordmanData, swordmanJob);

        CharacterData assassinData = new CharacterData(13L, "Davinel", 50, 40, "ASSASSIN", 40, 60, 15, 1, 40, 20, Collections.emptyList());
        Job assassinJob = JobRepository.findById("ASSASSIN");
        this.assassinState = statCalculator.buildState(assassinData, assassinJob);

        // 2. Create a mock inventory with INSTANCES for the tests
        this.characterInventory = new ArrayList<>();
        this.characterInventory.add(new EquipInstance(SWORD_TPL));    // Inventory slot 0
        this.characterInventory.add(new EquipInstance(SHIELD_TPL));   // Inventory slot 1
        this.characterInventory.add(new EquipInstance(TWO_HANDED_SWORD_TPL)); // Inventory slot 2
        this.characterInventory.add(new EquipInstance(KNIFE_TPL));    // Inventory slot 3
    }


    @Test
    @DisplayName("Should equip an item on an empty slot successfully")
    void equip_onEmptySlot_shouldSucceed() {
        // GIVEN (Arrange)
        // El setUp() ya nos da un swordmanState con el equipo vacío.
        // Obtenemos la instancia única de la espada de nuestro "inventario".
        ActorState initialActorState = this.swordmanState;
        EquipInstance swordToEquip = this.characterInventory.get(0); // SWORD_TPL instance

        // Verificación inicial para asegurar que el slot está realmente vacío.
        assertTrue(initialActorState.equipment().isPresent());
        assertNull(initialActorState.equipment().get().rightHand(), "Precondition failed: Right hand should be empty.");

        // WHEN (Act)
        // Ejecutamos la acción de equipar la espada en la mano derecha.
        EquipResult result = equipmentService.equip(initialActorState, swordToEquip, EquipmentSlot.RIGHT_HAND);

        // THEN (Assert)
        // 1. Verificamos que no se devolvió ningún ítem, ya que el slot estaba vacío.
        assertTrue(result.returnedItems().isEmpty(), "No items should be returned when equipping on an empty slot."); // <-- CORREGIDO

        // 2. Obtenemos el nuevo estado y confirmamos que es una instancia diferente.
        ActorState newState = result.newState();
        assertNotSame(initialActorState, newState, "The new state must be a different instance from the original.");

        // 3. Verificamos que el equipo ha sido actualizado correctamente.
        CharacterEquipment newEquipment = newState.equipment().orElseThrow();
        assertSame(swordToEquip, newEquipment.rightHand(), "The sword instance should be in the right hand.");

        // 4. Verificamos que los otros slots importantes siguen vacíos.
        assertNull(newEquipment.leftHand(), "The left hand should remain empty.");
    }


}