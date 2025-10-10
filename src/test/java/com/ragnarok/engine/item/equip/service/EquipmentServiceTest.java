package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.actor.ActorProfile;

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

import static org.junit.jupiter.api.Assertions.*;

//
//@DisplayName("Tests for EquipmentService")
//class EquipmentServiceTest {
//
//
//
//    private EquipmentService equipmentService;
//    private StatCalculator statCalculator;
//
//    // --- Actors for different test scenarios ---
//    private PlayerProfile swordmanState;
//    private PlayerProfile assassinState;
//
//    // This will act as a mock inventory for our test character
//    private List<EquipInstance> characterInventory;
//
//    // --- Arsenal of TEMPLATES, our single source of truth ---
//    private static final WeaponTemplate KNIFE_TPL = (WeaponTemplate) EquipmentRepository.findById(2001L);
//    private static final WeaponTemplate SWORD_TPL = (WeaponTemplate) EquipmentRepository.findById(2002L);
//    private static final ArmorTemplate SHIELD_TPL = (ArmorTemplate) EquipmentRepository.findById(1002L);
//    private static final WeaponTemplate TWO_HANDED_SWORD_TPL = (WeaponTemplate) EquipmentRepository.findById(2003L);
//    private static final WeaponTemplate HIGH_LEVEL_SWORD_TPL = (WeaponTemplate) EquipmentRepository.findById(2004L);
//    private static final WeaponTemplate ASSASSIN_KATAR_TPL = (WeaponTemplate) EquipmentRepository.findById(2005L);
//    private static final WeaponTemplate INCOMPATIBLE_AXE_TPL = (WeaponTemplate) EquipmentRepository.findById(2006L);
//
//    @BeforeEach
//    void setUp() {
//        this.equipmentService = new EquipmentService();
//        this.statCalculator = new StatCalculator();
//
//        // 1. Create the base actor states
//        CharacterData swordmanData = new CharacterData(12L, "Dyrk", 50, 40, "SWORDMAN", 50, 27, 23, 1, 30, 1, Collections.emptyList());
//        Job swordmanJob = JobRepository.findById("SWORDMAN");
//        this.swordmanState = statCalculator.buildState(swordmanData, swordmanJob);
//
//        CharacterData assassinData = new CharacterData(13L, "Davinel", 50, 40, "ASSASSIN", 40, 60, 15, 1, 40, 20, Collections.emptyList());
//        Job assassinJob = JobRepository.findById("ASSASSIN");
//        this.assassinState = statCalculator.buildState(assassinData, assassinJob);
//
//        // 2. Create a mock inventory with INSTANCES for the tests
//        this.characterInventory = new ArrayList<>();
//        this.characterInventory.add(new EquipInstance(SWORD_TPL));    // Inventory slot 0
//        this.characterInventory.add(new EquipInstance(SHIELD_TPL));   // Inventory slot 1
//        this.characterInventory.add(new EquipInstance(TWO_HANDED_SWORD_TPL)); // Inventory slot 2
//        this.characterInventory.add(new EquipInstance(KNIFE_TPL));    // Inventory slot 3
//    }
//
//
//    @Test
//    @DisplayName("Should equip an item on an empty slot successfully")
//    void equip_onEmptySlot_shouldSucceed() {
//
//        PlayerProfile initialActorProfile = this.swordmanState;
//        EquipInstance swordToEquip = this.characterInventory.get(0);
//
//
//        assertTrue(initialActorProfile.equipment().isPresent());
//        assertNull(initialActorProfile.equipment().get().rightHand(), "Precondition failed: Right hand should be empty.");
//
//
//
//        EquipResult result = equipmentService.equip(initialActorProfile, swordToEquip, EquipmentSlot.RIGHT_HAND);
//
//
//
//        assertTrue(result.returnedItems().isEmpty(), "No items should be returned when equipping on an empty slot."); // <-- CORREGIDO
//
//
//        PlayerProfile newState = result.updatedState();
//        assertNotSame(initialActorProfile, newState, "The new state must be a different instance from the original.");
//
//        CharacterEquipment newEquipment = newState.equipment().orElseThrow();
//        assertSame(swordToEquip, newEquipment.rightHand(), "The sword instance should be in the right hand.");
//
//
//        assertNull(newEquipment.leftHand(), "The left hand should remain empty.");
//    }
//
//
//    @Test
//    @DisplayName("Should swap an equipped item correctly")
//    void equip_onOccupiedSlot_shouldSwapAndReturnOldItem() {
//
//        // ARRANGE
//
//        EquipInstance swordInstance = this.characterInventory.get(0); // SWORD_TPL instance
//        PlayerProfile stateWithSword = equipmentService.equip(this.swordmanState, swordInstance, EquipmentSlot.RIGHT_HAND).updatedState();
//
//        EquipInstance knifeInstance = this.characterInventory.get(3);
//
//        assertSame(swordInstance, stateWithSword.equipment().get().rightHand(), "Precondition failed: Sword should be equipped.");
//
//        // ACT
//
//        EquipResult swapResult = equipmentService.equip(stateWithSword, knifeInstance, EquipmentSlot.RIGHT_HAND);
//
//        // ASSERT
//
//        List<EquipInstance> returnedItems = swapResult.returnedItems();
//        assertNotNull(returnedItems);
//        assertEquals(1, returnedItems.size(), "Exactly one item should have been returned.");
//        assertSame(swordInstance, returnedItems.get(0), "The returned item should be the original sword instance.");
//
//
//        PlayerProfile finalState = swapResult.updatedState(); // <-- CORREGIDO
//        assertNotSame(stateWithSword, finalState, "A new state object should have been created.");
//        assertSame(knifeInstance, finalState.equipment().get().rightHand(), "The knife should now be in the right hand.");
//    }
//
//
//    @Test
//    @DisplayName("Should fail to equip an item if level requirement is not met")
//    void equip_whenLevelIsTooLow_shouldFailAndReturnOriginalState() {
//
//        // ARRANGE
//       PlayerProfile initialState = this.swordmanState;
//        EquipInstance highLevelSword = new EquipInstance(HIGH_LEVEL_SWORD_TPL);
//
//        // ACT
//        EquipResult result = equipmentService.equip(initialState, highLevelSword, EquipmentSlot.RIGHT_HAND);
//
//        // ASSERT
//        PlayerProfile finalState = result.updatedState();
//        List<EquipInstance> returnedItems = result.returnedItems();
//
//        assertSame(initialState, finalState, "State should not change on a failed operation.");
//        assertTrue(returnedItems.isEmpty(), "No items should be returned on a failed operation.");
//        assertNull(finalState.equipment().get().rightHand(), "The item should not have been equipped.");
//    }
//
//
//    @Test
//    @DisplayName("Should fail to equip an item if job requirement is not met")
//    void equip_whenJobIsInvalid_shouldFailAndReturnOriginalState() {
//        // ARRANGE
//        ActorProfile initialState = this.swordmanState;
//        EquipInstance assassinKatar = new EquipInstance(ASSASSIN_KATAR_TPL);
//
//        // ACT
//        EquipResult result = equipmentService.equip(initialState, assassinKatar, EquipmentSlot.RIGHT_HAND);
//
//        // ASSERT
//        ActorProfile finalState = result.updatedState();
//        List<EquipInstance> returnedItems = result.returnedItems();
//
//        assertSame(initialState, finalState, "State should not change on a failed operation.");
//        assertTrue(returnedItems.isEmpty(), "No items should be returned on a failed operation.");
//    }
//
//
//    @Test
//    @DisplayName("Should fail to equip an item in an incompatible slot")
//    void equip_whenSlotIsIncompatible_shouldFailAndReturnOriginalState() {
//        // ARRANGE
//        ActorProfile initialState = this.swordmanState;
//        EquipInstance shieldInstance = this.characterInventory.get(1); // SHIELD_TPL instance
//
//        // ACT
//        EquipResult result = equipmentService.equip(initialState, shieldInstance, EquipmentSlot.RIGHT_HAND);
//
//        // ASSERT
//        ActorProfile finalState = result.updatedState();
//        List<EquipInstance> returnedItems = result.returnedItems();
//
//        assertSame(initialState, finalState, "State should not change on a failed operation.");
//        assertTrue(returnedItems.isEmpty(), "No items should be returned on a failed operation.");
//    }
//
//    @Test
//    @DisplayName("Should equip a two-handed weapon on empty hands")
//    void equip_twoHandedWeaponOnEmptyHands_shouldOccupyRightHandAndClearLeftHand() {
//        // ARRANGE
//        ActorProfile initialState = this.swordmanState;
//        EquipInstance twoHandedSword = this.characterInventory.get(2); // TWO_HANDED_SWORD_TPL instance
//
//        // ACT
//        EquipResult result = equipmentService.equip(initialState, twoHandedSword, EquipmentSlot.RIGHT_HAND);
//
//        // ASSERT
//        ActorProfile finalState = result.updatedState();
//        CharacterEquipment newEquipment = finalState.equipment().get();
//
//        assertTrue(result.returnedItems().isEmpty(), "No items should be returned.");
//        assertNotSame(initialState, finalState, "A new state object should be created.");
//        assertSame(twoHandedSword, newEquipment.rightHand(), "The 2H sword should be in the right hand.");
//        assertNull(newEquipment.leftHand(), "The left hand should be empty when a 2H weapon is equipped.");
//    }
//
//    @Test
//    @DisplayName("Should equip 2H weapon over a sword and shield, returning both")
//    void equip_twoHandedWeaponOverOneHandedAndShield_shouldReturnBothItems() {
//        // ARRANGE
//        EquipInstance swordInstance = this.characterInventory.get(0);
//        EquipInstance shieldInstance = this.characterInventory.get(1);
//        EquipInstance twoHandedSword = this.characterInventory.get(2);
//
//        ActorProfile stateWithSword = equipmentService.equip(this.swordmanState, swordInstance, EquipmentSlot.RIGHT_HAND).updatedState();
//        ActorProfile initialState = equipmentService.equip(stateWithSword, shieldInstance, EquipmentSlot.LEFT_HAND).updatedState();
//
//        // ACT
//        EquipResult result = equipmentService.equip(initialState, twoHandedSword, EquipmentSlot.RIGHT_HAND);
//
//        // ASSERT
//        ActorProfile finalState = result.updatedState();
//        List<EquipInstance> returnedItems = result.returnedItems();
//        CharacterEquipment finalEquipment = finalState.equipment().get();
//
//        assertEquals(2, returnedItems.size(), "Should return two items.");
//        assertTrue(returnedItems.contains(swordInstance), "The returned items should include the sword.");
//        assertTrue(returnedItems.contains(shieldInstance), "The returned items should include the shield.");
//
//        assertSame(twoHandedSword, finalEquipment.rightHand(), "The 2H sword should be in the right hand.");
//        assertNull(finalEquipment.leftHand(), "The left hand should be empty.");
//    }
//
//    @Test
//    @DisplayName("Should unequip 2H weapon when equipping a shield")
//    void equip_shieldOverTwoHanded_shouldReturnTwoHandedWeapon() {
//        // ARRANGE
//        EquipInstance twoHandedSword = this.characterInventory.get(2);
//        EquipInstance shieldInstance = this.characterInventory.get(1);
//        ActorProfile initialState = equipmentService.equip(this.swordmanState, twoHandedSword, EquipmentSlot.RIGHT_HAND).updatedState();
//
//        // ACT
//        EquipResult result = equipmentService.equip(initialState, shieldInstance, EquipmentSlot.LEFT_HAND);
//
//        // ASSERT
//        ActorProfile finalState = result.updatedState();
//        List<EquipInstance> returnedItems = result.returnedItems();
//        CharacterEquipment finalEquipment = finalState.equipment().get();
//
//        assertEquals(1, returnedItems.size(), "Should return one item.");
//        assertSame(twoHandedSword, returnedItems.get(0), "The returned item should be the 2H sword.");
//
//        assertNotSame(initialState, finalState, "A new state object should be created.");
//        assertSame(shieldInstance, finalEquipment.leftHand(), "The shield should be in the left hand.");
//        assertNull(finalEquipment.rightHand(), "The right hand should now be empty.");
//    }
//
//    @Test
//    @DisplayName("Should successfully dual wield compatible weapons")
//    void equip_dualWieldWithCompatibleWeapon_shouldSucceed() {
//        // ARRANGE
//        EquipInstance swordInstance = this.characterInventory.get(0);
//        EquipInstance knifeInstance = this.characterInventory.get(3);
//        ActorProfile initialState = equipmentService.equip(this.assassinState, swordInstance, EquipmentSlot.RIGHT_HAND).updatedState();
//
//        // ACT
//        EquipResult result = equipmentService.equip(initialState, knifeInstance, EquipmentSlot.LEFT_HAND);
//
//        // ASSERT
//        ActorProfile finalState = result.updatedState();
//        CharacterEquipment finalEquipment = finalState.equipment().get();
//
//        assertTrue(result.returnedItems().isEmpty(), "No items should be returned.");
//        assertNotSame(initialState, finalState, "A new state object should be created.");
//
//        assertSame(swordInstance, finalEquipment.rightHand(), "The sword should remain in the right hand.");
//        assertSame(knifeInstance, finalEquipment.leftHand(), "The knife should be equipped in the left hand.");
//    }
//
//    @Test
//    @DisplayName("Should fail to dual wield incompatible weapons")
//    void equip_dualWieldWithIncompatibleWeapon_shouldFailAndReturnOriginalState() {
//        // ARRANGE
//        EquipInstance swordInstance = this.characterInventory.get(0);
//        ActorProfile initialState = equipmentService.equip(this.assassinState, swordInstance, EquipmentSlot.RIGHT_HAND).updatedState();
//        EquipInstance incompatibleAxe = new EquipInstance(INCOMPATIBLE_AXE_TPL);
//
//        // ACT
//        EquipResult result = equipmentService.equip(initialState, incompatibleAxe, EquipmentSlot.LEFT_HAND);
//
//        // ASSERT
//        ActorProfile finalState = result.updatedState();
//
//        assertSame(initialState, finalState, "State should not change on a failed operation.");
//        assertTrue(result.returnedItems().isEmpty(), "No items should be returned on a failed operation.");
//    }
//
//
//
//
//
//}