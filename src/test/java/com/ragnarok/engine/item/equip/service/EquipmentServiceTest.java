package com.ragnarok.engine.item.equip.service;


import com.ragnarok.engine.actor.BaseProfile;
import com.ragnarok.engine.actor.PlayerProfile;
import com.ragnarok.engine.character.CharacterData;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.EquipmentSlot;
import com.ragnarok.engine.item.equip.model.ArmorType;
import com.ragnarok.engine.item.equip.model.EquipmentBonuses;
import com.ragnarok.engine.item.equip.model.WeaponType;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.template.ArmorTemplate;
import com.ragnarok.engine.item.template.WeaponTemplate;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.repository.EquipmentRepository;
import com.ragnarok.engine.repository.JobRepository;
import com.ragnarok.engine.stat.StatBlock;
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
    private StatCalculator statCalculator;

    // --- Test Data ---
    private PlayerProfile initialSwordmanProfile;
    private PlayerProfile initialAssassinProfile;
    private List<EquipInstance> characterInventory;


    private static final WeaponTemplate SWORD_TPL = new WeaponTemplate(
            2002L, "Sword", Element.NEUTRAL, WeaponType.ONE_HANDED_SWORD, 0.8,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 25, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            1, List.of("SWORDMAN","ASSASSIN"), 1, List.of(WeaponType.ONE_HANDED_SWORD)
    );
    private static final WeaponTemplate KNIFE_TPL = new WeaponTemplate(
            2001L, "Knife", Element.NEUTRAL, WeaponType.DAGGER, 0.7,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            1, List.of("THIEF", "SWORDMAN"), 1, List.of(WeaponType.DAGGER, WeaponType.ONE_HANDED_SWORD) // Compatible con Daga y Espada
    );
    private static final ArmorTemplate SHIELD_TPL = new ArmorTemplate(
            1002L, "Shield", Element.NEUTRAL, ArmorType.SHIELD,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            1, List.of("SWORDMAN"), 0
    );
    private static final WeaponTemplate TWO_HANDED_SWORD_TPL = new WeaponTemplate(
            2003L, "Two-Handed Sword", Element.NEUTRAL, WeaponType.TWO_HANDED_SWORD, 1.2,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 150, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            20, List.of("SWORDMAN"), 2
    );

    private static final WeaponTemplate HIGH_LEVEL_SWORD_TPL = new WeaponTemplate(
            2004L, "Excalibur", Element.HOLY, WeaponType.ONE_HANDED_SWORD, 0.9,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 150, 0, 5, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            99, List.of("SWORDMAN"), 1 // <-- Nivel requerido: 99
    );

    private static final WeaponTemplate ASSASSIN_KATAR_TPL = new WeaponTemplate(
            2005L, "Katar", Element.NEUTRAL, WeaponType.KATAR, 0.7,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 70, 0, 0, 0, 5, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            40, List.of("ASSASSIN"), 1 // <-- Solo para "ASSASSIN"
    );

    private static final WeaponTemplate INCOMPATIBLE_AXE_TPL = new WeaponTemplate(
            2006L, "Axe", Element.NEUTRAL, WeaponType.ONE_HANDED_AXE, 1.0,
            new EquipmentBonuses(StatBlock.ZERO, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, Collections.emptyMap(), Collections.emptyList()),
            10, List.of("SWORDMAN"), 1
    );


    @BeforeEach
    void setUp() {

        this.equipmentService = new EquipmentService();
        this.statCalculator = new StatCalculator();


        CharacterData swordmanData = new CharacterData(
                12L, "Dyrk", 50, 40, "SWORDMAN",
                50, 27, 23, 1, 30, 1, Collections.emptyList()
        );
        Job swordmanJob = JobRepository.findById("SWORDMAN");

        BaseProfile nakedProfile = statCalculator.buildState(swordmanData, swordmanJob);

        this.initialSwordmanProfile = new PlayerProfile(
                nakedProfile,
                CharacterEquipment.UNEQUIPPED,
                CharacterInventories.EMPTY,
                Collections.emptyList()
        );

        CharacterData assassinData = new CharacterData(
                13L, "Davinel", 50, 40, "ASSASSIN",
                40, 60, 15, 1, 40, 20, Collections.emptyList()
        );
        Job assassinJob = JobRepository.findById("ASSASSIN");
        BaseProfile nakedAssassinProfile = statCalculator.buildState(assassinData, assassinJob);
        this.initialAssassinProfile = new PlayerProfile(
                nakedAssassinProfile,
                CharacterEquipment.UNEQUIPPED,
                CharacterInventories.EMPTY,
                Collections.emptyList()
        );


        this.characterInventory = new ArrayList<>();
        this.characterInventory.add(new EquipInstance(SWORD_TPL));
        this.characterInventory.add(new EquipInstance(KNIFE_TPL));
        this.characterInventory.add(new EquipInstance(SHIELD_TPL));
        this.characterInventory.add(new EquipInstance(TWO_HANDED_SWORD_TPL));
        this.characterInventory.add(new EquipInstance(SWORD_TPL));
    }

    @Test
    @DisplayName("Should equip an item on an empty slot successfully")
    void equip_onEmptySlot_shouldSucceed() {
        // ARRANGE

        PlayerProfile initialState = this.initialSwordmanProfile;
        EquipInstance swordToEquip = this.characterInventory.get(0);


        assertThat(initialState.equipment().rightHand()).isNull();

        // ACT
        EquipResult result = equipmentService.equip(initialState, swordToEquip, EquipmentSlot.RIGHT_HAND);

        // ASSERT

        assertThat(result.returnedItems()).isEmpty();


        PlayerProfile finalState = result.updatedState();
        assertThat(finalState).isNotSameAs(initialState);


        assertThat(finalState.equipment().rightHand()).isSameAs(swordToEquip);
        assertThat(finalState.equipment().leftHand()).isNull();
    }


    @Test
    @DisplayName("Should swap an equipped item correctly")
    void equip_onOccupiedSlot_shouldSwapAndReturnOldItem() {
        // ARRANGE

        EquipInstance swordInstance = this.characterInventory.get(0);
        PlayerProfile stateWithSword = equipmentService.equip(this.initialSwordmanProfile, swordInstance, EquipmentSlot.RIGHT_HAND).updatedState();


        EquipInstance knifeInstance = this.characterInventory.get(1); // KNIFE_TPL


        assertThat(stateWithSword.equipment().rightHand()).isSameAs(swordInstance);

        // ACT

        EquipResult swapResult = equipmentService.equip(stateWithSword, knifeInstance, EquipmentSlot.RIGHT_HAND);

        // ASSERT

        List<EquipInstance> returnedItems = swapResult.returnedItems();
        assertThat(returnedItems)
                .isNotNull()
                .hasSize(1)
                .containsExactly(swordInstance);


        PlayerProfile finalState = swapResult.updatedState();
        assertThat(finalState).isNotSameAs(stateWithSword);
        assertThat(finalState.equipment().rightHand()).isSameAs(knifeInstance);
    }


    @Test
    @DisplayName("Should fail to equip an item if level requirement is not met")
    void equip_whenLevelIsTooLow_shouldFailAndReturnOriginalState() {
        // ARRANGE
        PlayerProfile initialState = this.initialSwordmanProfile;
        EquipInstance highLevelSword = new EquipInstance(HIGH_LEVEL_SWORD_TPL);


        assertThat(initialState.baseLevel()).isLessThan(HIGH_LEVEL_SWORD_TPL.requiredLevel());

        // ACT
        EquipResult result = equipmentService.equip(initialState, highLevelSword, EquipmentSlot.RIGHT_HAND);

        // ASSERT

        assertThat(result.updatedState()).isSameAs(initialState);
        assertThat(result.returnedItems()).isEmpty();
    }


    @Test
    @DisplayName("Should fail to equip an item if job requirement is not met")
    void equip_whenJobIsInvalid_shouldFailAndReturnOriginalState() {
        // ARRANGE
        PlayerProfile initialState = this.initialSwordmanProfile;
        EquipInstance assassinKatar = new EquipInstance(ASSASSIN_KATAR_TPL);

        // ACT
        EquipResult result = equipmentService.equip(initialState, assassinKatar, EquipmentSlot.RIGHT_HAND);

        // ASSERT

        assertThat(result.updatedState()).isSameAs(initialState);
        assertThat(result.returnedItems()).isEmpty();
    }

    @Test
    @DisplayName("Should fail to equip an item in an incompatible slot")
    void equip_whenSlotIsIncompatible_shouldFailAndReturnOriginalState() {
        // ARRANGE
        PlayerProfile initialState = this.initialSwordmanProfile;

        EquipInstance shieldInstance = this.characterInventory.stream()
                .filter(inst -> inst.getItemTemplate().id() == 1002L)
                .findFirst()
                .orElseThrow();

        // ACT

        EquipResult result = equipmentService.equip(initialState, shieldInstance, EquipmentSlot.RIGHT_HAND);

        // ASSERT

        assertThat(result.updatedState()).isSameAs(initialState);
        assertThat(result.returnedItems()).isEmpty();
    }

    @Test
    @DisplayName("Should equip a two-handed weapon on empty hands")
    void equip_twoHandedWeaponOnEmptyHands_shouldOccupyRightHand() {
        // ARRANGE
        PlayerProfile initialState = this.initialSwordmanProfile;
        EquipInstance twoHandedSword = this.characterInventory.stream()
                .filter(inst -> inst.getItemTemplate().id() == 2003L) // TWO_HANDED_SWORD_TPL
                .findFirst()
                .orElseThrow();


        assertThat(initialState.equipment().rightHand()).isNull();
        assertThat(initialState.equipment().leftHand()).isNull();

        // ACT
        EquipResult result = equipmentService.equip(initialState, twoHandedSword, EquipmentSlot.RIGHT_HAND);

        // ASSERT
        PlayerProfile finalState = result.updatedState();

        assertThat(result.returnedItems()).isEmpty();
        assertThat(finalState).isNotSameAs(initialState);
        assertThat(finalState.equipment().rightHand()).isSameAs(twoHandedSword);
        assertThat(finalState.equipment().leftHand()).isNull();
    }

    @Test
    @DisplayName("Should equip 2H weapon over a sword and shield, returning both")
    void equip_twoHandedWeaponOverOneHandedAndShield_shouldReturnBothItems() {
        // ARRANGE

        EquipInstance swordInstance = characterInventory.stream().filter(i -> i.getTemplateId() == 2002L).findFirst().orElseThrow();
        EquipInstance shieldInstance = characterInventory.stream().filter(i -> i.getTemplateId() == 1002L).findFirst().orElseThrow();
        EquipInstance twoHandedSword = characterInventory.stream().filter(i -> i.getTemplateId() == 2003L).findFirst().orElseThrow();


        PlayerProfile stateWithSword = equipmentService.equip(this.initialSwordmanProfile, swordInstance, EquipmentSlot.RIGHT_HAND).updatedState();
        PlayerProfile initialState = equipmentService.equip(stateWithSword, shieldInstance, EquipmentSlot.LEFT_HAND).updatedState();

        assertThat(initialState.equipment().rightHand()).isSameAs(swordInstance);
        assertThat(initialState.equipment().leftHand()).isSameAs(shieldInstance);

        // ACT

        EquipResult result = equipmentService.equip(initialState, twoHandedSword, EquipmentSlot.RIGHT_HAND);

        // ASSERT

        assertThat(result.returnedItems())
                .hasSize(2)
                .containsExactlyInAnyOrder(swordInstance, shieldInstance);


        PlayerProfile finalState = result.updatedState();
        assertThat(finalState.equipment().rightHand()).isSameAs(twoHandedSword);
        assertThat(finalState.equipment().leftHand()).isNull();
    }


    @Test
    @DisplayName("Should unequip 2H weapon when equipping a shield")
    void equip_shieldOverTwoHanded_shouldReturnTwoHandedWeapon() {
        // ARRANGE

        EquipInstance twoHandedSword = characterInventory.stream().filter(i -> i.getTemplateId() == 2003L).findFirst().orElseThrow();
        EquipInstance shieldInstance = characterInventory.stream().filter(i -> i.getTemplateId() == 1002L).findFirst().orElseThrow();


        PlayerProfile initialState = equipmentService.equip(this.initialSwordmanProfile, twoHandedSword, EquipmentSlot.RIGHT_HAND).updatedState();

        assertThat(initialState.equipment().rightHand()).isSameAs(twoHandedSword);
        assertThat(initialState.equipment().leftHand()).isNull();

        // ACT

        EquipResult result = equipmentService.equip(initialState, shieldInstance, EquipmentSlot.LEFT_HAND);

        // ASSERT

        assertThat(result.returnedItems())
                .hasSize(1)
                .containsExactly(twoHandedSword);

        PlayerProfile finalState = result.updatedState();
        assertThat(finalState.equipment().leftHand()).isSameAs(shieldInstance); // El escudo está en la mano izquierda.
        assertThat(finalState.equipment().rightHand()).isNull(); // La mano derecha ha quedado vacía.
    }


    @Test
    @DisplayName("Should successfully dual wield two compatible swords")
    void equip_dualWieldWithTwoSwords_shouldSucceed() {
        // ARRANGE
        // 1. Obtenemos las dos instancias de espada del inventario.
        EquipInstance sword1 = this.characterInventory.get(0);
        EquipInstance sword2 = this.characterInventory.get(4);

        // 2. Creamos el estado inicial: un asesino con la primera espada en la mano derecha.
        PlayerProfile initialState = equipmentService.equip(this.initialAssassinProfile, sword1, EquipmentSlot.RIGHT_HAND).updatedState();

        // ACT
        // Intentamos equipar la segunda espada (compatible) en la mano izquierda.
        EquipResult result = equipmentService.equip(initialState, sword2, EquipmentSlot.LEFT_HAND);

        // ASSERT
        assertThat(result.returnedItems()).isEmpty();

        PlayerProfile finalState = result.updatedState();
        assertThat(finalState).isNotSameAs(initialState);

        // Verificamos que ambas espadas están equipadas correctamente.
        assertThat(finalState.equipment().rightHand()).isSameAs(sword1);
        assertThat(finalState.equipment().leftHand()).isSameAs(sword2);
    }


    @Test
    @DisplayName("Should fail to dual wield incompatible weapons")
    void equip_dualWieldWithIncompatibleWeapon_shouldFailAndReturnOriginalState() {
        // ARRANGE
        // 1. Obtenemos la espada.
        EquipInstance swordInstance = characterInventory.stream().filter(i -> i.getTemplateId() == 2002L).findFirst().orElseThrow();

        // 2. Creamos el estado inicial con el asesino y la espada equipada.
        PlayerProfile initialState = equipmentService.equip(this.initialAssassinProfile, swordInstance, EquipmentSlot.RIGHT_HAND).updatedState();

        // 3. Creamos una instancia del hacha incompatible.
        EquipInstance incompatibleAxe = new EquipInstance(INCOMPATIBLE_AXE_TPL);

        // ACT
        // Intentamos equipar el hacha. Nuestra SWORD_TPL no la tiene en su lista de compatibilidad.
        EquipResult result = equipmentService.equip(initialState, incompatibleAxe, EquipmentSlot.LEFT_HAND);

        // ASSERT
        // La operación debe fallar, devolviendo el estado original sin cambios.
        assertThat(result.updatedState()).isSameAs(initialState);
        assertThat(result.returnedItems()).isEmpty();
    }





}
