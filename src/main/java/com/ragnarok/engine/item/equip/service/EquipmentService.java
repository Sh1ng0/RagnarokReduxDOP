package com.ragnarok.engine.item.equip.service;

import com.ragnarok.engine.actor.ActorProfile;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.EquipmentSlot;
import com.ragnarok.engine.item.equip.model.ArmorTemplate;
import com.ragnarok.engine.item.equip.model.EquipmentTemplate;
import com.ragnarok.engine.item.equip.model.WeaponTemplate;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ragnarok.engine.logger.LogEvent;

/**
 * A stateless service responsible for managing all of an actor's equipment logic.
 * <p>
 * Following the principles of Data-Oriented Programming (DOP), this service is
 * completely <strong>stateless</strong>. It does not store any information between
 * calls; each operation is atomic, and its result depends solely on the
 * input parameters.
 * <p>
 * It operates on an immutable {@link ActorProfile} and, upon success, returns a new,
 * modified {@code ActorProfile} via an {@link EquipResult} record. The original
 * state is never modified.
 * <p>
 * It manages complex use cases, including:
 * <ul>
 * <li>Validation of level and job requirements.</li>
 * <li>Equipping and unequipping of two-handed weapons.</li>
 * <li>Symmetric compatibility validation for dual wield.</li>
 * <li>Standard item swapping in single-use slots.</li>
 * </ul>
 */
