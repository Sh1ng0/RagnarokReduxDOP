package com.ragnarok.engine.item.equip;

import com.ragnarok.engine.actor.ActorState;
import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.EquipmentSlot;

import java.util.List;
import java.util.Optional;


public class EquipmentService {

    // Este método dice: Comprueba si este item es equipable por este actor, si lo es, equípalo y devuelve lo que esté en dicho slot

    public EquipResult equip(ActorState currentState, Equipment itemToEquip, EquipmentSlot targetSlot) {

//        CharacterEquipment currentSlots = currentState.equipment().get();

        var equipmentOptional = currentState.equipment();


        if (equipmentOptional.isEmpty()) {
            System.out.println("LOG: Actor doesn't have an equip component");
            return new EquipResult(currentState, Optional.empty());
        }

        // Validacion de nivel
        if (currentState.baseLevel() < itemToEquip.requiredLevel()) {
            System.out.printf("LOG: Nivel insuficiente. Se requiere %d, el actor tiene %d.%n",
                    itemToEquip.requiredLevel(), currentState.baseLevel());
            return new EquipResult(currentState, Optional.empty());
        }

        // Validación de job
        List<String> equippableJobs = itemToEquip.equippableJobs();
        // Si el objeto es equipable por todos los jobs Collections.emptyList(), no pasa a la segunda parte de la puerta logica y da true igual
        if (!equippableJobs.isEmpty() && !equippableJobs.contains(currentState.jobId())) {
            System.out.printf("LOG: Job incompatible. El item es para %s, el actor es %s.%n",
                    equippableJobs, currentState.jobId());
            return new EquipResult(currentState, Optional.empty());

        }

        // Compatibilidad de slots
        boolean isSlotCompatible = isItemCompatibleWithSlot(itemToEquip, targetSlot);
        if (!isSlotCompatible) {
            System.out.printf("LOG: Item %s not compatible with slot %s.%n", itemToEquip.name(), targetSlot);
            return new EquipResult(currentState, Optional.empty());
        }
        System.out.println("LOG: El item pasó todas las validaciones (WIP).");

        //Compatibilidad dos manos
        // a bit of pattern matching...
        if (itemToEquip instanceof WeaponItem weapon && weapon.type().isTwoHanded()){

// WE ARE HERE

        }

        // Dual wielding logic goes here

        CharacterEquipment currentSlots = equipmentOptional.get();

        // Esto guarda el item que estaba en dicho slot pa que no se pierda (puede ser null y no haber nada)
        Equipment itemToReturn = currentSlots.get(targetSlot);

        CharacterEquipment newSlots = currentSlots.with(targetSlot, itemToEquip);

        ActorState newState = currentState.withEquipment(newSlots);

       // CUANDO SE CREE EL SERVICIO DE INVENTARIO HABRÁ QUE REFACTORIZAR ESTO
        System.out.printf("LOG: Item %s equipado en %s. Item devuelto: %s.%n",
                itemToEquip.name(), targetSlot,
                itemToReturn != null ? itemToReturn.name() : "ninguno");

        return new EquipResult(newState, Optional.ofNullable(itemToReturn));




    }




    private boolean isItemCompatibleWithSlot(Equipment item, EquipmentSlot slot) {

        return switch (item) {
            case WeaponItem weapon -> slot == EquipmentSlot.RIGHT_HAND || slot == EquipmentSlot.LEFT_HAND;

            case ArmorItem armor -> {

                yield switch (armor.type()) {
                    case SHIELD -> slot == EquipmentSlot.LEFT_HAND;
                    case ARMOR -> slot == EquipmentSlot.ARMOR;
                    case HEADGEAR ->
                            slot == EquipmentSlot.HEAD_TOP || slot == EquipmentSlot.HEAD_MID || slot == EquipmentSlot.HEAD_LOW;
                    case GARMENT -> slot == EquipmentSlot.GARMENT;
                    case FOOTGEAR -> slot == EquipmentSlot.FOOTGEAR;
                    case ACCESSORY -> slot == EquipmentSlot.ACCESSORY_1 || slot == EquipmentSlot.ACCESSORY_2;
                };
            }

//            default -> false;   NO default gracias a las sealed interfaces
        };

    }

    ;
}

