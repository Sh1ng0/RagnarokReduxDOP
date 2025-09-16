package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.actor.ActorState;
import com.ragnarok.engine.character.CharacterData;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.item.defaults.EquipmentDefaults;
import com.ragnarok.engine.item.equip.model.*;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.repository.EquipmentRepository;
import com.ragnarok.engine.repository.JobRepository;
import com.ragnarok.engine.stat.StatCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.Collections;
import java.util.List;

@DisplayName("Tests for EquipmentService")
class EquipmentServiceTest {

    // The service under test
    private EquipmentService equipmentService;

    // A helper to create actor states consistently
    private StatCalculator statCalculator;

    // --- Actors for different test scenarios ---
    private ActorState swordmanState;
    private ActorState assassinState; // Essential for dual wield tests

    // --- Arsenal of items, our single source of truth ---
    private static final WeaponItem BASIC_SWORD = (WeaponItem) EquipmentRepository.findById(2002L);
    private static final ArmorItem BASIC_SHIELD = (ArmorItem) EquipmentRepository.findById(1002L);
    private static final WeaponItem TWO_HANDED_SWORD = (WeaponItem) EquipmentRepository.findById(2003L);
    private static final WeaponItem HIGH_LEVEL_SWORD = (WeaponItem) EquipmentRepository.findById(2004L);
    private static final WeaponItem ASSASSIN_KATAR = (WeaponItem) EquipmentRepository.findById(2005L);
    private static final WeaponItem INCOMPATIBLE_AXE = (WeaponItem) EquipmentRepository.findById(2006L);
    private static final WeaponItem KNIFE = (WeaponItem) EquipmentRepository.findById(2001L);


    @BeforeEach
    void setUp() {
        this.equipmentService = new EquipmentService();
        this.statCalculator = new StatCalculator();

        // 1. Create the Swordman state
        CharacterData swordmanData = new CharacterData(
                12L, "Veteran Swordsman", 50, 40, "SWORDMAN",
                50, 27, 23, 1, 30, 1, Collections.emptyList()
        );
        Job swordmanJob = JobRepository.findById("SWORDMAN");
        this.swordmanState = statCalculator.buildState(swordmanData, swordmanJob);

        // 2. Create the Assassin state
        CharacterData assassinData = new CharacterData(
                13L, "Silent Killer", 50, 40, "ASSASSIN",
                40, 60, 15, 1, 40, 20, Collections.emptyList()
        );
        Job assassinJob = JobRepository.findById("ASSASSIN");
        this.assassinState = statCalculator.buildState(assassinData, assassinJob);
    }

    // All @Test methods will go here...
}