public class EquipmentService {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentService.class);



    /**
     * Attempts to equip an item to an actor, handling all validation and game logic.
     * <p>
     * This is the primary method of the service. It follows a strict order of operations:
     * <ol>
     * <li><b>Pre-validation (Guard Clauses):</b> Checks for level, job, and basic slot compatibility based on the item's template. If any check fails, the operation is aborted.</li>
     * <li><b>Dual Wield Compatibility:</b> If equipping a weapon, it performs a symmetric check to ensure compatibility with the weapon in the other hand.</li>
     * <li><b>Two-Handed Weapon Logic:</b> Manages the logic for equipping and unequipping two-handed weapons, correctly handling both hand slots.</li>
     * <li><b>Standard Equip:</b> For any other case, it performs a simple swap.</li>
     * </ol>
     * The method is pure and adheres to immutability. It never modifies the input {@code currentState}.
     *
     * @param currentState    The actor's state <strong>before</strong> the operation.
     * @param instanceToEquip The unique {@link EquipInstance} to be equipped, taken from the player's inventory.
     * @param targetSlot      The {@link EquipmentSlot} where the user intends to place the item.
     * @return An {@link EquipResult} containing the new {@code ActorProfile} after the operation,
     * and a list of unique {@code EquipInstance} objects that were unequipped and should be returned to the inventory.
     */
    public EquipResult equip(ActorProfile currentState, EquipInstance instanceToEquip, EquipmentSlot targetSlot) {

        // GUARD ORDER

        //  Fast Guards -> Contextual Guards -> Specific Cases -> General Case



        var equipmentOptional = currentState.equipment();

        if (equipmentOptional.isEmpty()) {
            LogEvent.ACTOR_HAS_NO_EQUIPMENT_COMPONENT.log(logger);
            return new EquipResult(currentState, List.of());
        }

        EquipmentTemplate itemTemplate = instanceToEquip.getItemTemplate();

        // LEVEL
        if (currentState.baseLevel() < itemTemplate.requiredLevel()) {
            LogEvent.EQUIP_FAIL_LEVEL.log(logger, itemTemplate.name(), itemTemplate.requiredLevel(), currentState.baseLevel());

            return new EquipResult(currentState, List.of());
        }

        // JOB
//        List<String> equippableJobs = itemTemplate.equippableJobs();
        // Si el objeto es equipable por todos los jobs Collections.emptyList(), no pasa a la segunda parte de la puerta logica y da true igual
        if (!canJobEquip(currentState, itemTemplate)) {
            // El log ahora lo podemos mover dentro del método canJobEquip si queremos,
            // o dejarlo aquí, pero la lógica de la llamada es lo importante.

            LogEvent.EQUIP_FAIL_JOB.log(logger, itemTemplate.name(), currentState.jobId(), itemTemplate.equippableJobs());

            return new EquipResult(currentState, List.of());
        }

        // SLOT COMPATIBILITY
        boolean isSlotCompatible = isItemCompatibleWithSlot(itemTemplate, targetSlot);
        if (!isItemCompatibleWithSlot(itemTemplate, targetSlot)) {
            LogEvent.EQUIP_FAIL_SLOT.log(logger, itemTemplate.name(), targetSlot);

            return new EquipResult(currentState, List.of());
        }


        CharacterEquipment currentSlots = equipmentOptional.get();

        // DUAL WIELD

        if (itemTemplate instanceof WeaponTemplate newWeapon) {
            EquipInstance prospectiveRightHand;
            EquipInstance prospectiveLeftHand;

            if (targetSlot == EquipmentSlot.RIGHT_HAND) {
                prospectiveRightHand = instanceToEquip;
                prospectiveLeftHand = currentSlots.leftHand();
            } else { // targetSlot is LEFT_HAND
                prospectiveRightHand = currentSlots.rightHand();
                prospectiveLeftHand = instanceToEquip;
            }

            if (prospectiveRightHand != null && prospectiveLeftHand != null &&
                    prospectiveRightHand.getItemTemplate() instanceof WeaponTemplate mainHand &&
                    prospectiveLeftHand.getItemTemplate() instanceof WeaponTemplate offHand) {

                if (!mainHand.compatibleOffHandTypes().contains(offHand.type())) {
                    LogEvent.EQUIP_FAIL_DUAL_WIELD.log(logger, mainHand.name(), offHand.name());

                    return new EquipResult(currentState, List.of());
                }
            }
        }



        // TWO HAND

        if (itemTemplate instanceof WeaponTemplate weapon && weapon.type().isTwoHanded()) {
            // The list now contains EquipInstances.
            List<EquipInstance> returnedItems = new ArrayList<>();
            if (currentSlots.rightHand() != null) returnedItems.add(currentSlots.rightHand());
            if (currentSlots.leftHand() != null) returnedItems.add(currentSlots.leftHand());

            // We pass the unique instance to the with() method.
            CharacterEquipment newSlots = currentSlots
                    .with(EquipmentSlot.RIGHT_HAND, null)
                    .with(EquipmentSlot.LEFT_HAND, null)
                    .with(EquipmentSlot.RIGHT_HAND, instanceToEquip);

            ActorProfile newState = currentState.withEquipment(newSlots);

            // This is the corrected line
            String returnedItemsNames = returnedItems.isEmpty()
                    ? "ninguno"
                    : String.join(", ", returnedItems.stream().map(EquipInstance::getName).toList());

            LogEvent.EQUIP_SUCCESS.log(logger, instanceToEquip.getName(), targetSlot, returnedItemsNames);


            return new EquipResult(newState, returnedItems);
        }


        // EQUIPPING OVER TWO HAND

        EquipInstance itemInRightHand = currentSlots.rightHand();
        if (itemInRightHand != null && itemInRightHand.getItemTemplate() instanceof WeaponTemplate equippedWeapon && equippedWeapon.type().isTwoHanded()) {

            List<EquipInstance> returnedItems = List.of(itemInRightHand);
            CharacterEquipment newSlots = currentSlots
                    .with(EquipmentSlot.RIGHT_HAND, null)
                    .with(targetSlot, instanceToEquip);

            ActorProfile newState = currentState.withEquipment(newSlots);

            String returnedItemsNames = String.join(", ", returnedItems.stream().map(EquipInstance::getName).toList());

            LogEvent.EQUIP_SUCCESS.log(
                    logger,
                    instanceToEquip.getName(),
                    targetSlot,
                    returnedItemsNames
            );


            return new EquipResult(newState, returnedItems);
        }


        // EQUIP

        EquipInstance itemPreviouslyInSlot = currentSlots.get(targetSlot);
        List<EquipInstance> returnedItems = new ArrayList<>();
        if (itemPreviouslyInSlot != null) returnedItems.add(itemPreviouslyInSlot);

        CharacterEquipment newSlots = currentSlots.with(targetSlot, instanceToEquip);

        ActorProfile newState = currentState.withEquipment(newSlots);


        String returnedItemsNames = returnedItems.isEmpty()
                ? "none"
                : String.join(", ", returnedItems.stream().map(EquipInstance::getName).toList());

        // CUANDO SE CREE EL SERVICIO DE INVENTARIO HABRÁ QUE REFACTORIZAR ESTO
        LogEvent.EQUIP_SUCCESS.log(logger, instanceToEquip.getName(), targetSlot, returnedItemsNames);


        return new EquipResult(newState, returnedItems);


    }

    /**
     * Unequips an item from a specified slot on an actor.
     * <p>
     * This method is pure and adheres to immutability. It will return a new ActorProfile
     * if an item is successfully unequipped. If the target slot is already empty,
     * it will return the original state and an empty Optional.
     *
     * @param currentState The actor's state <strong>before</strong> the operation.
     * @param targetSlot   The {@link EquipmentSlot} to clear.
     * @return An {@link UnequipResult} containing the new state and the unique item instance that was removed.
     */
    public UnequipResult unequip(ActorProfile currentState, EquipmentSlot targetSlot) {
        var equipmentOptional = currentState.equipment();

        // Guard Clause: Actor cannot have equipment.
        if (equipmentOptional.isEmpty()) {
            return new UnequipResult(currentState, Optional.empty());
        }

        CharacterEquipment currentSlots = equipmentOptional.get();
        // REFACTOR: The item in the slot is now a unique EquipInstance.
        EquipInstance itemInSlot = currentSlots.get(targetSlot);

        // Guard Clause: The slot is already empty, nothing to do.
        if (itemInSlot == null) {
            return new UnequipResult(currentState, Optional.empty());
        }


        CharacterEquipment newSlots = currentSlots.with(targetSlot, null);
        ActorProfile newState = currentState.withEquipment(newSlots);


        LogEvent.UNEQUIP_SUCCESS.log(logger, itemInSlot.getName(), targetSlot);


        return new UnequipResult(newState, Optional.of(itemInSlot));
    }


    /**
     * Checks if a given piece of equipment can fundamentally be placed in a target slot.
     * <p>
     * This is a low-level validation helper that checks the physical compatibility
     * between an item's type (e.g., Weapon, Shield, Armor) and a slot. It uses a
     * {@code switch} expression with pattern matching over the sealed {@link EquipmentTemplate}
     * interface to apply the correct rules.
     *
     * @param item The {@link EquipmentTemplate} to validate.
     * @param slot The {@link EquipmentSlot} to check for compatibility.
     * @return {@code true} if the item is compatible with the slot, {@code false} otherwise.
     */
    private boolean isItemCompatibleWithSlot(EquipmentTemplate item, EquipmentSlot slot) {

        return switch (item) {
            case WeaponTemplate weapon -> slot == EquipmentSlot.RIGHT_HAND || slot == EquipmentSlot.LEFT_HAND;

            case ArmorTemplate armor -> {

                yield switch (armor.type()) {
                    case SHIELD -> slot == EquipmentSlot.LEFT_HAND;
                    case ARMOR -> slot == EquipmentSlot.ARMOR;
                    case HEADGEAR ->
                            slot == EquipmentSlot.HEAD_TOP || slot == EquipmentSlot.HEAD_MID || slot == EquipmentSlot.HEAD_LOW;
                    case GARMENT -> slot == EquipmentSlot.GARMENT;
                    case FOOTGEAR -> slot == EquipmentSlot.FOOTGEAR;
                    case ACCESSORY -> slot == EquipmentSlot.ACCESSORY_1 || slot == EquipmentSlot.ACCESSORY_2;
                }; // NO DEFAULT
            }

//            default -> false;   NO default gracias a las sealed interfaces
        };

    }


    // Job stuff

    /**
     * Checks if an actor's job meets the equipment's job requirements,
     * considering the full job hierarchy.
     * <p>
     * This method validates if the actor's current job, or any of its parent jobs
     * (e.g., "Thief" for an "Assassin"), is present in the item's list of
     * equippable jobs. If the item has an empty list of required jobs, it is
     * considered equippable by all.
     *
     * @param actor The actor state, containing the {@code jobId} to be validated.
     * @param item  The equipment template, containing the list of equippable job IDs.
     * @return {@code true} if the actor's job is valid for the item, {@code false} otherwise.
     */
    private boolean canJobEquip(ActorProfile actor, EquipmentTemplate item) {
        List<String> requiredJobs = item.equippableJobs();
        if (requiredJobs.isEmpty()) {
            return true;
        }

        Job characterJob;
        try {
            // Llamada estática directa. Mucho más limpio.
            characterJob = JobRepository.findById(actor.jobId());
        } catch (IllegalArgumentException e) {
            // Si el JobId del actor no existe en el repo, es un estado inválido.
            LogEvent.JOB_NOT_FOUND_IN_REPOSITORY.log(logger, actor.jobId());

            return false;
        }

        return isJobFamilyMember(characterJob, requiredJobs);
    }

    /**
     * Recursively checks if a given job or any of its ancestors belong to a list
     * of required job IDs.
     * <p>
     * This helper method traverses up the job tree (via {@code getPreviousJobIds()})
     * starting from the character's current job. It returns {@code true} as soon as
     * it finds a match between any job in the hierarchy and the required list.
     *
     * @param currentJob     The job object to check against the required list.
     * @param requiredJobIds The list of job IDs allowed to equip the item.
     * @return {@code true} if a match is found anywhere in the job's inheritance chain,
     * {@code false} otherwise.
     */

    private boolean isJobFamilyMember(Job currentJob, List<String> requiredJobIds) {
        if (requiredJobIds.contains(currentJob.getId())) {
            return true;
        }

        for (String parentId : currentJob.getPreviousJobIds()) {
            try {
                // Llamada estática directa aquí también.
                Job parentJob = JobRepository.findById(parentId);
                if (isJobFamilyMember(parentJob, requiredJobIds)) {
                    return true;
                }
            } catch (IllegalArgumentException e) {
                LogEvent.JOB_HIERARCHY_BROKEN.log(logger, parentId);
                continue;
            }
        }

        return false;
    }

}

