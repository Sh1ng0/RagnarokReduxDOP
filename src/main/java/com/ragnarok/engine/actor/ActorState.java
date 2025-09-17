package com.ragnarok.engine.actor;

import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.stat.*;

import java.util.Map;
import java.util.Optional;

/**
 * An immutable record that represents a snapshot of a character at a given moment.
 * This is the object that the combat engine (the DOP switch) will use.
 * It contains no logic, only the final, calculated data.
 */
public record ActorState(

        // UI stuff
        long nextActionTick,
        Position position,
        // Identificadores básicos
        long id,
        String name,
        int baseLevel,
        String jobId,


        // Recursos de combate (HP/SP)
        int currentHp,
        int maxHp,
        int currentSp,
        int maxSp,

        // Stats primarios (el bloque completo)
        StatBlock totalStats,

        // Propiedades defensivas
        Race race,
        Size size,
        Element element,


        // Atributos de ataque físico
        Attack attack,
        int hitRate,       // Precisión
        int attackDelayInTicks,  // ASPD
        int criticalRate,

        // Atributos de ataque mágico
        MagicAttack magicAttack,

        // Atributos de defensa física
        Defense defense,


        // Atributos de defensa mágica
        MagicDefense magicDefense,


        // Atributos de evasión
        Flee flee,

        Map<String, Integer> availableSkills,

        Optional<CharacterEquipment> equipment // Optional because monsters have no equipment


        // WIther para devolver una modificación en los equipos equipadoS




) {

    public ActorState withEquipment(CharacterEquipment newEquipment) {
        // WIther para devolver una modificación en los equipos equipadoS
    return new ActorState(
            this.nextActionTick, this.position, this.id, this.name, this.baseLevel, this.jobId,
            this.currentHp, this.maxHp, this.currentSp, this.maxSp, this.totalStats,
            this.race, this.size, this.element, this.attack, this.hitRate,
            this.attackDelayInTicks, this.criticalRate, this.magicAttack, this.defense,
            this.magicDefense, this.flee, this.availableSkills,
            Optional.of(newEquipment)
    );
}  }








