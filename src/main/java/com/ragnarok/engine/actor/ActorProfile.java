package com.ragnarok.engine.actor;

import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.stat.*;

import java.util.Map;
import java.util.Optional;
/**
 * An immutable record representing a character's "stat sheet" or profile.
 * <p>
 * This object contains all the slow-changing, calculated combat statistics
 * derived from a character's base stats, job, and equipment. It is the final
 * output of the {@code StatCalculator}.
 * <p>
 * This "photo" of the character's capabilities is only recalculated when a
 * significant event occurs (e.g., leveling up, equipping an item, receiving a buff),
 * not on every game tick.
 *
 * @param id                  The template ID of the character/monster (e.g., the ID for "Poring").
 * @param name                The character's or monster's name.
 * @param baseLevel           The character's base level.
 * @param jobId               The string identifier for the character's job.
 * @param maxHp               The maximum calculated health points.
 * @param maxSp               The maximum calculated spirit points.
 * @param totalStats          The final block of primary stats (STR, AGI, VIT, etc.) after all bonuses.
 * @param race                The character's race.
 * @param size                The character's size.
 * @param element             The character's element.
 * @param attack              The calculated physical attack attributes.
 * @param hitRate             The final HIT rate (accuracy).
 * @param attackDelayInTicks  The final calculated attack delay (ASPD).
 * @param criticalRate        The final critical hit chance.
 * @param magicAttack         The calculated magical attack attributes.
 * @param defense             The calculated physical defense attributes.
 * @param magicDefense        The calculated magical defense attributes.
 * @param flee                The calculated evasion attributes.
 * @param availableSkills     A map of available skill IDs and their levels.
 * @param equipment           An Optional containing the character's equipped items.
 */
public record ActorProfile(
        // Identificadores básicos
        long id,
        String name,
        int baseLevel,
        String jobId,

        // Recursos de combate (máximos)
        int maxHp,
        int maxSp,

        // Stats y propiedades
        StatBlock totalStats,
        Race race,
        Size size,
        Element element,

        // Atributos de combate calculados
        Attack attack,
        int hitRate,
        int attackDelayInTicks,
        int criticalRate,
        MagicAttack magicAttack,
        Defense defense,
        MagicDefense magicDefense,
        Flee flee,

        // Habilidades y Equipo
        Map<String, Integer> availableSkills,
        Optional<CharacterEquipment> equipment,

        // INventario
        CharacterInventories inventory


) {

    public ActorProfile withEquipment(CharacterEquipment newEquipment) {
        return new ActorProfile(
                this.id, this.name, this.baseLevel, this.jobId,
                this.maxHp, this.maxSp, this.totalStats,
                this.race, this.size, this.element, this.attack, this.hitRate,
                this.attackDelayInTicks, this.criticalRate, this.magicAttack, this.defense,
                this.magicDefense, this.flee, this.availableSkills,
                Optional.of(newEquipment), this.inventory
        );
    }


    /**
     * Returns a new ActorProfile instance with updated inventories.
     * @param newInventories The new inventories state.
     * @return A new, updated ActorProfile.
     */
    public ActorProfile withInventories(CharacterInventories newInventories) {
        return new ActorProfile(
                this.id, this.name, this.baseLevel, this.jobId,
                this.maxHp, this.maxSp, this.totalStats,
                this.race, this.size, this.element, this.attack, this.hitRate,
                this.attackDelayInTicks, this.criticalRate, this.magicAttack, this.defense,
                this.magicDefense, this.flee, this.availableSkills,
                this.equipment, newInventories
        );
    }
}



